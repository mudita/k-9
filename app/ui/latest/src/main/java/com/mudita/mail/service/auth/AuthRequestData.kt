package com.mudita.mail.service.auth

import android.content.Intent
import net.openid.appauth.AuthState

data class AuthRequestData(
    val intent: Intent,
    private val authState: AuthState
) {

    companion object {

        fun AuthRequestData.toAuthResponseData(responseIntent: Intent) =
            AuthResponseData(responseIntent, authState)
    }
}
