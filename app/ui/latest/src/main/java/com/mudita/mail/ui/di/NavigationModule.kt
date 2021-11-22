package com.mudita.mail.ui.di

import com.mudita.mail.ui.usecase.signIn.navigator.SignInNavigator
import com.mudita.mail.ui.usecase.signIn.navigator.SignInScreenNavigatorImpl
import org.koin.dsl.module

val composeNavigationModule = module {

    factory<SignInNavigator> { params ->
        SignInScreenNavigatorImpl(
            context = params.get(),
            // injection from shared koin module of interface impl
            toSetupAccountNavigator = get()
        )
    }
}
