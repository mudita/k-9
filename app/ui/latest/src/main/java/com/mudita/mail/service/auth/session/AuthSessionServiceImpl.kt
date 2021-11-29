package com.mudita.mail.service.auth.session

import com.mudita.mail.repository.auth.session.AuthSessionData
import com.mudita.mail.repository.auth.session.AuthSessionRepository
import net.openid.appauth.AuthorizationService

class AuthSessionServiceImpl(
    private val authSessionRepository: AuthSessionRepository,
    private val authorizationService: AuthorizationService
) : AuthSessionService {

    override fun refreshToken(username: String) {
        val authState = authSessionRepository.getAuthSessionData(username).authState
        authState.performActionWithFreshTokens(
            authorizationService
        ) { _, _, authorizationException ->
            if (authorizationException != null) {
                authSessionRepository.saveAuthSessionData(AuthSessionData(authState, username))
            }
            return@performActionWithFreshTokens
        }
    }

    override fun invalidateToken(username: String) {
        // TODO(Not-implemented) need to reason how to do it, because after analysis of existing tests
        // invalidating tokens means new one is available in place of previous
    }

    override fun getToken(username: String): String? =
        authSessionRepository.getAuthSessionData(username).authState.accessToken
}
