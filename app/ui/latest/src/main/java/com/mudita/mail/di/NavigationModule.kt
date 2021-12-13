package com.mudita.mail.di

import androidx.navigation.NavHostController
import com.mudita.mail.ui.extension.getWith
import com.mudita.mail.ui.usecase.email.navigator.EmailNavigator
import com.mudita.mail.ui.usecase.email.navigator.EmailNavigatorImpl
import com.mudita.mail.ui.usecase.signIn.navigator.SignInNavigator
import com.mudita.mail.ui.usecase.signIn.navigator.SignInScreenNavigatorImpl
import org.koin.dsl.module

val composeNavigationModule = module {

    factory<SignInNavigator> { (navHostController: NavHostController) ->
        SignInScreenNavigatorImpl(
            navHostController = navHostController
        )
    }

    factory<EmailNavigator> { params ->
        EmailNavigatorImpl(
            context = getWith(params),
            toSetupAccountNavigator = get()
        )
    }
}
