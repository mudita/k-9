package com.fsck.k9.ui.setup

import com.fsck.k9.ui.setup.ui.di.composeNavigationModule
import com.fsck.k9.ui.setup.ui.di.composeViewModelModule

val composeUiModules = listOf(
    composeNavigationModule,
    composeViewModelModule
)
