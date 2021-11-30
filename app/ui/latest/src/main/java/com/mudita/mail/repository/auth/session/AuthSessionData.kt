package com.mudita.mail.repository.auth.session

import net.openid.appauth.AuthState

data class AuthSessionData(
    val authState: AuthState
)
