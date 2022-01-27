package com.mudita.mail.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import com.fsck.k9.ui.base.K9Activity
import com.mudita.mail.ui.navigation.AddAccountDestination
import com.mudita.mail.ui.navigation.Destination
import com.mudita.mail.ui.navigation.SignInDestination
import com.mudita.mail.ui.usecase.MuditaMailApp

class SignInActivity : K9Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val startDestination = getStartDestination(intent)
        setContent {
            MuditaMailApp(startDestination = startDestination)
        }
    }

    private fun getStartDestination(intent: Intent): Destination =
        if (intent.getBooleanExtra(IS_SIGN_IN_KEY, false)) {
            SignInDestination
        } else {
            AddAccountDestination
        }

    companion object {

        private const val IS_SIGN_IN_KEY = "IS_SIGN_IN"

        @JvmStatic
        fun launchSignIn(context: Context) {
            val intent = Intent(context, SignInActivity::class.java).apply {
                putExtra(IS_SIGN_IN_KEY, true)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            context.startActivity(intent)
        }

        @JvmStatic
        fun launchAdd(context: Context) {
            val intent = Intent(context, SignInActivity::class.java).apply {
                putExtra(IS_SIGN_IN_KEY, false)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            context.startActivity(intent)
        }
    }
}
