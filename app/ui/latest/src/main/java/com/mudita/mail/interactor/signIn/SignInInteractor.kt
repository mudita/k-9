package com.mudita.mail.interactor.signIn

import com.mudita.mail.repository.providers.model.ProviderTile
import com.mudita.mail.repository.providers.model.ProviderType
import com.mudita.mail.service.authConfig.AuthConfig

interface SignInInteractor {

    fun getProviders(): List<ProviderTile>

    fun getProviderAuthConfig(providerType: ProviderType): AuthConfig?

    suspend fun startAuthProcess(authConfig: AuthConfig)

    suspend fun processAuthData() : String
}
