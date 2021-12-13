package com.mudita.mail.provider

import com.fsck.k9.mail.oauth.OAuth2TokenProvider
import com.mudita.mail.service.auth.session.AuthSessionService

class OAuth2TokenProviderImpl(
    private val authSessionService: AuthSessionService,
) : OAuth2TokenProvider {

    override fun getToken(username: String, timeoutMillis: Long) =
        authSessionService.getToken(username)

    override fun invalidateToken(username: String) =
        authSessionService.invalidateToken(username)

    override fun refreshToken(username: String) =
        authSessionService.refreshToken(username)
}
