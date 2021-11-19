package com.fsck.k9.ui.setup.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import com.fsck.k9.ui.base.K9Activity
import com.fsck.k9.ui.setup.ui.usecase.MuditaMailApp

class MuditaMailSetupActivity : K9Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MuditaMailApp()
        }
    }
}
