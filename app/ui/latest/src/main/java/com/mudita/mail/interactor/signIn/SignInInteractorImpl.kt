package com.mudita.mail.interactor.signIn

import com.mudita.mail.repository.auth.config.AuthConfig
import com.mudita.mail.repository.providers.ProvidersRepository
import com.mudita.mail.repository.providers.model.ProviderType
import com.mudita.mail.service.auth.AuthResponseData
import com.mudita.mail.service.auth.AuthService
import com.mudita.mail.service.auth.config.AuthConfigService

class SignInInteractorImpl(
    private val providersRepository: ProvidersRepository,
    private val authConfigService: AuthConfigService,
    private val authService: AuthService
) : SignInInteractor {

    override fun getProviders() = providersRepository.getProviders()

    override fun getProviderAuthConfig(providerType: ProviderType) =
        authConfigService.getAuthConfigForProviderType(providerType)

    override suspend fun processAuthResponseData(
        providerType: ProviderType,
        authResponseData: AuthResponseData
    ) = authService.processAuthResponseData(providerType, authResponseData)

    override fun getAuthRequestData(authConfig: AuthConfig) =
        authService.getAuthRequestData(authConfig)
}
