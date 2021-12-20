package com.mudita.mail.ui.common

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.Color

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
        sheetBackgroundColor = Color.Transparent,
        sheetContent = { sheetContent() }
    ) {
        content()
    }
}