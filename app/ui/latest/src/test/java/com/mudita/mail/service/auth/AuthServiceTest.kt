package com.mudita.mail.service.auth

import android.net.Uri
import com.mudita.mail.MuditaRobolectricTest
import com.mudita.mail.repository.providers.model.ProviderType
import com.mudita.mail.service.auth.config.AuthConfigService
import kotlin.test.fail
import org.junit.Before
import org.junit.Test

class AuthServiceTest : MuditaRobolectricTest() {

    private lateinit var authService: AuthService
    private lateinit var authConfigService: AuthConfigService

    val googleAuthEndpoint = "https://accounts.google.com/o/oauth2/auth"
    val googleTokenEndpoint = "https://oauth2.googleapis.com/token"

    @Before
    fun setup() {
        authService = getKoin().get()
        authConfigService = getKoin().get()
    }

    @Test
    fun `getting auth intent with gmail config should result in google oauth intent`() {
        val config = authConfigService.getAuthConfigForProviderType(ProviderType.GMAIL).getOrThrow()
        val googleRequestAuthUri = Uri.parse(googleAuthEndpoint)
        val requestData = authService.getAuthRequestData(config)

        fail()
    }

    @Test
    fun `getting auth intent with outlook config should result in outlook oauth intent`() {
        fail()
    }

    @Test
    fun `building request data with invalid input data should result in failure return value`() {
        fail()
    }

    @Test
    fun `processing gmail auth response data should result in success result with gmail accounts email`() {
        fail()
    }

    @Test
    fun `processing outlook auth response data should result in success result with outlook accounts email`() {
        fail()
    }

    @Test
    fun `processing gmail auth response data should result in auth response saved to storage and accessible further`() {
        fail()
    }

    @Test
    fun `processing outlook auth response data should result in auth response saved to storage and accessible further`() {
        fail()
    }

    @Test
    fun `processing response intent with request exception should result in failure return with exception`() {
        fail()
    }

    @Test
    fun `processing response with code for token response error should result in failure return with exception`() {
        fail()
    }
}
