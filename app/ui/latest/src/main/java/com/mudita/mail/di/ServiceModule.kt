package com.mudita.mail.di

import com.mudita.mail.service.auth.config.AuthConfigService
import com.mudita.mail.service.auth.config.AuthConfigServiceImpl
import com.mudita.mail.service.auth.intent.AuthIntentService
import com.mudita.mail.service.auth.intent.AuthIntentServiceImpl
import com.mudita.mail.service.auth.session.AuthSessionService
import com.mudita.mail.service.auth.session.AuthSessionServiceImpl
import net.openid.appauth.AuthorizationService
import org.koin.dsl.module

val serviceModule = module {

    factory<AuthConfigService> { AuthConfigServiceImpl(get()) }

    factory { AuthorizationService(get()) }

    factory<AuthSessionService> { AuthSessionServiceImpl(get(), get()) }

    factory<AuthIntentService> { AuthIntentServiceImpl(get(), get()) }
}
