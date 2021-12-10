package com.mudita.mail.service.auth

import android.content.Intent
import android.net.Uri
import com.mudita.mail.MuditaRobolectricTest
import com.mudita.mail.repository.auth.session.AuthSessionRepository
import com.mudita.mail.repository.providers.model.ProviderType
import com.mudita.mail.service.api.email.EmailApiClientService
import com.mudita.mail.service.auth.AuthRequestData.Companion.toAuthResponseData
import com.mudita.mail.service.auth.config.AuthConfigService
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue
import kotlinx.coroutines.runBlocking
import net.openid.appauth.AuthState
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationService
import net.openid.appauth.TokenRequest
import net.openid.appauth.TokenResponse
import org.json.JSONObject
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.test.mock.declare

class AuthServiceTest : MuditaRobolectricTest() {

    private lateinit var authService: AuthService
    private lateinit var authConfigService: AuthConfigService

    private val googleAuthEndpoint = "https://accounts.google.com/o/oauth2/auth"
    private val outlookAuthEndpoint = "https://login.microsoftonline.com/common/oauth2/v2.0/authorize"

    private val openIdIntent = "net.openid.appauth.AuthorizationResponse"

    private val testResponse = """
        {
          "request": {
            "configuration": {
              "authorizationEndpoint": "https:\/\/accounts.google.com\/o\/oauth2\/auth",
              "tokenEndpoint": "https:\/\/oauth2.googleapis.com\/token"
            },
            "clientId": "test_client_id",
            "responseType": "code",
            "redirectUri": "https:\/\/mail.google.com\/",
            "scope": "https:\/\/mail.google.com\/ https:\/\/www.googleapis.com\/auth\/userinfo.email",
            "state": "test_state",
            "nonce": "test_nonce",
            "codeVerifier": "code_verifiercode_verifiercode_verifiercode_verifiercode_verifier",
            "codeVerifierChallenge": "code_verifier_challange",
            "codeVerifierChallengeMethod": "code_method",
            "additionalParameters": {}
          },
          "state": "code_verifiercode_verifier",
          "code": "code",
          "scope": "email https:\/\/mail.google.com\/ https:\/\/www.googleapis.com\/auth\/userinfo.email openid",
          "additional_parameters": {
            "authuser": "1",
            "prompt": "consent"
          }
        }
    """.trimIndent()

    private val validMockToken = "ValidAccessToken"
    private val invalidMockToken = "InvalidToken"

    private val testGmailEmail = "testemail@gmail.com"
    private val testOutlookEmail = "testemail@outlook.com"

    private lateinit var mockAuthSessionRepo: AuthSessionRepository

    private lateinit var tokenRequest: TokenRequest

    @Before
    fun setup() {
        tokenRequest = mockk(relaxed = true) {
            every { jsonSerialize() } returns JSONObject()
        }

        declare<AuthorizationService> {
            mockk(relaxed = true) {
                every { getAuthorizationRequestIntent(any()) } returns Intent()
                every { performTokenRequest(any(), any()) } answers {
                    secondArg<AuthorizationService.TokenResponseCallback>().onTokenRequestCompleted(
                        TokenResponse.Builder(tokenRequest)
                            .setAccessToken(validMockToken).build(),
                        null
                    )
                }
            }
        }

        declare<EmailApiClientService> {
            mockk(relaxed = true) {
                coEvery { getEmail(ProviderType.GMAIL, validMockToken) } returns Result.success(testGmailEmail)
                coEvery { getEmail(ProviderType.OUTLOOK, validMockToken) } returns Result.success(testOutlookEmail)
                coEvery {
                    getEmail(
                        ProviderType.GMAIL,
                        invalidMockToken
                    )
                } returns Result.failure(Exception())
            }
        }

        mockAuthSessionRepo = mockk {
            mockk(relaxed = true) {
                every { saveAuthSessionData(any(), any()) } returns Unit
            }
        }
        declare {
            mockAuthSessionRepo
        }

        authService = getKoin().get()
        authConfigService = getKoin().get()
    }

    @Test
    fun `getting auth intent with gmail config should result in google oauth intent`() {
        val config = authConfigService.getAuthConfigForProviderType(ProviderType.GMAIL).getOrThrow()
        val googleRequestAuthUri = Uri.parse(googleAuthEndpoint)

        val requestData = authService.getAuthRequestData(config).getOrThrow()

        val responseData = requestData.toAuthResponseData(Intent())

        assertEquals(
            googleRequestAuthUri,
            responseData.authState.authorizationServiceConfiguration?.authorizationEndpoint
        )
    }

