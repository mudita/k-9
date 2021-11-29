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
    val email = remember {
        mutableStateOf("")
    }
    val wasLaunched = remember {
        mutableStateOf(false)
    }
    val authLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == AppCompatActivity.RESULT_OK) {
            wasLaunched.value = true
            viewModel.handleAuthResult(email.value, it.data!!)
        }
    }
    state.value.authIntent?.let {
        authLauncher.launch(it)
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

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
                value = email.value,
                onValueChange = {
                    email.value = it
                }
            )
            Button(
                onClick = {
                    viewModel.submitEmail(email.value)
                }
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
        }
    }
}
