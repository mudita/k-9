package com.mudita.mail.ui.usecase.signIn.viewModel

import android.content.Intent
import androidx.lifecycle.viewModelScope
import com.mudita.mail.interactor.signIn.SignInInteractor
import com.mudita.mail.repository.auth.config.AuthConfig
import com.mudita.mail.repository.providers.model.ProviderTile
import com.mudita.mail.repository.providers.model.ProviderType
import com.mudita.mail.service.auth.AuthRequestData.Companion.toAuthResponseData
import com.mudita.mail.ui.usecase.signIn.navigator.SignInNavigator
import com.mudita.mail.ui.viewModel.BaseUiState
import com.mudita.mail.ui.viewModel.BaseViewModel
import com.mudita.mail.ui.viewModel.UiError
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

data class UiState(
    val providers: List<ProviderTile> = emptyList(),
    val authIntent: Intent? = null,
    override val isLoading: Boolean = false,
    override val error: UiError? = null
) : BaseUiState

class SignInViewModel(
    private val interactor: SignInInteractor,
    private val navigator: SignInNavigator
) : BaseViewModel<UiState>() {

    private val _uiState = MutableStateFlow(UiState())

    val uiState: StateFlow<UiState> = _uiState

    init {
        interactor.getProviders().let { providers ->
            _uiState.update { it.copy(providers = providers) }
        }
    }

    private val intentChannel: Channel<Intent> = Channel(capacity = 1)

    fun selectProvider(providerType: ProviderType) =
        when (providerType) {
            ProviderType.GMAIL,
            ProviderType.OUTLOOK -> startAuthProcess(providerType)
            else -> updateErrorState(
                UiError("Authorization process currently not supported for selected authorization way")
            )
        }

    private fun startAuthProcess(providerType: ProviderType) {
        interactor.getProviderAuthConfig(providerType)
            .fold(
                onSuccess = { startAuthProcess(providerType, it) },
                onFailure = ::updateErrorState
            )
    }

    private fun startAuthProcess(providerType: ProviderType, authConfig: AuthConfig) {
        viewModelScope.launchWithLoading {
            interactor.getAuthRequestData(authConfig)
                .suspendingRunBlockOrShowError { authRequestData ->
                    _uiState.update {
                        it.copy(
                            authIntent = authRequestData.intent
                        )
                    }
                    val intent = intentChannel.receive()
                    interactor.processAuthResponseData(
                        providerType,
                        authRequestData.toAuthResponseData(intent)
                    ).fold(
                        onSuccess = navigator::moveToAccountSetupChecks,
                        onFailure = ::updateErrorState
                    )
                }
        }
    }

    fun handleAuthResult(intent: Intent) {
        _uiState.update { it.copy(authIntent = null) }
        viewModelScope.launchWithLoading {
            intentChannel.send(intent)
        }
    }

    override fun updateLoadingState(isLoading: Boolean) {
        _uiState.update { it.copy(isLoading = isLoading) }
    }

    override fun updateErrorState(uiError: UiError) {
        _uiState.update { it.copy(error = uiError) }
    }
}
