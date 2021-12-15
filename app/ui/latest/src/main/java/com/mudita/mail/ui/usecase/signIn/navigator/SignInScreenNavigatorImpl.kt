package com.mudita.mail.ui.usecase.signIn.navigator

import android.content.Context
import com.fsck.k9.ui.base.navigation.ToSetupAccountNavigator

class SignInScreenNavigatorImpl(
    private val context: Context,
    private val toSetupAccountNavigator: ToSetupAccountNavigator
) : SignInNavigator {

    override fun moveToAccountSetupChecks(email: String) {
        toSetupAccountNavigator.moveToSetupAccount(context, email)
    }
}
