package com.mudita.mail.di

import com.mudita.mail.ui.extension.getWith
import com.mudita.mail.ui.usecase.add.navigator.AddAccountNavigator
import com.mudita.mail.ui.usecase.add.navigator.AddAccountNavigatorImpl
import com.mudita.mail.ui.usecase.email.navigator.EmailNavigator
import com.mudita.mail.ui.usecase.email.navigator.EmailNavigatorImpl
import com.mudita.mail.ui.usecase.signIn.navigator.SignInNavigator
import com.mudita.mail.ui.usecase.signIn.navigator.SignInScreenNavigatorImpl
import org.koin.dsl.module

val composeNavigationModule = module {

    factory<SignInNavigator> { params ->
        SignInScreenNavigatorImpl(
            navController = getWith(params),
            context = getWith(params),
            toSetupAccountNavigator = get()
        )
    }

    factory<EmailNavigator> { params ->
        EmailNavigatorImpl(
            navController = getWith(params),
            context = getWith(params),
            toSetupAccountNavigator = get()
        )
    }

    factory<AddAccountNavigator> { params ->
        AddAccountNavigatorImpl(
            navController = getWith(params),
            context = getWith(params),
            toSetupAccountNavigator = get(),
            backPressHandler = getWith(params)
        )
    }
}
