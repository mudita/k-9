package com.mudita.mail.ui.usecase.email.view

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mudita.mail.ui.theme.BlackPure
import com.mudita.mail.ui.theme.MuditaTheme
import com.mudita.mail.ui.usecase.email.viewModel.EmailViewModel
import com.mudita.mail.ui.viewModel.isError

@Composable
fun EmailScreen(
    viewModel: EmailViewModel
) {
    val state = viewModel.uiState.collectAsState()
    val wasLaunched = remember { mutableStateOf(false) }

    val authLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            wasLaunched.value = true
            result.data?.let { viewModel.handleAuthResult(it) }
        }
    }

    LaunchedEffect(key1 = wasLaunched.value, key2 = state.value) {
        if (wasLaunched.value.not() && state.value.authIntent != null) {
            authLauncher.launch(state.value.authIntent)
        }
    }

    LaunchedEffect(key1 = wasLaunched.value) {
        if (wasLaunched.value.not()) {
            viewModel.startAuthProcess()
        }
    }

    state.value.run {
        EmailScreen(
            isLoading = isLoading,
            isError = error.isError(),
            errorText = error?.message.orEmpty()
        )
    }
}

@Composable
fun EmailScreen(
    isLoading: Boolean,
    isError: Boolean,
    errorText: String
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            if (isLoading) {
                CircularProgressIndicator(
                    color = BlackPure
                )
            }
            if (isError) {
                Text(
                    modifier = Modifier.padding(top = 4.dp, start = 32.dp, end = 32.dp),
                    text = errorText,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview
@Composable
fun EmailScreenPreview() {
    MuditaTheme {
        Scaffold {
            EmailScreen(
                true,
                true,
                "Authorization process currently not supported for selected authorization way"
            )
        }
    }
}
