package com.mudita.mail.ui.viewModel

interface BaseUiState {

    val isLoading: Boolean

    val error: UiError?
}
