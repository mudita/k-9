package com.mudita.mail.ui.di

import com.mudita.mail.ui.extension.getFrom
import com.mudita.mail.ui.usecase.signIn.viewModel.SignInViewModel
import org.koin.dsl.module

val composeViewModelModule = module {

    factory { params ->
        SignInViewModel(
            navigator = getFrom(params)
        )
    }
}
