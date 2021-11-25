package com.mudita.mail.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import com.fsck.k9.DI
import com.mudita.mail.ui.usecase.MuditaMailApp

class SignInActivity : BaseAuthActivity(DI.get()) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MuditaMailApp()
        }
    }
}