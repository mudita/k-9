package com.mudita.mail.di

import com.mudita.mail.ui.extension.getWith
import com.mudita.mail.ui.usecase.email.viewModel.EmailViewModel
import com.mudita.mail.ui.usecase.signIn.viewModel.SignInViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val composeViewModelModule = module {

    viewModel { params ->
        SignInViewModel(
            interactor = get(),
            navigator = getWith(params),
        )
    }

    viewModel { params ->
        EmailViewModel(
            navigator = getWith(params),
        )
    }
}
