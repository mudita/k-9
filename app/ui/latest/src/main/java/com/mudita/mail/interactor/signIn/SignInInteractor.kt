package com.mudita.mail.interactor.signIn

import com.mudita.mail.repository.auth.config.AuthConfig
import com.mudita.mail.repository.providers.model.ProviderTile
import com.mudita.mail.repository.providers.model.ProviderType
import com.mudita.mail.service.auth.AuthRequestData
import com.mudita.mail.service.auth.AuthResponseData

interface SignInInteractor {

    fun getProviders(): List<ProviderTile>

    fun getProviderAuthConfig(providerType: ProviderType): Result<AuthConfig>

    suspend fun processAuthResponseData(
        providerType: ProviderType,
        authResponseData: AuthResponseData
    ): Result<String>

    fun getAuthRequestData(
        authConfig: AuthConfig
    ): Result<AuthRequestData>
}
