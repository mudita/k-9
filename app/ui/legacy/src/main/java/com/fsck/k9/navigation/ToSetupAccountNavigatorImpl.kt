package com.fsck.k9.navigation

import android.content.Context
import android.content.Intent
import com.fsck.k9.activity.setup.AccountSetupBasics
import com.fsck.k9.ui.base.navigation.ToSetupAccountNavigator

class ToSetupAccountNavigatorImpl : ToSetupAccountNavigator {

    override fun moveToSetupAccount(context: Context) {
        context.run { startActivity(Intent(this, AccountSetupBasics::class.java)) }
    }
}
