package com.mudita.mail.repository.auth.session

import android.net.Uri
import com.mudita.mail.MuditaRobolectricTest
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import net.openid.appauth.AuthState
import net.openid.appauth.AuthorizationServiceConfiguration
import org.junit.Before
import org.junit.Test
import org.robolectric.RuntimeEnvironment

class AuthSessionRepositoryTest : MuditaRobolectricTest() {

    private lateinit var authSessionRepository: AuthSessionRepository

    private val testUser = "testemailvalid@mail.com"
    private val testAuthSession = AuthSessionData(AuthState())

    @Before
    fun setup() {
        authSessionRepository = SharedPrefsAuthSessionRepository(RuntimeEnvironment.getApplication().applicationContext)
        authSessionRepository.saveAuthSessionData(testUser, testAuthSession)
    }

    @Test
    fun `repository should return null for not existing user`() {
        val username = "testemail@mail.com"

        val sessionData = authSessionRepository.getAuthSessionData(username)

        assertNull(sessionData)
    }

    @Test
    fun `repository should return auth session data with filled auth state for existing user`() {
        val username = testUser

        val sessionData = authSessionRepository.getAuthSessionData(username)

        assertNotNull(sessionData)
    }

    @Test
    fun `retrieved auth session data changed from null to something after creation`() {
        val username = "testuser@emial.com"
        val authSessionData = AuthSessionData(AuthState())

        val nullSessionData = authSessionRepository.getAuthSessionData(username)

        assertNull(null)

        val sessionData = authSessionRepository.saveAuthSessionData(username, authSessionData)

        assertNotNull(sessionData)
    }

    @Test
    fun `test auth session should change after property updates`() {
        val username = testUser

        // Created with no service config in @Before
        val authSessionData = testAuthSession

        val retrievedAuthSessionData = authSessionRepository.getAuthSessionData(username)

        assertNull(retrievedAuthSessionData?.authState?.authorizationServiceConfiguration?.authorizationEndpoint)
        assertEquals(
            authSessionData.authState.authorizationServiceConfiguration?.authorizationEndpoint,
            retrievedAuthSessionData?.authState?.authorizationServiceConfiguration?.authorizationEndpoint
        )

        // Create auth state with filled service config
        val testServiceConfigEndpoint = "testServiceEndpoint"

        val serviceConfig = AuthorizationServiceConfiguration(
            Uri.parse(testServiceConfigEndpoint),
            Uri.parse(testServiceConfigEndpoint)
        )

        val filledAuthState = AuthState(serviceConfig)

        // Update config for user
        authSessionRepository.saveAuthSessionData(username, AuthSessionData(filledAuthState))

        // get updated config
        val filledRetrievedAuthSessionData = authSessionRepository.getAuthSessionData(username)

        assertNotEquals(authSessionData.authState, filledRetrievedAuthSessionData?.authState)
        assertEquals(
            Uri.parse(testServiceConfigEndpoint),
            filledRetrievedAuthSessionData?.authState?.authorizationServiceConfiguration?.authorizationEndpoint
        )
    }
}