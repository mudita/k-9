package com.mudita.mail.ui.usecase.add.navigator

interface AddAccountNavigator {

    fun moveToAccountSetupChecks(email: String, domain: String?)

    fun moveToManualAccountSetup()

    fun moveToAppSpecificPasswordSetup()

    fun moveBack()
}
