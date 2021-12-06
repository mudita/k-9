package com.mudita.mail.service.auth.config

import com.mudita.mail.MuditaRobolectricTest
import com.mudita.mail.repository.providers.model.ProviderType
import kotlin.test.assertTrue
import org.junit.Before
import org.junit.Test

class AuthConfigServiceTest : MuditaRobolectricTest() {

    private lateinit var authConfigService: AuthConfigService

    @Before
    fun setup() {
        authConfigService = getKoin().get()
    }

    @Test
    fun `call with gmail provider type should result in gmail auth config wrapped in success result`() {
        val gmailProvider = ProviderType.GMAIL

        val configResult = authConfigService.getAuthConfigForProviderType(gmailProvider)

        assertTrue { configResult.isSuccess }
    }

    @Test
    fun `call with gmail provider type should result in gmail auth config with non null client id`() {
        val gmailProvider = ProviderType.GMAIL

        val configResult = authConfigService.getAuthConfigForProviderType(gmailProvider)

        assertTrue { configResult.getOrNull()?.clientId?.isNotEmpty() ?: false }
    }

    @Test
    fun `call with not support by ouath provider type should result in failure return type`() {
        val manualProvider = ProviderType.MANUAL

        val configResult = authConfigService.getAuthConfigForProviderType(manualProvider)

        assertTrue { configResult.isFailure }
    }
}
