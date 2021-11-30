package com.mudita.mail.factory.httpClient

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import kotlinx.serialization.json.Json

class KtorHttpClientHolderFactory : HttpClientHolderFactory {

    private val client: HttpClient by lazy {
        createHttpClient()
    }

    override fun create() = HttpClientHolder(client)

    private fun createHttpClient(): HttpClient =
        HttpClient(OkHttp) {
            install(JsonFeature) {
                serializer = KotlinxSerializer(
                    Json {
                        isLenient = false
                        ignoreUnknownKeys = true
                        allowSpecialFloatingPointValues = true
                        useArrayPolymorphism = false
                    }
                )
            }
        }
}