    @Test
    fun `getting auth intent with outlook config should result in outlook oauth intent`() {
        val config = authConfigService.getAuthConfigForProviderType(ProviderType.OUTLOOK).getOrThrow()
        val outlookRequestUri = Uri.parse(outlookAuthEndpoint)

        val requestData = authService.getAuthRequestData(config).getOrThrow()

        val responseData = requestData.toAuthResponseData(Intent())

        assertEquals(
            outlookRequestUri,
            responseData.authState.authorizationServiceConfiguration?.authorizationEndpoint
        )
    }

    @Test
    fun `building request data with invalid input data should result in failure return value`() {
        val requestData = authConfigService.getAuthConfigForProviderType(ProviderType.MANUAL)
        assertTrue(requestData.isFailure)
        assertFailsWith(IllegalArgumentException::class) {
            requestData.getOrThrow()
        }
    }

    @Test
    fun `processing gmail auth response data should result in success result with gmail accounts email`() {
        val expectedEmail = testGmailEmail
        runBlocking {
            val email = authService.processAuthResponseData(
                ProviderType.GMAIL,
                AuthResponseData(
                    Intent().apply { putExtra(openIdIntent, testResponse) },
                    AuthState()
                )
            ).getOrThrow()
            assertEquals(expectedEmail, email)
        }
    }

    @Test
    fun `processing outlook auth response data should result in success result with outlook accounts email`() {
        val expectedEmail = testOutlookEmail
        runBlocking {
            val email = authService.processAuthResponseData(
                ProviderType.OUTLOOK,
                AuthResponseData(
                    Intent().apply { putExtra(openIdIntent, testResponse) },
                    AuthState()
                )
            ).getOrThrow()
            assertEquals(expectedEmail, email)
        }
    }

    @Test
    fun `processing gmail auth response data should result in auth response saved to storage and accessible further`() {
        val expectedEmail = testGmailEmail
        runBlocking {
            val email = authService.processAuthResponseData(
                ProviderType.GMAIL,
                AuthResponseData(
                    Intent().apply { putExtra(openIdIntent, testResponse) },
                    AuthState()
                )
            ).getOrThrow()
            assertEquals(expectedEmail, email)
            verify(exactly = 1) { mockAuthSessionRepo.saveAuthSessionData(expectedEmail, any()) }
        }
    }

    @Test
    fun `processing outlook auth response data should result in auth response saved to storage and accessible further`() {
        val expectedEmail = testOutlookEmail
        val providerType = ProviderType.OUTLOOK

        runBlocking {
            val email = authService.processAuthResponseData(
                providerType,
                AuthResponseData(
                    Intent().apply { putExtra(openIdIntent, testResponse) },
                    AuthState()
                )
            ).getOrThrow()
            assertEquals(expectedEmail, email)
            verify(exactly = 1) { mockAuthSessionRepo.saveAuthSessionData(expectedEmail, any()) }
        }
    }

    @Test
    fun `processing response intent with request exception should result in failure return with exception`() {
        val expectedEmail = testGmailEmail
        declare<AuthorizationService> {
            mockk(relaxed = true) {
                every { getAuthorizationRequestIntent(any()) } returns Intent()
                every { performTokenRequest(any(), any()) } answers {
                    secondArg<AuthorizationService.TokenResponseCallback>().onTokenRequestCompleted(
                        TokenResponse.Builder(tokenRequest)
                            .setAccessToken(invalidMockToken).build(),
                        null
                    )
                }
            }
        }
        authService = getKoin().get()
        runBlocking {
            val emailResult = authService.processAuthResponseData(
                ProviderType.GMAIL,
                AuthResponseData(
                    Intent().apply { putExtra(openIdIntent, testResponse) },
                    AuthState()
                )
            )
            assertTrue { emailResult.isFailure }
            verify(exactly = 0) { mockAuthSessionRepo.saveAuthSessionData(expectedEmail, any()) }
        }
    }

    @Test
    fun `processing response with code for token response error should result in failure return with exception`() {
        mockkStatic(AuthorizationException::class) {
            every { AuthorizationException.fromIntent(any()) } returns AuthorizationException(3, 3, "", "", null, null)
        }
        val expectedEmail = testOutlookEmail
        runBlocking {
            val emailResult = authService.processAuthResponseData(
                ProviderType.GMAIL,
                AuthResponseData(
                    Intent(),
                    AuthState()
                )
            )
            assertTrue { emailResult.isFailure }
            verify(exactly = 0) { mockAuthSessionRepo.saveAuthSessionData(expectedEmail, any()) }
        }
    }

    @After
    fun tearDown() {
        getKoin().get<AuthSessionRepository>().removeAuthSession(testGmailEmail)
        getKoin().get<AuthSessionRepository>().removeAuthSession(testOutlookEmail)
    }
}
