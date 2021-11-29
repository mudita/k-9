package com.fsck.k9.mail.oauth

enum class OAuth2Provider(
    val isInDomain: (String) -> Boolean
) {

    // FIXME: TEMPORARY SOLUTION WITH VALID LIST OF DOMAINS
    GMAIL(
        {
            it in listOf("gmail.com", "android.com", "google.com", "googlemail.com", "proexe.pl")
        }
    );

    companion object {
        private fun getTypeFromDomain(domain: String): OAuth2Provider? {
            return values().firstOrNull { it.isInDomain(domain) }
        }

        fun getProvider(email: String): OAuth2Provider? {
            val domain = email.split("@".toRegex()).toTypedArray()[1]
            return getTypeFromDomain(domain)
        }

        fun isXOAuth2(domain: String): Boolean {
            return values().any { it.isInDomain(domain) }
        }
    }
}
