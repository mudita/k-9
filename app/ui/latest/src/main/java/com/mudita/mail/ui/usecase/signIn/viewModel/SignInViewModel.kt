package com.mudita.mail.ui.usecase.signIn.viewModel

import androidx.lifecycle.ViewModel
import com.mudita.mail.ui.usecase.signIn.navigator.SignInNavigator

class SignInViewModel(
    // Other dependencies like interactor aggregating calls
    // to other services repositories etc
    // val interactor: SignInInteractor
    private val navigator: SignInNavigator
) : ViewModel() {

    fun gmailSelected() {
        navigator.moveToCredentials()
    }
}
