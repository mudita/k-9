package com.fsck.k9.navigation

import android.content.Context
import android.content.Intent
import com.fsck.k9.activity.setup.AccountSetupBasics
import com.fsck.k9.ui.base.navigation.ToSetupAccountNavigator

class ToSetupAccountNavigatorImpl : ToSetupAccountNavigator {

    override fun moveToSetupAccount(context: Context, username: String, rootDomain: String?) {
        AccountSetupBasics.startActivityWithEmailSet(context, username, rootDomain)
    }

    override fun moveToManualAccountSetup(context: Context) {
        context.run { startActivity(Intent(this, AccountSetupBasics::class.java)) }
    }

    override fun moveToSetupAccountWithPassword(context: Context, username: String, password: String) {
        AccountSetupBasics.startActivityWithEmailAndPasswordSet(context, username, password)
    }
}
