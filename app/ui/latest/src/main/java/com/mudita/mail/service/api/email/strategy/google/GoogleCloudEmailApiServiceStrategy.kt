package com.mudita.mail.service.api.email.strategy.google

import com.mudita.mail.service.api.client.ApiClientService
import com.mudita.mail.service.api.email.strategy.EmailApiServiceStrategy

class GoogleCloudEmailApiServiceStrategy(
    private val apiClientService: ApiClientService
) : EmailApiServiceStrategy {

    override suspend fun getEmail(accessToken: String) =
        apiClientService.get<GoogleCloudUserInfo>(
            "https://www.googleapis.com/oauth2/v2/userinfo",
            parameters = mapOf(ACCESS_TOKEN to accessToken)
        ).map { it.email }

    companion object {

        private const val ACCESS_TOKEN = "access_token"
    }
}
