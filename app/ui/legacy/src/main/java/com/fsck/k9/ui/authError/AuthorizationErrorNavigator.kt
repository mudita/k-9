package com.fsck.k9.ui.authError

import com.fsck.k9.Account

interface AuthorizationErrorNavigator {

    fun moveToMessageList()

    fun moveToEditIncomingServerSettings(account: Account)

    fun moveToEditOutgoingServerSettings(account: Account)

    fun moveToSignInScreen()
}