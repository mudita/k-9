package com.mudita.mail.di

import com.mudita.mail.factory.httpClient.HttpClientHolderFactory
import com.mudita.mail.factory.httpClient.KtorHttpClientHolderFactory
import com.mudita.mail.service.api.email.strategy.EmailApiContext
import org.koin.dsl.module

val utilModule = module {

    factory<HttpClientHolderFactory> { KtorHttpClientHolderFactory() }

    single { get<HttpClientHolderFactory>().create().client }

    factory { EmailApiContext() }
}
