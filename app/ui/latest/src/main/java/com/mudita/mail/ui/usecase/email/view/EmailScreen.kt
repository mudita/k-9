package com.mudita.mail.ui.usecase.email.view

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.tooling.preview.Preview
import com.mudita.mail.ui.theme.BlackPure
import com.mudita.mail.ui.theme.MuditaTheme
import com.mudita.mail.ui.usecase.email.viewModel.EmailViewModel

@Composable
fun EmailScreen(
    viewModel: EmailViewModel
) {
    val state = viewModel.uiState.collectAsState()
    val wasLaunched = remember { mutableStateOf(false) }
    val email = remember { mutableStateOf("") }

    val authLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            wasLaunched.value = true
            result.data?.let { viewModel.handleAuthResult(email.value, it) }
        }
    }
    state.value.authIntent?.let { authLauncher.launch(it) }

    EmailScreen(
        email = email.value,
        onEmailBtnTapAction = { viewModel.submitEmail(email.value) },
        onEmailChanged = { email.value = it }
    )
}

@Composable
fun EmailScreen(
    email: String,
    onEmailBtnTapAction: () -> Unit,
    onEmailChanged: (String) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                placeholder = { Text(text = "Email") },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = BlackPure,
                    focusedIndicatorColor = Transparent,
                    disabledIndicatorColor = Transparent,
                    unfocusedIndicatorColor = Transparent,
                    backgroundColor = Transparent,
                ),
                textStyle = MaterialTheme.typography.subtitle2,
                value = email,
                onValueChange = onEmailChanged
            )
            Button(
                onClick = onEmailBtnTapAction
            ) {
                Text("Submit email", color = Color.Black)
            }
        }
    }
}

@Preview
@Composable
fun EmailScreenPreview() {
    MuditaTheme {
        Scaffold {
            EmailScreen("", {}, {})
        }
    }
}
