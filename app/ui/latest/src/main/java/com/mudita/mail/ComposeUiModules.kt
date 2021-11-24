package com.mudita.mail

import com.mudita.mail.ui.di.composeNavigationModule
import com.mudita.mail.ui.di.composeViewModelModule
import com.mudita.mail.ui.di.interactorModule
import com.mudita.mail.ui.di.repositoryModule

val composeUiModules = listOf(
    composeNavigationModule,
    composeViewModelModule,
    interactorModule,
    repositoryModule
)
