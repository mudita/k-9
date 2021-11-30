package com.mudita.mail.factory.httpClient

import io.ktor.client.HttpClient

data class HttpClientHolder(
    val client: HttpClient
)
