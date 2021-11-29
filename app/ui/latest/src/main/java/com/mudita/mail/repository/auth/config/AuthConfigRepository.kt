package com.mudita.mail.repository.auth.config

interface AuthConfigRepository {

    fun getAuthConfig(predicate: (AuthConfig) -> Boolean): AuthConfig?
}
