package com.mudita.mail.ui.usecase.email.viewModel

import com.mudita.mail.interactor.email.EmailInteractor
import com.mudita.mail.ui.usecase.email.navigator.EmailNavigator
import com.mudita.mail.ui.viewModel.BaseUiState
import com.mudita.mail.ui.viewModel.BaseViewModel
import com.mudita.mail.ui.viewModel.UiError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class UiState(
    override val isLoading: Boolean = false,
    override val error: UiError? = null
) : BaseUiState

class EmailViewModel(
    interactor: EmailInteractor,
    private val navigator: EmailNavigator
) : BaseViewModel<UiState>() {

    private val _uiState = MutableStateFlow(UiState())

    val uiState: StateFlow<UiState> = _uiState

    fun onNext(
        email: String,
        password: String
    ) {
        navigator.moveToAccountSetupChecks(
            "starczewskij@icloud.com",
            "ewmu-tryn-gldp-wfvj"
        )
    }

    fun onGenerateAppSpecificPassword() {
    }

    fun onBack() {
        navigator.moveBack()
    }

    override fun updateLoadingState(isLoading: Boolean) {
    }

    override fun updateErrorState(uiError: UiError) {
    }
}
