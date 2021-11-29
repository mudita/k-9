package com.mudita.mail.ui.usecase.signIn.navigator

import com.mudita.mail.ui.navigation.navigator.Navigator

interface SignInNavigator : Navigator {

    fun moveToEmailScreen(providerTypeName: String)
}
