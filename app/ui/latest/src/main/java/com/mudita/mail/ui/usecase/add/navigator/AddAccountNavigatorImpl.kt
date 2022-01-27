package com.mudita.mail.ui.usecase.add.navigator

import android.content.Context
import androidx.navigation.NavController
import com.fsck.k9.ui.base.navigation.ToSetupAccountNavigator
import com.mudita.mail.ui.navigation.EmailDestination
import com.mudita.mail.ui.util.BackPressHandler

class AddAccountNavigatorImpl(
    private val navController: NavController,
    private val context: Context,
    private val backPressHandler: BackPressHandler,
    private val toSetupAccountNavigator: ToSetupAccountNavigator
) : AddAccountNavigator {

    override fun moveToAccountSetupChecks(email: String, domain: String?) =
        toSetupAccountNavigator.moveToSetupAccount(context, email, domain)

    override fun moveToManualAccountSetup() =
        toSetupAccountNavigator.moveToManualAccountSetup(context)

    override fun moveToAppSpecificPasswordSetup() {
        navController.navigate(EmailDestination.toRoute())
    }

    override fun moveBack() = backPressHandler.moveBack()
}
