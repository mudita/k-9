package com.mudita.mail.service.auth.config

import com.mudita.mail.repository.auth.config.AuthConfig
import com.mudita.mail.repository.providers.model.ProviderType

interface AuthConfigService {

    fun getAuthConfigForProviderType(providerType: ProviderType): Result<AuthConfig>
}
