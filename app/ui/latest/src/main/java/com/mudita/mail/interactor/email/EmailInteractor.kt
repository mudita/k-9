package com.mudita.mail.interactor.email

import android.content.Intent
import com.mudita.mail.repository.providers.model.ProviderType
import com.mudita.mail.repository.auth.config.AuthConfig

interface EmailInteractor {

    fun getProviderAuthConfig(providerType: ProviderType): AuthConfig?

    suspend fun processResponseAuthIntent(
        intent: Intent,
        username: String
    )

    fun getAuthRequestIntent(
        username: String,
        authConfig: AuthConfig
    ): Intent
}