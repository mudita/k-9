package com.fsck.k9.autodiscovery.providersxml

import android.content.res.XmlResourceParser
import android.net.Uri
import com.fsck.k9.autodiscovery.api.ConnectionSettingsDiscovery
import com.fsck.k9.autodiscovery.api.DiscoveredServerSettings
import com.fsck.k9.autodiscovery.api.DiscoveryParams
import com.fsck.k9.autodiscovery.api.DiscoveryResults
import com.fsck.k9.helper.EmailHelper
import com.fsck.k9.mail.AuthType
import com.fsck.k9.mail.ConnectionSecurity
import com.fsck.k9.preferences.Protocols
import java.net.URI
import org.xmlpull.v1.XmlPullParser
import timber.log.Timber

class ProvidersXmlDiscovery(
    private val xmlProvider: ProvidersXmlProvider
) : ConnectionSettingsDiscovery {

    override fun discover(discoveryParams: DiscoveryParams): DiscoveryResults? {
        val email = discoveryParams.email
        val authType = discoveryParams.authType

        val domain = EmailHelper.getDomainFromEmailAddress(email) ?: return null

        val providerName: String? = discoveryParams.provider

        val provider = if (providerName != null) {
            findProvider(providerName)
        } else {
            findProviderForDomain(domain)
        } ?: return null

        val incomingSettings = provider.toIncomingServerSettings(email, authType) ?: return null
        val outgoingSettings = provider.toOutgoingServerSettings(email, authType) ?: return null
        return DiscoveryResults(listOf(incomingSettings), listOf(outgoingSettings))
    }

    private fun findProviderForDomain(domain: String): Provider? {
        return try {
            xmlProvider.getXml().use { xml ->
                parseProvidersForDomain(xml, domain)
            }
        } catch (e: Exception) {
            Timber.e(e, "Error while trying to load provider settings.")
            null
        }
    }

    private fun parseProvidersForDomain(xml: XmlResourceParser, domain: String): Provider? {
        do {
            val xmlEventType = xml.next()
            if (xmlEventType == XmlPullParser.START_TAG && xml.name == "provider") {
                val providerDomain = xml.getAttributeValue(null, "domain")
                if (domain.equals(providerDomain, ignoreCase = true)) {
                    val provider = parseProvider(xml)
                    if (provider != null) return provider
                }
            }
        } while (xmlEventType != XmlPullParser.END_DOCUMENT)

        return null
    }

    private fun parseProvider(xml: XmlResourceParser): Provider? {
        var incomingUriTemplate: String? = null
        var incomingUsernameTemplate: String? = null
        var outgoingUriTemplate: String? = null
        var outgoingUsernameTemplate: String? = null

        do {
            val xmlEventType = xml.next()
            if (xmlEventType == XmlPullParser.START_TAG) {
                when (xml.name) {
                    "incoming" -> {
                        incomingUriTemplate = xml.getAttributeValue(null, "uri")
                        incomingUsernameTemplate = xml.getAttributeValue(null, "username")
                    }
                    "outgoing" -> {
                        outgoingUriTemplate = xml.getAttributeValue(null, "uri")
                        outgoingUsernameTemplate = xml.getAttributeValue(null, "username")
                    }
                }
            }
        } while (!(xmlEventType == XmlPullParser.END_TAG && xml.name == "provider"))

        return if (incomingUriTemplate != null && incomingUsernameTemplate != null && outgoingUriTemplate != null &&
            outgoingUsernameTemplate != null
        ) {
            Provider(incomingUriTemplate, incomingUsernameTemplate, outgoingUriTemplate, outgoingUsernameTemplate)
        } else {
            null
        }
    }

    private fun findProvider(providerName: String): Provider? {
        return try {
            xmlProvider.getXml().use { xml ->
                parseProvidersForProvider(xml, providerName)
            }
        } catch (e: Exception) {
            Timber.e(e, "Error while trying to load provider settings.")
            null
        }
    }

    private fun parseProvidersForProvider(xml: XmlResourceParser, providerName: String): Provider? {
        do {
            val xmlEventType = xml.next()
            if (xmlEventType == XmlPullParser.START_TAG && xml.name == "provider") {
                val providerId = xml.getAttributeValue(null, "id")
                if (providerName.equals(providerId, ignoreCase = true)) {
                    val provider = parseProvider(xml)
                    if (provider != null) return provider
                }
            }
        } while (xmlEventType != XmlPullParser.END_DOCUMENT)

        return null
    }

    private fun Provider.toIncomingServerSettings(email: String, authType: AuthType): DiscoveredServerSettings? {
        val user = EmailHelper.getLocalPartFromEmailAddress(email) ?: return null
        val domain = EmailHelper.getDomainFromEmailAddress(email) ?: return null

        val username = incomingUsernameTemplate.fillInUsernameTemplate(email, user, domain)

        val xOAuth2Label = if (authType == AuthType.XOAUTH2) AuthType.XOAUTH2.name else ""
        val xOAuth2Colon = if (authType == AuthType.XOAUTH2) ":" else ""

        val security = when {
            incomingUriTemplate.startsWith("imap+ssl") -> ConnectionSecurity.SSL_TLS_REQUIRED
            incomingUriTemplate.startsWith("imap+tls") -> ConnectionSecurity.STARTTLS_REQUIRED
            else -> error("Connection security required")
        }

        val incomingUri = with(URI(incomingUriTemplate)) {
            URI(scheme, "$xOAuth2Label$xOAuth2Colon$username", host, port, null, null, null).toString()
        }
        val uri = Uri.parse(incomingUri)
        val host = uri.host ?: error("Host name required")
        val port = if (uri.port == -1) {
            if (security == ConnectionSecurity.STARTTLS_REQUIRED) 143 else 993
        } else {
            uri.port
        }

        return DiscoveredServerSettings(Protocols.IMAP, host, port, security, authType, username)
    }

    private fun Provider.toOutgoingServerSettings(email: String, authType: AuthType): DiscoveredServerSettings? {
        val user = EmailHelper.getLocalPartFromEmailAddress(email) ?: return null
        val domain = EmailHelper.getDomainFromEmailAddress(email) ?: return null

        val xOAuth2Label = if (authType == AuthType.XOAUTH2) AuthType.XOAUTH2.name else ""
        val xOAuth2Colon = if (authType == AuthType.XOAUTH2) ":" else ""

        val username = outgoingUsernameTemplate.fillInUsernameTemplate(email, user, domain)

        val security = when {
            outgoingUriTemplate.startsWith("smtp+ssl") -> ConnectionSecurity.SSL_TLS_REQUIRED
            outgoingUriTemplate.startsWith("smtp+tls") -> ConnectionSecurity.STARTTLS_REQUIRED
            else -> error("Connection security required")
        }

        val outgoingUri = with(URI(outgoingUriTemplate)) {
            URI(scheme, "$username$xOAuth2Colon$xOAuth2Label", host, port, null, null, null).toString()
        }

        val uri = Uri.parse(outgoingUri)
        val host = uri.host ?: error("Host name required")
        val port = if (uri.port == -1) {
            if (security == ConnectionSecurity.STARTTLS_REQUIRED) 587 else 465
        } else {
            uri.port
        }

        return DiscoveredServerSettings(Protocols.SMTP, host, port, security, authType, username)
    }

    private fun String.fillInUsernameTemplate(email: String, user: String, domain: String): String {
        return this.replace("\$email", email).replace("\$user", user).replace("\$domain", domain)
    }

    internal data class Provider(
        val incomingUriTemplate: String,
        val incomingUsernameTemplate: String,
        val outgoingUriTemplate: String,
        val outgoingUsernameTemplate: String
    )
}
