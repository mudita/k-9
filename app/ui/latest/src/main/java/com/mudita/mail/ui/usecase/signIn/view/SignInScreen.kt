package com.mudita.mail.ui.usecase.signIn.view

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mudita.mail.R
import com.mudita.mail.repository.providers.model.ProviderTile
import com.mudita.mail.repository.providers.model.ProviderType
import com.mudita.mail.ui.common.AvailableProviders
import com.mudita.mail.ui.common.ErrorBottomSheet
import com.mudita.mail.ui.common.LoadingBottomSheet
import com.mudita.mail.ui.common.ModalLayout
import com.mudita.mail.ui.common.ScreenHeader
import com.mudita.mail.ui.theme.MuditaTheme
import com.mudita.mail.ui.usecase.signIn.viewModel.SignInViewModel
import com.mudita.mail.ui.viewModel.isError

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
                ScreenHeader(
                    titleRes = R.string.app_header__welcome,
                    subtitleRes = R.string.app_header__description
                )
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
