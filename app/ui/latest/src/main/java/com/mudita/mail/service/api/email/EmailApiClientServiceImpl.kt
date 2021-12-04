package com.mudita.mail.service.api.email

import com.mudita.mail.service.api.client.ApiClientService

class EmailApiClientServiceImpl(
    private val apiClientService: ApiClientService
) : EmailApiClientService {

    override suspend fun getEmail(token: String) =
        apiClientService.get<UserInfo>(
        "https://www.googleapis.com/oauth2/v2/userinfo",
        parameters = mapOf(ACCESS_TOKEN to token)
    ).map { it.email }

    companion object {

        private const val ACCESS_TOKEN = "access_token"
    }
}
