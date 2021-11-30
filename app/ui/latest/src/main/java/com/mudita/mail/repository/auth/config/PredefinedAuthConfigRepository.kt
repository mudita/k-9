package com.mudita.mail.repository.auth.config

import android.content.Context
import com.mudita.mail.R

class PredefinedAuthConfigRepository(context: Context) : AuthConfigRepository {

    private val availableConfigs = listOf(
        AuthConfig(
            clientName = "GMAIL",
            clientId = context.getString(R.string.google_client_id),
            redirectUrl = context.getString(R.string.google_redirect_url),
            scopes = listOf("https://mail.google.com/", "https://www.googleapis.com/auth/userinfo.email"),
            responseType = ResponseType.CODE,
            authEndpoint = "https://accounts.google.com/o/oauth2/auth",
            tokenEndpoint = "https://oauth2.googleapis.com/token"
        )
    )

    override fun getAuthConfig(predicate: (AuthConfig) -> Boolean) =
        availableConfigs.firstOrNull(predicate)
}
