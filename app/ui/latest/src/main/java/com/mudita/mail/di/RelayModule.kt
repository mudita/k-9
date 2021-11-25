package com.mudita.mail.di

import com.mudita.mail.relay.auth.AuthRelay
import com.mudita.mail.relay.auth.AuthRelayImpl
import org.koin.dsl.module

val relayModule = module {
    single<AuthRelay> { AuthRelayImpl() }
}