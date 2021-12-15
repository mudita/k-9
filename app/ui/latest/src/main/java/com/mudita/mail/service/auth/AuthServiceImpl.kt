package com.mudita.mail.service.auth

import android.net.Uri
import com.fsck.k9.Preferences
import com.fsck.k9.mail.AuthenticationFailedException
import com.mudita.mail.repository.auth.config.AuthConfig
import com.mudita.mail.repository.auth.session.AuthSessionData
import com.mudita.mail.repository.auth.session.AuthSessionRepository
import com.mudita.mail.repository.providers.model.ProviderType
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
    private val authSessionRepository: AuthSessionRepository,
    private val preferences: Preferences
) : AuthService {

    override fun getAuthRequestData(
        authConfig: AuthConfig
    ): Result<AuthRequestData> {

        val serviceConfig = AuthorizationServiceConfiguration(
            Uri.parse(authConfig.authEndpoint),
            Uri.parse(authConfig.tokenEndpoint)
        )

        val authRequestBuilder = try {
            AuthorizationRequest.Builder(
                serviceConfig,
                authConfig.clientId,
                authConfig.responseType.value,
                Uri.parse(authConfig.redirectUrl)
            )
                .setScopes(authConfig.scopes)
                .setPrompt(authConfig.prompt)
        } catch (e: IllegalArgumentException) {
            return Result.failure(e)
        }

        val intent = authorizationService.getAuthorizationRequestIntent(authRequestBuilder.build())
        val authState = AuthState(serviceConfig)

        return Result.success(
            AuthRequestData(
                intent,
                authState
            )
        )
    }

    override suspend fun processAuthResponseData(
        providerType: ProviderType,
        authResponseData: AuthResponseData
    ): Result<String> {
        val intent = authResponseData.intent
        val authState = authResponseData.authState

        val authorizationResponse = AuthorizationResponse.fromIntent(intent)
        val authorizationException = AuthorizationException.fromIntent(intent)

        if (authorizationException != null) {
            return Result.failure(authorizationException)
        }

        authorizationResponse ?: return Result.failure(Exception())

        authState.update(authorizationResponse, authorizationException)

        val (tokenResponse, tokenAuthorizationException) = try {
            performTokenRequest(authorizationResponse)
        } catch (e: AuthorizationException) {
            return Result.failure(e)
        }

        authState.update(tokenResponse, tokenAuthorizationException)

        val token = authState.accessToken ?: return Result.failure(Exception())

        val email = emailApiClientService.getEmail(providerType, token).getOrElse { return Result.failure(it) }

        val account = preferences.accounts.find { it.email == email }

        if (account != null && account.isFinishedSetup) {
            authSessionRepository.removeAuthSession(email)
            return Result.failure(IllegalArgumentException("Selected user already exists"))
        }

        authSessionRepository.saveAuthSessionData(email, AuthSessionData(authState))
        return Result.success(email)
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
