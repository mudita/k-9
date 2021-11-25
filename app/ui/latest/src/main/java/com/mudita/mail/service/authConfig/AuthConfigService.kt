package com.mudita.mail.service.authConfig

import com.mudita.mail.repository.providers.authConfig.AuthConfigRepository
import com.mudita.mail.repository.providers.model.ProviderType

enum class ResponseType(val value: String) {

    CODE("code"),
    TOKEN("token"),
    ID_TOKEN("id_token")
}

data class AuthConfig(
    val clientName: String,
    val clientId: String,
    val redirectUrl: String,
    val scope: String,
    val responseType: ResponseType
)

interface AuthConfigService {

    fun getAuthConfigForProviderType(providerType: ProviderType): AuthConfig?
}

class AuthConfigServiceImpl(
    private val authConfigRepository: AuthConfigRepository
) : AuthConfigService {

    override fun getAuthConfigForProviderType(providerType: ProviderType) =
        authConfigRepository.getAuthConfig { config ->
            providerType.name == config.clientName
        }
}