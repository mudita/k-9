package com.mudita.mail.di

import androidx.navigation.NavHostController
import com.mudita.mail.ui.extension.getWith
import com.mudita.mail.ui.usecase.email.viewModel.EmailViewModel
import com.mudita.mail.ui.usecase.signIn.viewModel.SignInViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

val composeViewModelModule = module {

    viewModel { (navHostController: NavHostController) ->
        SignInViewModel(
            interactor = get(),
            navigator = get { parametersOf(navHostController) }
        )
    }

    viewModel { params ->
        EmailViewModel(
            interactor = get(),
            navigator = getWith(params),
            providerTypeName = params.get(),
        )
    }
}
