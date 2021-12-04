package com.mudita.mail.repository.auth.session

import android.content.Context
import net.openid.appauth.AuthState

class SharedPrefsAuthSessionRepository(
    context: Context
) : AuthSessionRepository {

    private val sharedPreferences by lazy { context.getSharedPreferences(AUTH_PREFS, Context.MODE_PRIVATE) }

    override fun getAuthSessionData(username: String): AuthSessionData? {
        val authJson = sharedPreferences.getString(userAuthStateKey(username), null)
        return authJson?.let(AuthState::jsonDeserialize)?.let { AuthSessionData(it) }
    }

    private fun userAuthStateKey(username: String) = "${AUTH_KEY}_$username"

    override fun saveAuthSessionData(username: String, authSessionData: AuthSessionData) {
        sharedPreferences.edit()
            .putString(
                userAuthStateKey(username),
                authSessionData.authState.jsonSerializeString()
            ).apply()
    }

    companion object {

        private const val AUTH_KEY = "auth_key"
        private const val AUTH_PREFS = "auth_prefs"
    }
}
