package com.mudita.mail.interactor.signIn

import com.mudita.mail.relay.auth.AuthRelay
import com.mudita.mail.repository.providers.ProvidersRepository
import com.mudita.mail.repository.providers.model.ProviderType
import com.mudita.mail.service.authConfig.AuthConfig
import com.mudita.mail.service.authConfig.AuthConfigService

class SignInInteractorImpl(
    private val providersRepository: ProvidersRepository,
    private val authConfigService: AuthConfigService,
    private val authRelay: AuthRelay
) : SignInInteractor {

    override fun getProviders() = providersRepository.getProviders()

    override fun getProviderAuthConfig(providerType: ProviderType) =
        authConfigService.getAuthConfigForProviderType(providerType)

    override suspend fun startAuthProcess(authConfig: AuthConfig) =
        authRelay.offerInput(authConfig)

    override suspend fun processAuthData() = authRelay.requestResult()
}
