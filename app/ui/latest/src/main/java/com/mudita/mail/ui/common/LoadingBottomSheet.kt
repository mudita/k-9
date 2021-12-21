package com.mudita.mail.ui.common

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mudita.mail.ui.theme.MuditaTheme
import com.mudita.mail.ui.usecase.signIn.view.TopHideIndicatorBottomSheet

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
