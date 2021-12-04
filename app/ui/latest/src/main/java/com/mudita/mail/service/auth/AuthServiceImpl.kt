package com.mudita.mail.service.auth

import android.net.Uri
import com.fsck.k9.mail.AuthenticationFailedException
import com.mudita.mail.repository.auth.config.AuthConfig
import com.mudita.mail.repository.auth.session.AuthSessionData
import com.mudita.mail.repository.auth.session.AuthSessionRepository
import com.mudita.mail.service.api.email.EmailApiClientService
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import net.openid.appauth.AuthState
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationResponse
import net.openid.appauth.AuthorizationService
import net.openid.appauth.AuthorizationServiceConfiguration
import net.openid.appauth.TokenResponse

class AuthServiceImpl(
    private val authorizationService: AuthorizationService,
    private val emailApiClientService: EmailApiClientService,
    private val authSessionRepository: AuthSessionRepository
) : AuthService {

    override fun getAuthIntentData(
        authConfig: AuthConfig
    ): AuthRequestData {

        val serviceConfig = AuthorizationServiceConfiguration(
            Uri.parse(authConfig.authEndpoint),
            Uri.parse(authConfig.tokenEndpoint)
        )

        val authRequestBuilder = AuthorizationRequest.Builder(
            serviceConfig,
            authConfig.clientId,
            authConfig.responseType.value,
            Uri.parse(authConfig.redirectUrl)
        ).setScopes(authConfig.scopes)

        val intent = authorizationService.getAuthorizationRequestIntent(authRequestBuilder.build())
        val authState = AuthState(serviceConfig)

        return AuthRequestData(
            intent,
            authState
        )
    }

    override suspend fun processAuthResponseData(authResponseData: AuthResponseData): String? {
        val intent = authResponseData.intent
        val authState = authResponseData.authState

        val authorizationResponse = AuthorizationResponse.fromIntent(intent)
        val authorizationException = AuthorizationException.fromIntent(intent)

        authState.update(authorizationResponse, authorizationException)

        authorizationResponse ?: return null

        val (tokenResponse, tokenAuthorizationException) = try {
            performTokenRequest(authorizationResponse)
        } catch (e: AuthorizationException) {
            return null
        }

        authState.update(tokenResponse, tokenAuthorizationException)

        val token = authState.accessToken ?: return null

        val email = try {
            emailApiClientService.getEmail(token).getOrThrow()
        } catch (e: Exception) {
            return null
        }

        authSessionRepository.saveAuthSessionData(email, AuthSessionData(authState))
        return email
    }

    private suspend fun performTokenRequest(
        authorizationResponse: AuthorizationResponse
    ) = suspendCoroutine<Pair<TokenResponse?, AuthorizationException?>> { continuation ->
        authorizationService.performTokenRequest(
            authorizationResponse.createTokenExchangeRequest()
        ) { tokenResponse, tokenAuthorizationException ->
            when {
                tokenResponse != null -> {
                    continuation.resume(tokenResponse to tokenAuthorizationException)
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
}
