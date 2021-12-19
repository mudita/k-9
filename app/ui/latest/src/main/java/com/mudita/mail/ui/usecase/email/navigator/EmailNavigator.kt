package com.mudita.mail.ui.usecase.email.navigator

import com.mudita.mail.ui.navigation.navigator.Navigator

interface EmailNavigator : Navigator {

    fun moveBack()

    fun moveToAccountSetupChecks(email: String, password: String)
}