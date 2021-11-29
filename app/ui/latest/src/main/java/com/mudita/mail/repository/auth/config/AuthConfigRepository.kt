package com.mudita.mail.repository.auth.config

import com.mudita.mail.service.auth.config.AuthConfig

interface AuthConfigRepository {

    fun getAuthConfig(predicate: (AuthConfig) -> Boolean): AuthConfig?
}
