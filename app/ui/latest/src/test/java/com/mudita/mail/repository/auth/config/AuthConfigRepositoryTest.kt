package com.mudita.mail.repository.auth.config

import com.mudita.mail.MuditaRobolectricTest
import com.mudita.mail.repository.providers.model.ProviderType
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue
import org.junit.Before
import org.junit.Test
import org.robolectric.RuntimeEnvironment

class AuthConfigRepositoryTest : MuditaRobolectricTest() {

    private lateinit var authConfigRepository: AuthConfigRepository

    @Before
    fun setup() {
        authConfigRepository = PredefinedAuthConfigRepository(
            RuntimeEnvironment.getApplication().applicationContext
        )
    }

    @Test
    fun `repository should return gmail auth config for provider type name predicate`() {
        val gmailProvider = ProviderType.GMAIL
        val predicate: (AuthConfig) -> Boolean = {
            it.clientName == gmailProvider.name
        }
        val authConfig = authConfigRepository.getAuthConfig(predicate)

        assertEquals(gmailProvider.name, authConfig?.clientName)
    }

    @Test
    fun `repository should return not empty client id for gmail predicate`() {
        val gmailProvider = ProviderType.GMAIL
        val predicate: (AuthConfig) -> Boolean = {
            it.clientName == gmailProvider.name
        }
        val authConfig = authConfigRepository.getAuthConfig(predicate)

        assertTrue { authConfig?.clientId?.isNotEmpty() ?: false }
    }

    @Test
    fun `repository should return not empty redirect uri for gmail repository`() {
        val gmailProvider = ProviderType.GMAIL
        val predicate: (AuthConfig) -> Boolean = {
            it.clientName == gmailProvider.name
        }
        val authConfig = authConfigRepository.getAuthConfig(predicate)

        assertTrue { authConfig?.redirectUrl?.isNotEmpty() ?: false }
    }

    @Test
    fun `repository should return outlook auth config for provider name predicate`() {
        val outlookProvider = ProviderType.OUTLOOK
        val predicate: (AuthConfig) -> Boolean = {
            it.clientName == outlookProvider.name
        }
        val authConfig = authConfigRepository.getAuthConfig(predicate)

        assertEquals(outlookProvider.name, authConfig?.clientName)
    }

    @Test
    fun `repository should return not empty client id for outlook predicate`() {
        val outlookProvider = ProviderType.OUTLOOK
        val predicate: (AuthConfig) -> Boolean = {
            it.clientName == outlookProvider.name
        }
        val authConfig = authConfigRepository.getAuthConfig(predicate)

        assertTrue { authConfig?.clientId?.isNotEmpty() ?: false }
    }

    @Test
    fun `repository should return not empty redirect uri for outlook repository`() {
        val outlookProvider = ProviderType.OUTLOOK
        val predicate: (AuthConfig) -> Boolean = {
            it.clientName == outlookProvider.name
        }
        val authConfig = authConfigRepository.getAuthConfig(predicate)

        assertTrue { authConfig?.redirectUrl?.isNotEmpty() ?: false }
    }

    @Test
    fun `repository should return null auth config when cannot find config by given predicate`() {
        val outlookProvider = ProviderType.OUTLOOK
        val predicate: (AuthConfig) -> Boolean = {
            it.redirectUrl == outlookProvider.name
        }

        val authConfig = authConfigRepository.getAuthConfig(predicate)

        assertNull(authConfig)
    }

    @Test
    fun `repository should return null auth config for non auth provider type`() {
        val manualProvider = ProviderType.MANUAL
        val predicate: (AuthConfig) -> Boolean = {
            it.clientName == manualProvider.name
        }

        val authConfig = authConfigRepository.getAuthConfig(predicate)

        assertNull(authConfig)
    }
}