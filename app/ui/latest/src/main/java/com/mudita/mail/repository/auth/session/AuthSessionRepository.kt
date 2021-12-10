package com.mudita.mail.repository.auth.session

interface AuthSessionRepository {

    fun getAuthSessionData(username: String): AuthSessionData?

    fun saveAuthSessionData(username: String, authSessionData: AuthSessionData)

    fun removeAuthSession(username: String)
}
