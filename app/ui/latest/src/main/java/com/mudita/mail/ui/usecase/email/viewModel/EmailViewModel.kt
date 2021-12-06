package com.mudita.mail.ui.usecase.email.viewModel

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mudita.mail.interactor.email.EmailInteractor
import com.mudita.mail.repository.auth.config.AuthConfig
import com.mudita.mail.repository.providers.model.ProviderType
import com.mudita.mail.service.auth.AuthRequestData.Companion.toAuthResponseData
import com.mudita.mail.ui.usecase.email.navigator.EmailNavigator
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class UiState(
    val authIntent: Intent? = null
)

class EmailViewModel(
    val navigator: EmailNavigator,
    val interactor: EmailInteractor
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    private val intentChannel: Channel<Intent> = Channel(capacity = 1)

    fun startAuthProcess() {
        startAuthProcess(providerType = ProviderType.GMAIL)
    }

    private fun startAuthProcess(providerType: ProviderType) {
        interactor.getProviderAuthConfig(providerType)
            .fold(
                onSuccess = ::startAuthProcess,
                onFailure = ::showError
            )
    }

    private fun startAuthProcess(authConfig: AuthConfig) {
        viewModelScope.launch {
            val authRequestData = interactor.getAuthRequestData(authConfig)
            _uiState.update {
                it.copy(
                    authIntent = authRequestData.intent
                )
            }
            val intent = intentChannel.receive()
            val email = interactor.processAuthResponseData(authRequestData.toAuthResponseData(intent))
            // FIXME results instead of null
            email?.let {
                navigator.moveToAccountSetupChecks(it)
            }
        }
    }

    private fun showError(throwable: Throwable) {
    }

    fun handleAuthResult(
        intent: Intent
    ) {
        _uiState.update { it.copy(authIntent = null) }
        viewModelScope.launch {
            intentChannel.send(intent)
        }
    }
}
