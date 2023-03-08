package com.mudita.mail.ui.usecase.signIn.navigator

import android.content.Context
import androidx.navigation.NavController
import com.fsck.k9.ui.base.navigation.ToSetupAccountNavigator
import com.mudita.mail.ui.navigation.EmailDestination

class SignInScreenNavigatorImpl(
    private val navController: NavController,
    private val context: Context,
    private val toSetupAccountNavigator: ToSetupAccountNavigator
) : SignInNavigator {

    override fun moveToAccountSetupChecks(email: String, domain: String?) =
        toSetupAccountNavigator.moveToSetupAccount(context, email, domain)

    override fun moveToManualAccountSetup() =
        toSetupAccountNavigator.moveToManualAccountSetup(context)

    override fun moveToAppSpecificPasswordSetup() {
        navController.navigate(EmailDestination.toRoute())
    }
}
