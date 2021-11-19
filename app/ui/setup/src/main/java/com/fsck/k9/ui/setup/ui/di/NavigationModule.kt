package com.fsck.k9.ui.setup.ui.di

import com.fsck.k9.ui.setup.ui.usecase.signIn.navigator.SignInNavigator
import com.fsck.k9.ui.setup.ui.usecase.signIn.navigator.SignInScreenNavigatorImpl
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
