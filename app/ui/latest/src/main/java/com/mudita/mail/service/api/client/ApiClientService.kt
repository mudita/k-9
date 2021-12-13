package com.mudita.mail.service.api.client

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

// TODO refactor to be based on an interface
class ApiClientService(
    val client: HttpClient
) {

    suspend inline fun <reified T> get(
        path: String,
        parameters: Map<String, Any>
    ): T = client.get(path) { parameters.forEach { parameter(it.key, it.value) } }
}
