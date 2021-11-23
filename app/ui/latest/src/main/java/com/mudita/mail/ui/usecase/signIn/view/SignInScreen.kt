package com.mudita.mail.ui.usecase.signIn.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mudita.mail.R
import com.mudita.mail.repository.providers.model.ProviderTile
import com.mudita.mail.repository.providers.model.ProviderType
import com.mudita.mail.ui.theme.BlackPure
import com.mudita.mail.ui.theme.MuditaTheme
import com.mudita.mail.ui.theme.PrimaryTextColor
import com.mudita.mail.ui.usecase.signIn.viewModel.SignInViewModel
import com.mudita.mail.ui.util.resolverProviderImageRes
import com.mudita.mail.ui.util.stringKey.resolveStringKey

@Composable
fun SignInScreen(
    viewModel: SignInViewModel
) {

    val uiState = viewModel.uiState.collectAsState()

    SignInScreen(
        uiState.value.providers
    ) {
        viewModel.selectProvider(it)
    }
}

@Composable
private fun SignInScreen(
    providers: List<ProviderTile>,
    onProviderTapAction: (ProviderType) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, top = 48.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        SignInHeader()
        AvailableProviders(
            providers, onProviderTapAction
        )
    }
}

@Composable
fun SignInHeader() {
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
            text = stringResource(id = R.string.app_header__welcome),
            style = MaterialTheme.typography.h3,
            color = PrimaryTextColor
        )
        Text(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp),
            text = stringResource(id = R.string.app_header__description),
            style = MaterialTheme.typography.subtitle1,
            color = PrimaryTextColor,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun AvailableProviders(
    providers: List<ProviderTile>,
    onProviderTapAction: (ProviderType) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp, bottom = 16.dp)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            text = stringResource(id = R.string.providers__header),
            color = PrimaryTextColor,
            fontWeight = FontWeight.Bold
        )
        LazyColumn {
            items(providers) { providerTile ->
                providerTile.run {
                    Provider(
                        name = resolveStringKey(stringKey = nameKey),
                        description = resolveStringKey(stringKey = descriptionKey),
                        imageRes = resolverProviderImageRes(providerType = type)
                    ) {
                        onProviderTapAction(type)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun SingInScreenPreview() {
    MuditaTheme {
        Scaffold {
            SignInScreen(
                emptyList()
            ) {}
        }
    }
}
