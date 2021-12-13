package com.mudita.mail.service.auth

import android.content.Intent
import net.openid.appauth.AuthState

data class AuthResponseData(
    val intent: Intent,
    val authState: AuthState
)
