package com.mudita.mail.ui.usecase.signIn.viewModel

import androidx.lifecycle.ViewModel
import com.mudita.mail.interactor.signIn.SignInInteractor
import com.mudita.mail.repository.providers.model.ProviderTile
import com.mudita.mail.repository.providers.model.ProviderType
import com.mudita.mail.ui.usecase.signIn.navigator.SignInNavigator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

data class UiState(
    val providers: List<ProviderTile> = emptyList(),
)

class SignInViewModel(
    interactor: SignInInteractor,
    private val navigator: SignInNavigator
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    init {
        interactor.getProviders().let { providers ->
            _uiState.update { it.copy(providers = providers) }
        }
    }

    fun selectProvider(providerType: ProviderType) {
        when (providerType) {
            ProviderType.GMAIL,
            ProviderType.ICLOUD,
            ProviderType.OUTLOOK,
            ProviderType.MANUAL -> navigator.moveToEmailScreen(providerType.name)
        }
    }
}
