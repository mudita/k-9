package com.mudita.mail.ui.usecase.email.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Checkbox
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mudita.mail.R
import com.mudita.mail.ui.theme.BlackPure
import com.mudita.mail.ui.theme.GreyDark
import com.mudita.mail.ui.theme.GreyLight
import com.mudita.mail.ui.theme.GreyMedium
import com.mudita.mail.ui.theme.MuditaTheme
import com.mudita.mail.ui.theme.PrimaryTextColor
import com.mudita.mail.ui.theme.WhitePure
import com.mudita.mail.ui.usecase.email.viewModel.EmailViewModel

@Composable
fun EmailScreen(viewModel: EmailViewModel) {
    val state = viewModel.uiState.collectAsState()
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    EmailScreen(
        email = email.value,
        onEmailChanged = { email.value = it },
        password = password.value,
        onPasswordChanged = { password.value = it },
        onBackTapAction = { viewModel.onBack() },
        onNextTapAction = { viewModel.onNext(email.value, password.value) },
        onGeneratePasswordTapAction = { viewModel.onGenerateAppSpecificPassword() }
    )
}

@Composable
fun EmailScreen(
    email: String,
    onEmailChanged: (String) -> Unit,
    password: String,
    onPasswordChanged: (String) -> Unit,
    onBackTapAction: () -> Unit,
    onNextTapAction: () -> Unit,
    onGeneratePasswordTapAction: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(ScrollState(0)),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            MoveBackToolbar(onBackTapAction)
            EmailHeader()
            ProviderHeader()
            CredentialsInput(
                email,
                onEmailChanged,
                password,
                onPasswordChanged
            )
        }
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            NextButton(onNextTapAction)
            GeneratePasswordInfo(onGeneratePasswordTapAction)
        }
    }
}

@Composable
fun MoveBackToolbar(
    onBackTapAction: () -> Unit
) {
    Column(modifier = Modifier.padding(20.dp)) {
        Image(
            modifier = Modifier.clickable { onBackTapAction() },
            painter = painterResource(id = R.drawable.ic_arrow_back),
            contentDescription = "Back arrow"
        )
    }
}

@Composable
fun EmailHeader() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.padding(16.dp),
            painter = painterResource(
                id = R.drawable.ic_mudita_logo
            ),
            contentDescription = "Mudita Logo"
        )
        Text(
            text = "Sign In",
            style = MaterialTheme.typography.h3,
            color = PrimaryTextColor,
            fontWeight = FontWeight.Normal
        )
    }
}

@Composable
fun ProviderHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .size(56.dp)
                .border(
                    shape = RoundedCornerShape(4.dp),
                    border = BorderStroke(1.dp, GreyLight)
                )
                .padding(12.dp),
            painter = painterResource(
                id = R.drawable.ic_icloud
            ),
            contentDescription = "Mudita Logo"
        )
        Text(
            modifier = Modifier.padding(16.dp),
            text = "Enter your iCloud account \n" +
                "credentials"
        )
    }
}

@Composable
fun CredentialsInput(
    email: String,
    onEmailChanged: (String) -> Unit,
    password: String,
    onPasswordChanged: (String) -> Unit
) {
    Column(
        modifier =
        Modifier
            .padding(start = 16.dp, bottom = 16.dp, end = 16.dp)
            .fillMaxWidth()
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = email,
            onValueChange = { onEmailChanged(it) },
            colors = TextFieldDefaults.textFieldColors(
                textColor = GreyDark,
                cursorColor = GreyDark,
                focusedIndicatorColor = Transparent,
                disabledIndicatorColor = Transparent,
                unfocusedIndicatorColor = GreyMedium,
                backgroundColor = GreyLight,
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            placeholder = { Text("Enter your email") }
        )

        val isPasswordVisible = remember {
            mutableStateOf(false)
        }

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 18.dp),
            value = password,
            onValueChange = { onPasswordChanged(it) },
            visualTransformation = if (isPasswordVisible.value) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            colors = TextFieldDefaults.textFieldColors(
                textColor = GreyDark,
                cursorColor = GreyDark,
                focusedIndicatorColor = Transparent,
                disabledIndicatorColor = Transparent,
                unfocusedIndicatorColor = GreyMedium,
                backgroundColor = GreyLight,
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            placeholder = { Text("App-specific password") }
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val isChecked = remember { mutableStateOf(false) }
            Checkbox(
                checked = isChecked.value,
                onCheckedChange = {
                    isChecked.value = isChecked.value.not()
                    isPasswordVisible.value = isPasswordVisible.value.not()
                },
            )
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = "Show password",
                color = BlackPure
            )
        }
    }
}

@Composable
fun NextButton(
    onTapAction: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .clip(RoundedCornerShape(4.dp))
            .clickable { onTapAction() }
            .background(color = BlackPure)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            text = "NEXT",
            color = WhitePure,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun GeneratePasswordInfo(
    onTapAction: () -> Unit
) {
    Column(modifier = Modifier.padding(bottom = 16.dp)) {
        Text(
            modifier = Modifier
                .padding(16.dp)
                .clickable { onTapAction() }
                .fillMaxWidth(),
            text = "HOW TO GENERATE AN APP-SPECIFIC PASSWORD",
            textAlign = TextAlign.Center,
            color = BlackPure
        )
    }
}

@Preview
@Composable
fun EmailScreenPreview() {
    MuditaTheme {
        Scaffold {
            EmailScreen("", {}, "", {}, {}, {}, {})
        }
    }
}
