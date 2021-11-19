package com.fsck.k9.ui.setup.ui.di

import com.fsck.k9.ui.setup.ui.extension.getFrom
import com.fsck.k9.ui.setup.ui.usecase.signIn.viewModel.SignInViewModel
import org.koin.dsl.module

val composeViewModelModule = module {

    factory { params ->
        SignInViewModel(
            navigator = getFrom(params)
        )
    }
}
