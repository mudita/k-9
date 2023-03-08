package com.fsck.k9.ui.base.navigation

import android.content.Context

interface ToSetupAccountNavigator {

    fun moveToSetupAccount(context: Context, username: String, rootDomain: String?)

    fun moveToManualAccountSetup(context: Context)

    fun moveToSetupAccountWithPassword(context: Context, username: String, password: String)
}
