package com.mudita.mail.ui.usecase.email.viewModel

import android.content.Intent
import androidx.lifecycle.viewModelScope
import com.mudita.mail.interactor.email.EmailInteractor
import com.mudita.mail.repository.auth.config.AuthConfig
import com.mudita.mail.repository.providers.model.ProviderType
import com.mudita.mail.service.auth.AuthRequestData.Companion.toAuthResponseData
import com.mudita.mail.ui.usecase.email.navigator.EmailNavigator
import com.mudita.mail.ui.viewModel.BaseUiState
import com.mudita.mail.ui.viewModel.BaseViewModel
import com.mudita.mail.ui.viewModel.UiError
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

data class UiState(
    val authIntent: Intent? = null,
    override val isLoading: Boolean = false,
    override val error: UiError? = null
) : BaseUiState

class EmailViewModel(
    private val navigator: EmailNavigator,
    private val interactor: EmailInteractor,
    providerTypeName: String?,
) : BaseViewModel<UiState>() {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    private val providerType = providerTypeName?.let(ProviderType::valueOf)

    private val intentChannel: Channel<Intent> = Channel(capacity = 1)

    fun startAuthProcess() {
        when (providerType) {
            ProviderType.GMAIL,
            ProviderType.OUTLOOK -> startAuthProcess(providerType)
            else -> updateErrorState(
                UiError("Authorization process currently not supported for selected authorization way")
            )
        }
    }

    private fun startAuthProcess(providerType: ProviderType) {
        interactor.getProviderAuthConfig(providerType)
            .fold(
                onSuccess = { startAuthProcess(providerType, it) },
                onFailure = ::updateErrorState
            )
    }

    private fun startAuthProcess(
        providerType: ProviderType,
        authConfig: AuthConfig
    ) {
        viewModelScope.launchWithLoading {
            interactor.getAuthRequestData(authConfig)
                .suspendingRunBlockOrShowError { authRequestData ->
                    _uiState.update {
                        it.copy(
                            authIntent = authRequestData.intent
                        )
                    }
                    val intent = intentChannel.receive()
                    val email =
                        interactor.processAuthResponseData(
                            providerType,
                            authRequestData.toAuthResponseData(intent)
                        )
                            .getOrDefault("")
                    email.takeIf { it.isNotEmpty() }?.let {
                        navigator.moveToAccountSetupChecks(it)
                    }
                }
        }
    }

    fun handleAuthResult(
        intent: Intent
    ) {
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
