package com.fsck.k9.ui.setup.ui.usecase.signIn.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.fsck.k9.ui.setup.ui.theme.MuditaTheme
import com.fsck.k9.ui.setup.ui.usecase.signIn.viewModel.SignInViewModel

@Composable
fun SignInScreen(
    viewModel: SignInViewModel
) {
    SignInScreen {
        viewModel.gmailSelected()
    }
}

@Composable
private fun SignInScreen(
    onSignInProviderSelected: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = onSignInProviderSelected) {
            Text(text = "Move out")
        }
    }
}

@Preview
@Composable
fun SingInScreenPreview() {
    MuditaTheme {
        SignInScreen {}
    }
}
