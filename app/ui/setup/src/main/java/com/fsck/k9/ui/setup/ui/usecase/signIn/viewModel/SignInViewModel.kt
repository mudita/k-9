package com.fsck.k9.ui.setup.ui.usecase.signIn.viewModel

import androidx.lifecycle.ViewModel
import com.fsck.k9.ui.setup.ui.usecase.signIn.navigator.SignInNavigator

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
