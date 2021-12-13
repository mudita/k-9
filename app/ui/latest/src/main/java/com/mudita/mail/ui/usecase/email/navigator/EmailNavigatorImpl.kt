package com.mudita.mail.ui.usecase.email.navigator

import android.content.Context
import com.fsck.k9.ui.base.navigation.ToSetupAccountNavigator

class EmailNavigatorImpl(
    private val context: Context,
    private val toSetupAccountNavigator: ToSetupAccountNavigator
) : EmailNavigator {

    override fun moveToAccountSetupChecks(email: String) {
        toSetupAccountNavigator.moveToSetupAccount(context, email)
    }
}
