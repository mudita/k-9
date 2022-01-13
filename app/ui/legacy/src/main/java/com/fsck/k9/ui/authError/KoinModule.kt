package com.fsck.k9.ui.authError

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val authErrorUiModule = module {

    viewModel { AuthenticationErrorViewModel(get(), get(), get()) }
}