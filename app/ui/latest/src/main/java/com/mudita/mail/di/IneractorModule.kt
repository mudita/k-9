package com.mudita.mail.di

import com.mudita.mail.interactor.email.EmailInteractor
import com.mudita.mail.interactor.email.EmailInteractorImpl
import com.mudita.mail.interactor.signIn.SignInInteractor
import com.mudita.mail.interactor.signIn.SignInInteractorImpl
import org.koin.dsl.module

val interactorModule = module {

    factory<SignInInteractor> { SignInInteractorImpl(get()) }

    factory<EmailInteractor> { EmailInteractorImpl(get(), get()) }
}
