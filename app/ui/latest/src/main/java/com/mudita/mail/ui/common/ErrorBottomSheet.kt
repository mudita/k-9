package com.mudita.mail.ui.common

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mudita.mail.R
import com.mudita.mail.ui.theme.MuditaTheme
import com.mudita.mail.ui.theme.PrimaryTextColor

@Composable
fun ErrorBottomSheet(
    text: String
) {
    TopHideIndicatorBottomSheet {
        Text(
            text = stringResource(id = R.string.error_bottom_sheet_header),
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
