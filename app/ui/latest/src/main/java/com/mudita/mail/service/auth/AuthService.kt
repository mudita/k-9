package com.mudita.mail.service.auth

import com.mudita.mail.repository.auth.config.AuthConfig
import com.mudita.mail.repository.providers.model.ProviderType

interface AuthService {

    fun getAuthRequestData(
        authConfig: AuthConfig
    ): Result<AuthRequestData>

    suspend fun processAuthResponseData(
        providerType: ProviderType,
        authResponseData: AuthResponseData
    ): Result<String>
}
