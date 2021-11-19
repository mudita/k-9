package com.fsck.k9.navigation

import com.fsck.k9.ui.base.navigation.ToSetupAccountNavigator
import org.koin.dsl.module

// Binding of interface to impl
val navigationModule = module {

    factory<ToSetupAccountNavigator> { ToSetupAccountNavigatorImpl() }
}
