package com.mudita.mail

import com.mudita.mail.ui.di.composeNavigationModule
import com.mudita.mail.ui.di.composeViewModelModule
import com.mudita.mail.ui.di.interactorModule
import com.mudita.mail.ui.di.repositoryModule
import com.mudita.mail.ui.util.imageResolverModule
import com.mudita.mail.ui.util.stringKey.stringResolverModule

val composeUiModules = listOf(
    composeNavigationModule,
    composeViewModelModule,
    imageResolverModule,
    stringResolverModule,
    interactorModule,
    repositoryModule
)
