package com.mudita.mail.service.api.email

import com.mudita.mail.MuditaRobolectricTest
import com.mudita.mail.repository.providers.model.ProviderType
import com.mudita.mail.service.api.client.ApiClientService
import com.mudita.mail.service.api.email.strategy.EmailApiContext
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.respondError
import io.ktor.client.features.ClientRequestException
import io.ktor.client.features.HttpRequestTimeoutException
import io.ktor.client.features.HttpTimeout
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.fullPath
import io.ktor.http.headersOf
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.junit.Before
import org.junit.Test

class EmailApiServiceTest : MuditaRobolectricTest() {

    private lateinit var apiClientService: ApiClientService
    private lateinit var emailApiClientService: EmailApiClientService
    private lateinit var emailApiContext: EmailApiContext

    private val validToken = "validToken"
    private val invalidToken = "invalidToken"
    private val timeoutToken = "timeoutToken"

    private val testEmail = "testemail@email.com"

    private val testEmailResponse = """
        {
          "email" : "testemail@email.com"
        }
    """.trimIndent()

    private val mockEngine = MockEngine { request ->
        val encodedPath = request.url.fullPath

        when {
            encodedPath.contains("/oauth2/v2/userinfo?access_token=$validToken") -> respond(
                content = testEmailResponse,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
            encodedPath.contains("/oauth2/v2/userinfo?access_token=$timeoutToken") -> {
                delay(1500)
                respond(
                    content = testEmailResponse,
                    status = HttpStatusCode.OK,
                    headers = headersOf(HttpHeaders.ContentType, "application/json")
                )
            }
            else -> respondError(HttpStatusCode.NotFound)
        }
    }

    @Before
    fun setup() {
        apiClientService = ApiClientService(
            HttpClient(mockEngine) {
                install(JsonFeature) {
                    serializer = KotlinxSerializer(
                        Json {
                            isLenient = false
                            ignoreUnknownKeys = true
                            allowSpecialFloatingPointValues = true
                            useArrayPolymorphism = false
                        }
                    )
                    acceptContentTypes
                }
                install(HttpTimeout) {
                    requestTimeoutMillis = 1000
                }
            }
        )
        emailApiContext = EmailApiContext()
        emailApiClientService = EmailApiClientServiceImpl(
            emailApiContext,
            apiClientService
        )
    }

    @Test
    fun `call with token should result in response wrapped in result for google provider`() {
        val token = validToken
        val providerType = ProviderType.GMAIL

        runBlocking {
            val response = emailApiClientService.getEmail(providerType, token)
            assertTrue { response.isSuccess }
            val email = response.getOrNull()
            assertEquals(testEmail, email)
        }
    }

    @Test
    fun `call with empty token should result in NotFound status code response wrapped in failure for google provider`() {
        val token = invalidToken
        val providerType = ProviderType.GMAIL

        runBlocking {
            val response = emailApiClientService.getEmail(providerType, token)
            assertTrue { response.isFailure }
            assertEquals(
                HttpStatusCode.NotFound,
                (response.exceptionOrNull() as ClientRequestException).response.status
            )
        }
    }

    @Test
    fun `call with timeout should result in response wrapped in failure for google provider`() {
        val token = timeoutToken
        val providerType = ProviderType.GMAIL

        runBlocking {
            val response = emailApiClientService.getEmail(
                providerType,
                token
            )
            assertTrue { response.isFailure }
            assertTrue { response.exceptionOrNull() is HttpRequestTimeoutException }
        }
    }
}
