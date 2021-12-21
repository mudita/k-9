package com.mudita.mail.ui.usecase.email.navigator

import android.content.Context
import androidx.navigation.NavController
import com.fsck.k9.ui.base.navigation.ToSetupAccountNavigator

class EmailNavigatorImpl(
    private val navController: NavController,
    private val context: Context,
    private val toSetupAccountNavigator: ToSetupAccountNavigator
) : EmailNavigator {

    override fun moveBack() {
        navController.navigateUp()
    }

    override fun moveToAccountSetupChecks(email: String, password: String) =
        toSetupAccountNavigator.moveToSetupAccountWithPassword(context, email, password)
}
