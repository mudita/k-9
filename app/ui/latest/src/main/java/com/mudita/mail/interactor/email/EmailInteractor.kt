package com.mudita.mail.interactor.email

import com.mudita.mail.repository.auth.config.AuthConfig
import com.mudita.mail.repository.providers.model.ProviderType
import com.mudita.mail.service.auth.AuthRequestData
import com.mudita.mail.service.auth.AuthResponseData

interface EmailInteractor {

    fun getProviderAuthConfig(providerType: ProviderType): Result<AuthConfig>

    suspend fun processAuthResponseData(
        authResponseData: AuthResponseData
    ): String?

    fun getAuthRequestData(
        authConfig: AuthConfig
    ): AuthRequestData
}
