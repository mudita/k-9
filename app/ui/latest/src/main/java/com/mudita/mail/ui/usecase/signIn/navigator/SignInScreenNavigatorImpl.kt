package com.mudita.mail.ui.usecase.signIn.navigator

import androidx.navigation.NavHostController
import com.mudita.mail.ui.navigation.EmailDestination

class SignInScreenNavigatorImpl(
    private val navHostController: NavHostController
) : SignInNavigator {

    override fun moveToEmailScreen(providerTypeName: String) {
        navHostController.navigate(EmailDestination.toRoute())
    }
}
