package com.mudita.mail.interactor.email

import com.mudita.mail.repository.auth.config.AuthConfig
import com.mudita.mail.repository.providers.model.ProviderType
import com.mudita.mail.service.auth.AuthRequestData
import com.mudita.mail.service.auth.AuthResponseData
import com.mudita.mail.service.auth.AuthService
import com.mudita.mail.service.auth.config.AuthConfigService

class EmailInteractorImpl(
    private val authConfigService: AuthConfigService,
    private val authService: AuthService
) : EmailInteractor {

    override fun getProviderAuthConfig(providerType: ProviderType) =
        authConfigService.getAuthConfigForProviderType(providerType)

    override suspend fun processAuthResponseData(authResponseData: AuthResponseData) =
        authService.processAuthResponseData(authResponseData)

    override fun getAuthRequestData(authConfig: AuthConfig): AuthRequestData =
        authService.getAuthIntentData(authConfig)
}
