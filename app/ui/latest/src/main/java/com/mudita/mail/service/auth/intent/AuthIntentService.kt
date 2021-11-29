package com.mudita.mail.service.auth.intent

import android.content.Intent
import com.mudita.mail.repository.auth.config.AuthConfig

interface AuthIntentService {

    fun getAuthIntent(
        username: String,
        authConfig: AuthConfig
    ): Intent

    suspend fun processResponseAuthIntent(
        username: String,
        intent: Intent
    )
}
