package com.fsck.k9.autodiscovery.thunderbird

import com.fsck.k9.autodiscovery.api.ConnectionSettingsDiscovery
import com.fsck.k9.autodiscovery.api.DiscoveryResults
import com.fsck.k9.autodiscovery.api.DiscoveryTarget
import com.fsck.k9.mail.AuthType

class ThunderbirdDiscovery(
    private val fetcher: ThunderbirdAutoconfigFetcher,
    private val parser: ThunderbirdAutoconfigParser
) : ConnectionSettingsDiscovery {

    override fun discover(email: String, target: DiscoveryTarget, predefinedAuthType: AuthType?): DiscoveryResults? {
        val autoconfigInputStream = fetcher.fetchAutoconfigFile(email) ?: return null

        return autoconfigInputStream.use {
            parser.parseSettings(it, email)
        }
    }

    override fun toString(): String = "Thunderbird autoconfig"
}
