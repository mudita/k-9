package com.mudita.mail.ui.usecase.signIn.navigator

import androidx.navigation.NavHostController
import com.mudita.mail.repository.providers.model.ProviderType
import com.mudita.mail.ui.navigation.EmailDestination

class SignInScreenNavigatorImpl(
    private val navHostController: NavHostController
) : SignInNavigator {

    override fun moveToEmailScreen(providerType: ProviderType) {
        navHostController.navigate(EmailDestination.toRoute(providerType.name))
    }
}
