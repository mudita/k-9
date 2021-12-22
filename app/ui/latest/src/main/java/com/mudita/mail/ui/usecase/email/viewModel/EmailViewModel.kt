package com.mudita.mail.ui.usecase.email.viewModel

import com.mudita.mail.ui.usecase.email.navigator.EmailNavigator
import com.mudita.mail.ui.viewModel.BaseUiState
import com.mudita.mail.ui.viewModel.BaseViewModel
import com.mudita.mail.ui.viewModel.UiError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

data class UiState(
    override val isLoading: Boolean = false,
    override val error: UiError? = null,
    val startGeneratePasswordFlow: Boolean = false,
    val showHowToGeneratePassword: Boolean = false,
) : BaseUiState

class EmailViewModel(
    private val navigator: EmailNavigator
) : BaseViewModel<UiState>() {

    private val _uiState = MutableStateFlow(UiState())

    val uiState: StateFlow<UiState> = _uiState

    fun onNext(
        email: String,
        password: String
    ) = navigator.moveToAccountSetupChecks(email, password)

    fun onGenerateAppSpecificPassword() {
        _uiState.update { it.copy(showHowToGeneratePassword = false) }
        _uiState.update { it.copy(startGeneratePasswordFlow = true) }
    }

    fun onGenerateAppSpecificPasswordLaunched() {
        _uiState.update { it.copy(startGeneratePasswordFlow = false) }
    }

    fun onBack() {
        navigator.moveBack()
    }

    fun onHowToGenerateAppSpecificPassword() {
        _uiState.update { it.copy(showHowToGeneratePassword = true) }
    }

    override fun updateLoadingState(isLoading: Boolean) {
        _uiState.update { it.copy(isLoading = isLoading) }
    }

    override fun updateErrorState(uiError: UiError) {
        _uiState.update { it.copy(error = uiError) }
    }
}
