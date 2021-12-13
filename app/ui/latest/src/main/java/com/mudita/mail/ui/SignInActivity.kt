package com.mudita.mail.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import com.fsck.k9.ui.base.K9Activity
import com.mudita.mail.ui.usecase.MuditaMailApp

class SignInActivity : K9Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MuditaMailApp()
        }
    }

    companion object {

        @JvmStatic fun launch(activity: Activity) {
            val intent = Intent(activity, SignInActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            activity.startActivity(intent)
        }
    }
}
