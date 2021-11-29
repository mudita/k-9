package com.mudita.mail.service.auth.session

interface AuthSessionService {

    fun refreshToken(username: String)

    fun invalidateToken(username: String)

    fun getToken(username: String): String?
}
