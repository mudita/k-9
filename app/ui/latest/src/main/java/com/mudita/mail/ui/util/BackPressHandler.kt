package com.mudita.mail.ui.util

class BackPressHandler(
    private val onBackAction: () -> Unit
) {

    fun moveBack() = onBackAction()
}
