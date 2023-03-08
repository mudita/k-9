package com.fsck.k9.navigation

import com.fsck.k9.ui.authError.AuthorizationErrorNavigator
import com.fsck.k9.ui.authError.AuthorizationErrorNavigatorImpl
import com.fsck.k9.ui.base.navigation.ToSetupAccountNavigator
import org.koin.dsl.module

val navigationModule = module {

    factory<ToSetupAccountNavigator> { ToSetupAccountNavigatorImpl() }

    factory<AuthorizationErrorNavigator> { AuthorizationErrorNavigatorImpl(get()) }
}
