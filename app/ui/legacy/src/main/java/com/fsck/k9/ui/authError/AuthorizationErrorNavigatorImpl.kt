package com.fsck.k9.ui.authError

import android.content.Context
import com.fsck.k9.Account
import com.fsck.k9.activity.MessageList
import com.fsck.k9.activity.setup.AccountSetupIncoming
import com.fsck.k9.activity.setup.AccountSetupOutgoing
import com.mudita.mail.ui.SignInActivity

class AuthorizationErrorNavigatorImpl(
    private val context: Context,
) : AuthorizationErrorNavigator {

    override fun moveToMessageList() = MessageList.launch(context)

    override fun moveToEditIncomingServerSettings(account: Account) =
        AccountSetupIncoming.launchEditIncomingSettings(context, account.uuid)

    override fun moveToEditOutgoingServerSettings(account: Account) =
        AccountSetupOutgoing.launchEditOutgoingSettings(context, account.uuid)

    override fun moveToSignInScreen() = SignInActivity.launch(context)
}