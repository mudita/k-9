package com.mudita.mail.ui.usecase.email.viewModel

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mudita.mail.interactor.email.EmailInteractor
import com.mudita.mail.repository.providers.model.ProviderType
import com.mudita.mail.ui.usecase.email.navigator.EmailNavigator
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

    fun submitEmail(email: String) {
        startAuthProcess(email)
    }

    private fun startAuthProcess(email: String, providerType: ProviderType = ProviderType.GMAIL) {
        val authConfig = interactor.getProviderAuthConfig(providerType) ?: return
        val authIntent = interactor.getAuthRequestIntent(email, authConfig)
        _uiState.update { it.copy(authIntent = authIntent) }
    }

    fun handleAuthResult(
        username: String,
        intent: Intent
    ) {
        _uiState.update { it.copy(authIntent = null) }
        viewModelScope.launch {
            interactor.processResponseAuthIntent(intent, username)
            navigator.moveToAccountSetupChecks(username)
        }
    }
}
