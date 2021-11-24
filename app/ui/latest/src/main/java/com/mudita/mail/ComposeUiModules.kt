package com.mudita.mail

import com.mudita.mail.ui.di.composeNavigationModule
import com.mudita.mail.ui.di.composeViewModelModule

val composeUiModules = listOf(
    composeNavigationModule,
    composeViewModelModule
)
