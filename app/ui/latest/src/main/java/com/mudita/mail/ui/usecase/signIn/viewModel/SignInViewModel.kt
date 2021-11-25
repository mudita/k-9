package com.mudita.mail.ui.usecase.signIn.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mudita.mail.interactor.signIn.SignInInteractor
import com.mudita.mail.repository.providers.model.ProviderTile
import com.mudita.mail.repository.providers.model.ProviderType
import com.mudita.mail.ui.usecase.signIn.navigator.SignInNavigator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class UiState(
    val providers: List<ProviderTile> = emptyList(),
    val result: String = ""
)

class SignInViewModel(
    private val interactor: SignInInteractor,
    private val navigator: SignInNavigator
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    init {
        interactor.getProviders().let { providers ->
            _uiState.update { it.copy(providers = providers) }
        }
        viewModelScope.launch {
            val result = interactor.processAuthData()
            _uiState.update {
                it.copy(result = result)
            }
        }
    }

    fun selectProvider(providerType: ProviderType) {
        when (providerType) {
            ProviderType.GMAIL -> startAuthProcess(providerType)
            ProviderType.ICLOUD,
            ProviderType.OUTLOOK,
            ProviderType.MANUAL -> navigator.moveToCredentials()
        }
    }

    private fun startAuthProcess(providerType: ProviderType) {
        val authConfig = interactor.getProviderAuthConfig(providerType) ?: return
        viewModelScope.launch {
            interactor.startAuthProcess(authConfig)
        }
    }
}
