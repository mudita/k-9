package com.mudita.mail.ui.usecase.signIn.view

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mudita.mail.R
import com.mudita.mail.repository.providers.model.ProviderTile
import com.mudita.mail.repository.providers.model.ProviderType
import com.mudita.mail.ui.theme.MuditaTheme
import com.mudita.mail.ui.theme.PrimaryTextColor
import com.mudita.mail.ui.usecase.signIn.viewModel.SignInViewModel
import com.mudita.mail.ui.util.resolverProviderImageRes
import com.mudita.mail.ui.viewModel.isError
import resolveStringKey

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SignInScreen(
    viewModel: SignInViewModel
) {

    val uiState = viewModel.uiState.collectAsState()

    val authLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            result.data?.let { viewModel.handleAuthResult(it) }
        } else {
            viewModel.handleAuthProcessCancellation()
        }
    }

    LaunchedEffect(key1 = uiState.value.authIntent) {
        if (uiState.value.authIntent != null) {
            authLauncher.launch(uiState.value.authIntent)
        }
    }

    val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    LaunchedEffect(key1 = uiState.value.isLoading, key2 = uiState.value.error) {
        bottomSheetState.animateTo(
            if (uiState.value.isLoading || uiState.value.error.isError()) {
                ModalBottomSheetValue.Expanded
            } else {
                ModalBottomSheetValue.Hidden
            }
        )
    }

    SignInScreen(
        providers = uiState.value.providers,
        bottomSheetState = bottomSheetState,
        onProviderTapAction = { viewModel.selectProvider(it) },
        bottomSheetHideAction = { viewModel.onInfoHidden() }
    ) {
        if (uiState.value.error.isError()) {
            ErrorBottomSheet(text = uiState.value.error?.message.orEmpty())
        } else {
            LoadingBottomSheet()
        }
    }
}

@Composable
@OptIn(ExperimentalMaterialApi::class)
private fun SignInScreen(
    providers: List<ProviderTile>,
    bottomSheetState: ModalBottomSheetState,
    bottomSheetHideAction: () -> Unit,
    onProviderTapAction: (ProviderType) -> Unit,
    sheetContent: @Composable () -> Unit
) {
    ModalLayout(
        content = {
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
        },
        sheetContent = { sheetContent() },
        bottomSheetState = bottomSheetState,
        onDisposeAction = { bottomSheetHideAction() }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ModalLayout(
    content: @Composable () -> Unit,
    sheetContent: @Composable () -> Unit,
    bottomSheetState: ModalBottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden),
    onDisposeAction: () -> Unit = {}
) {
    if (bottomSheetState.currentValue != ModalBottomSheetValue.Hidden) {
        DisposableEffect(key1 = Unit) {
            onDispose { onDisposeAction() }
        }
    }
    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetBackgroundColor = Transparent,
        sheetContent = { sheetContent() }
    ) {
        content()
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

@Composable
fun LoadingBottomSheet() {
    TopHideIndicatorBottomSheet {
        CircularProgressIndicator(
            color = MaterialTheme.colors.secondary,
            strokeWidth = 4.dp,
            modifier = Modifier
                .height(80.dp)
                .width(80.dp)
        )
    }
}

@Composable
@Preview
fun LoadingBottomSheetPreview() {
    MuditaTheme {
        LoadingBottomSheet()
    }
}

@Composable
fun TopHideIndicatorBottomSheet(content: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp))
            .fillMaxWidth()
            .verticalScroll(ScrollState(0))
            .background(MaterialTheme.colors.background),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Divider(
            modifier = Modifier
                .padding(top = 8.dp, bottom = 16.dp)
                .width(60.dp)
                .height(4.dp)
                .clip(RoundedCornerShape(4.dp))
        )
        Spacer(modifier = Modifier.height(16.dp))
        content()
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun ErrorBottomSheet(
    text: String
) {
    TopHideIndicatorBottomSheet {
        Text(
            text = "Error occurred",
            style = MaterialTheme.typography.h5,
            color = PrimaryTextColor
        )
        Text(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp),
            text = text,
            style = MaterialTheme.typography.subtitle1,
            color = PrimaryTextColor,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
@Preview
fun ErrorBottomSheetPreview() {
    MuditaTheme {
        ErrorBottomSheet("Wystąpił problem z pobraniem adresu email, spróbuj ponownie")
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun SingInScreenPreview() {
    MuditaTheme {
        Scaffold {
            SignInScreen(
                emptyList(),
                rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden),
                {}, {}
            ) {}
        }
    }
}
