package com.mudita.mail

import com.mudita.mail.di.composeNavigationModule
import com.mudita.mail.di.composeViewModelModule
import com.mudita.mail.di.interactorModule
import com.mudita.mail.di.relayModule
import com.mudita.mail.di.repositoryModule
import com.mudita.mail.di.serviceModule

val composeUiModules = listOf(
    composeNavigationModule,
    composeViewModelModule,
    interactorModule,
    repositoryModule,
    serviceModule,
    relayModule
)
