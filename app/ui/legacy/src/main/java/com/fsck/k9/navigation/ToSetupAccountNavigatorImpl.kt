package com.fsck.k9.navigation

import android.content.Context
import com.fsck.k9.activity.setup.AccountSetupBasics
import com.fsck.k9.ui.base.navigation.ToSetupAccountNavigator

class ToSetupAccountNavigatorImpl : ToSetupAccountNavigator {

    override fun moveToSetupAccount(context: Context, username: String) {
        context.run { AccountSetupBasics.startActivityWithEmailSet(this, username) }
    }
}
