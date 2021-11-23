package com.mudita.mail

import android.app.Application
import com.fsck.k9.DI
import com.fsck.k9.ui.base.navigation.ToSetupAccountNavigator
import io.mockk.mockk
import org.koin.dsl.module

class TestApp : Application() {

    override fun onCreate() {
        super.onCreate()

        DI.start(this, composeUiModules + testModule)
    }
}

val testModule = module {

    single<ToSetupAccountNavigator> { mockk(relaxed = true) }
}