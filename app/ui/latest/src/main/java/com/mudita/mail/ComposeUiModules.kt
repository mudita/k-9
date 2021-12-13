package com.mudita.mail

import com.mudita.mail.di.composeNavigationModule
import com.mudita.mail.di.composeViewModelModule
import com.mudita.mail.di.interactorModule
import com.mudita.mail.di.repositoryModule
import com.mudita.mail.di.serviceModule
import com.mudita.mail.di.utilModule

val composeUiModules = listOf(
    composeNavigationModule,
    composeViewModelModule,
    interactorModule,
    repositoryModule,
    serviceModule,
    utilModule
)
