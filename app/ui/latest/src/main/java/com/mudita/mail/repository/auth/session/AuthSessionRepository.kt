package com.mudita.mail.repository.auth.session

interface AuthSessionRepository {

    fun getAuthSessionData(username: String): AuthSessionData

    fun saveAuthSessionData(authSessionData: AuthSessionData)
}
