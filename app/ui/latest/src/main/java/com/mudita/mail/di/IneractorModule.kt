package com.mudita.mail.di

import com.mudita.mail.interactor.delete.DeleteAccountInteractor
import com.mudita.mail.interactor.delete.DeleteAccountInteractorImpl
import com.mudita.mail.interactor.email.EmailInteractor
import com.mudita.mail.interactor.email.EmailInteractorImpl
import com.mudita.mail.interactor.signIn.SignInInteractor
import com.mudita.mail.interactor.signIn.SignInInteractorImpl
import org.koin.dsl.module

val interactorModule = module {

    factory<SignInInteractor> { SignInInteractorImpl(get(), get(), get()) }

    factory<DeleteAccountInteractor> { DeleteAccountInteractorImpl(get()) }

    factory<EmailInteractor> { EmailInteractorImpl() }
}
