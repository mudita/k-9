package com.mudita.mail.interactor.email

import android.content.Intent
import com.mudita.mail.repository.providers.model.ProviderType
import com.mudita.mail.repository.auth.config.AuthConfig
import com.mudita.mail.service.auth.config.AuthConfigService
import com.mudita.mail.service.auth.intent.AuthIntentService

class EmailInteractorImpl(
    private val authConfigService: AuthConfigService,
    private val authIntentService: AuthIntentService
) : EmailInteractor {

    override fun getProviderAuthConfig(providerType: ProviderType) =
        authConfigService.getAuthConfigForProviderType(providerType)

    override suspend fun processResponseAuthIntent(intent: Intent, username: String) {
        authIntentService.processResponseAuthIntent(username, intent)
    }

    override fun getAuthRequestIntent(username: String, authConfig: AuthConfig) =
        authIntentService.getAuthIntent(username, authConfig)
}
