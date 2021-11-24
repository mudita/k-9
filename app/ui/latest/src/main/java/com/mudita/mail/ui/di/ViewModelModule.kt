package com.mudita.mail.ui.di

import com.mudita.mail.ui.extension.getWith
import com.mudita.mail.ui.usecase.signIn.viewModel.SignInViewModel
import org.koin.dsl.module

val composeViewModelModule = module {

    factory { params ->
        SignInViewModel(
            interactor = get(),
            navigator = getWith(params)
        )
    }
}
