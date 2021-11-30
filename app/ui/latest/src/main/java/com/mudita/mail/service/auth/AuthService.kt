package com.mudita.mail.service.auth

import com.mudita.mail.repository.auth.config.AuthConfig

interface AuthService {

    fun getAuthIntentData(
        authConfig: AuthConfig
    ): AuthRequestData

    suspend fun processAuthResponseData(
        authResponseData: AuthResponseData
    ): String?
}
