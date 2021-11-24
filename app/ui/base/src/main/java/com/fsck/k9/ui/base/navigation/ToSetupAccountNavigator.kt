package com.fsck.k9.ui.base.navigation

import android.content.Context

// Here the trick we define an interface in base module responsible for
// between other not related modules navigation
interface ToSetupAccountNavigator {

    fun moveToSetupAccount(context: Context)
}
