package com.mudita.mail.service.auth.intent

import android.content.Intent
import android.net.Uri
import com.fsck.k9.mail.AuthenticationFailedException
import com.mudita.mail.repository.auth.config.AuthConfig
import com.mudita.mail.repository.auth.session.AuthSessionData
import com.mudita.mail.repository.auth.session.AuthSessionRepository
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import net.openid.appauth.AuthState
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationResponse
import net.openid.appauth.AuthorizationService
import net.openid.appauth.AuthorizationServiceConfiguration

class AuthIntentServiceImpl(
    private val authorizationService: AuthorizationService,
    private val authSessionRepository: AuthSessionRepository
) : AuthIntentService {

    override fun getAuthIntent(
        username: String,
        authConfig: AuthConfig
    ): Intent {

        val serviceConfig = AuthorizationServiceConfiguration(
            Uri.parse(authConfig.authEndpoint),
            Uri.parse(authConfig.tokenEndpoint)
        )

        val authState = AuthState(serviceConfig)

        authSessionRepository.saveAuthSessionData(AuthSessionData(authState, username))

        val authRequestBuilder = AuthorizationRequest.Builder(
            serviceConfig,
            authConfig.clientId,
            authConfig.responseType.value,
            Uri.parse(authConfig.redirectUrl)
        ).setScope(authConfig.scope)

        return authorizationService.getAuthorizationRequestIntent(authRequestBuilder.build())
    }

    override suspend fun processResponseAuthIntent(username: String, intent: Intent) =
        suspendCoroutine<Unit> { continuation ->
            val authorizationResponse = AuthorizationResponse.fromIntent(intent)
            val authorizationException = AuthorizationException.fromIntent(intent)

            val authState = authSessionRepository.getAuthSessionData(username).authState
            authState.update(authorizationResponse, authorizationException)

            when {
                authorizationResponse?.authorizationCode != null -> {
                    authorizationService.performTokenRequest(
                        authorizationResponse.createTokenExchangeRequest()
                    ) { tokenResponse, tokenAuthorizationException ->
                        when {

                            tokenResponse != null -> {
                                authState.update(tokenResponse, tokenAuthorizationException)
                                authSessionRepository.saveAuthSessionData(
                                    AuthSessionData(
                                        authState,
                                        username
                                    )
                                )
                                continuation.resume(Unit)
                            }
                            tokenAuthorizationException != null -> continuation.resumeWithException(
                                tokenAuthorizationException
                            )
                            else -> continuation.resumeWithException(
                                AuthenticationFailedException("Error while exchanging code for token")
                            )
                        }
                    }
                }
                authorizationException != null -> continuation.resumeWithException(authorizationException)
                else -> continuation.resumeWithException(
                    AuthenticationFailedException("Auth response or it's code is null")
                )
            }
        }
}
