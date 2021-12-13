package com.mudita.mail.di

import com.mudita.mail.service.api.client.ApiClientService
import com.mudita.mail.service.api.email.EmailApiClientService
import com.mudita.mail.service.api.email.EmailApiClientServiceImpl
import com.mudita.mail.service.auth.AuthService
import com.mudita.mail.service.auth.AuthServiceImpl
import com.mudita.mail.service.auth.config.AuthConfigService
import com.mudita.mail.service.auth.config.AuthConfigServiceImpl
import com.mudita.mail.service.auth.session.AuthSessionService
import com.mudita.mail.service.auth.session.AuthSessionServiceImpl
import net.openid.appauth.AuthorizationService
import org.koin.dsl.module

val serviceModule = module {

    factory<AuthConfigService> { AuthConfigServiceImpl(get()) }

    factory { AuthorizationService(get()) }

    factory<AuthSessionService> { AuthSessionServiceImpl(get(), get()) }

    factory { ApiClientService(get()) }

    factory<EmailApiClientService> { EmailApiClientServiceImpl(get()) }

    factory<AuthService> { AuthServiceImpl(get(), get(), get()) }
}
