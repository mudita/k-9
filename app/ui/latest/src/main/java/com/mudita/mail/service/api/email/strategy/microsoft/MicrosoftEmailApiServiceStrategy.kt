package com.mudita.mail.service.api.email.strategy.microsoft

import com.mudita.mail.service.api.client.ApiClientService
import com.mudita.mail.service.api.email.strategy.EmailApiServiceStrategy
import io.ktor.http.HttpHeaders

class MicrosoftEmailApiServiceStrategy(
    private val apiClientService: ApiClientService
) : EmailApiServiceStrategy {

    override suspend fun getEmail(accessToken: String) =
        apiClientService.get<MicrosoftUserInfo>(
            "https://outlook.office365.com/api/v2.0/me/",
            headers = mapOf(
                HttpHeaders.Authorization to bearerToken(accessToken)
            )
        ).map { it.email }

    private fun bearerToken(accessToken: String) =
        BEARER_TOKEN_PREFIX + accessToken

    companion object {

        private const val BEARER_TOKEN_PREFIX = "Bearer "
    }
}
