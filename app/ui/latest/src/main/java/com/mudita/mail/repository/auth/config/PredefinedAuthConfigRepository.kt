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
        ),
        AuthConfig(
            clientName = "OUTLOOK",
            clientId = context.getString(R.string.outlook_client_id),
            redirectUrl = context.getString(R.string.outlook_redirect_url),
            scopes = listOf(
                "https://outlook.office.com/IMAP.AccessAsUser.All",
                "https://outlook.office.com/POP.AccessAsUser.All",
                "https://outlook.office.com/SMTP.Send",
                "offline_access"
            ),
            responseType = ResponseType.CODE,
            authEndpoint = "https://login.microsoftonline.com/common/oauth2/v2.0/authorize",
            tokenEndpoint = "https://login.microsoftonline.com/common/oauth2/v2.0/token"
        )
    )

    override fun getAuthConfig(predicate: (AuthConfig) -> Boolean) =
        availableConfigs.firstOrNull(predicate)
}
