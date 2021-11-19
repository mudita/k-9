package com.fsck.k9.ui.setup.ui.usecase.signIn.navigator

import android.content.Context
import com.fsck.k9.ui.base.navigation.ToSetupAccountNavigator

class SignInScreenNavigatorImpl(
    private val context: Context,
    private val toSetupAccountNavigator: ToSetupAccountNavigator
) : SignInNavigator {

    override fun moveToCredentials() = toSetupAccountNavigator.moveToSetupAccount(context)
}
