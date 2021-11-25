package com.mudita.mail.di

import com.mudita.mail.service.authConfig.AuthConfigService
import com.mudita.mail.service.authConfig.AuthConfigServiceImpl
import org.koin.dsl.module

val serviceModule = module {

    factory<AuthConfigService> { AuthConfigServiceImpl(get()) }
}