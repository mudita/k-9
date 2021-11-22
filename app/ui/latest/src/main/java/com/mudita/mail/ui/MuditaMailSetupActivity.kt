package com.mudita.mail.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import com.fsck.k9.ui.base.K9Activity
import com.mudita.mail.ui.usecase.MuditaMailApp

class MuditaMailSetupActivity : K9Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MuditaMailApp()
        }
    }
}
