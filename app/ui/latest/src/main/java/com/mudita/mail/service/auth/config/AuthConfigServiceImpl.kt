package com.mudita.mail.service.auth.config

import com.mudita.mail.repository.auth.config.AuthConfigRepository
import com.mudita.mail.repository.providers.model.ProviderType
import com.mudita.mail.util.successOrFailure

class AuthConfigServiceImpl(
    private val authConfigRepository: AuthConfigRepository
) : AuthConfigService {

    override fun getAuthConfigForProviderType(providerType: ProviderType) =
        authConfigRepository.getAuthConfig { config ->
            providerType.name == config.clientName
        }.successOrFailure(IllegalArgumentException("No config found for given provider type"))
}
