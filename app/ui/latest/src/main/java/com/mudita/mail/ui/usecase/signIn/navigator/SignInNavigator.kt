package com.mudita.mail.ui.usecase.signIn.navigator

import com.mudita.mail.ui.navigation.navigator.Navigator

interface SignInNavigator : Navigator {

    fun moveToAccountSetupChecks(email: String, domain: String?)

    fun moveToManualAccountSetup()

    fun moveToAppSpecificPasswordSetup()
}
