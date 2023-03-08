package com.mudita.mail.ui.usecase.email.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mudita.mail.R
import com.mudita.mail.ui.common.TopHideIndicatorBottomSheet
import com.mudita.mail.ui.theme.BlackPure
import com.mudita.mail.ui.theme.MuditaTheme
import com.mudita.mail.ui.theme.WhitePure

@Composable
fun PasswordInfoBootmSheet(
    onTapAction: () -> Unit
) {
    TopHideIndicatorBottomSheet {
        Text(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
            textAlign = TextAlign.Center,
            text = stringResource(id = R.string.password_info)
        )
        Column(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
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
                text = stringResource(id = R.string.password_info_generate),
                color = WhitePure,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
fun PasswordInfoPreview() {
    MuditaTheme {
        PasswordInfoBootmSheet {}
    }
}
