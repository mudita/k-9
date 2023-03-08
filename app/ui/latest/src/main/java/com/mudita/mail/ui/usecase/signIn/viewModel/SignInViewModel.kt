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
import kotlinx.coroutines.launch

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

    private var intentChannel: Channel<Intent> = Channel(capacity = 1)

    fun selectProvider(providerType: ProviderType) =
        when (providerType) {
            ProviderType.GMAIL,
            ProviderType.OUTLOOK -> startAuthProcess(providerType)
            ProviderType.MANUAL -> navigator.moveToManualAccountSetup()
            ProviderType.ICLOUD -> {
                _uiState.update { it.copy(authIntent = null) }
                handleAuthProcessCancellation()
                navigator.moveToAppSpecificPasswordSetup()
            }
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
                    _uiState.update { it.copy(authIntent = authRequestData.intent) }
                    val intent = intentChannel.receive()
                    interactor.processAuthResponseData(
                        providerType,
                        authRequestData.toAuthResponseData(intent)
                    ).fold(
                        onSuccess = { navigator.moveToAccountSetupChecks(it, providerType.providerName) },
                        onFailure = ::updateErrorState
                    )
                }
        }
    }

    fun handleAuthResult(intent: Intent) {
        _uiState.update { it.copy(authIntent = null) }
        viewModelScope.launch {
            intentChannel.send(intent)
        }
        startLoading()
    }

    fun onInfoHidden() {
        _uiState.update { it.copy(error = null) }
        stopLoading()
    }

    fun handleAuthProcessCancellation() {
        intentChannel.cancel()
        intentChannel = Channel(1)
        stopLoading()
    }

    override fun updateLoadingState(isLoading: Boolean) {
        _uiState.update { it.copy(isLoading = isLoading) }
    }

    override fun updateErrorState(uiError: UiError) {
        _uiState.update { it.copy(error = uiError) }
    }
}
