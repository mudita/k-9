package com.mudita.mail.ui.usecase.email.view

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

    EmailScreen()
}

@Composable
fun EmailScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            color = BlackPure
        )
    }
}

@Preview
@Composable
fun EmailScreenPreview() {
    MuditaTheme {
        Scaffold {
            EmailScreen()
        }
    }
}
