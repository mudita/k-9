package com.mudita.mail.ui.di

import com.mudita.mail.ui.extension.getWith
import com.mudita.mail.ui.usecase.signIn.navigator.SignInNavigator
import com.mudita.mail.ui.usecase.signIn.navigator.SignInScreenNavigatorImpl
import org.koin.dsl.module

val composeNavigationModule = module {

    factory<SignInNavigator> { params ->
        SignInScreenNavigatorImpl(
            context = getWith(params),
            toSetupAccountNavigator = get()
        )
    }
}
