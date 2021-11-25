package com.mudita.mail.repository.providers.authConfig

import android.content.Context
import com.mudita.mail.R
import com.mudita.mail.service.authConfig.AuthConfig
import com.mudita.mail.service.authConfig.ResponseType

interface AuthConfigRepository {

    fun getAuthConfig(predicate: (AuthConfig) -> Boolean): AuthConfig?
}

class PredefinedAuthConfigRepository(context: Context) : AuthConfigRepository {

    private val availableConfigs = listOf(
        AuthConfig(
            clientName = "GMAIL",
            clientId = context.getString(R.string.google_client_id),
            redirectUrl = context.getString(R.string.google_redirect_url),
            scope = "https://mail.google.com/",
            responseType = ResponseType.CODE
        )
    )

    override fun getAuthConfig(predicate: (AuthConfig) -> Boolean) =
        availableConfigs.firstOrNull(predicate)
}