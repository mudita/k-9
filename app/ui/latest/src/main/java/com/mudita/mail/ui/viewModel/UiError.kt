package com.mudita.mail.ui.viewModel

data class UiError(
    val message: String?
)

fun UiError?.isError() = this != null
