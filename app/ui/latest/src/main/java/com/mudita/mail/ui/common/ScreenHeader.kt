package com.mudita.mail.ui.common

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mudita.mail.R
import com.mudita.mail.ui.theme.PrimaryTextColor

@Composable
fun ScreenHeader(
    @StringRes
    titleRes: Int,
    @StringRes
    subtitleRes: Int,
    @DrawableRes
    logoRes: Int = R.drawable.ic_mudita_logo
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.padding(bottom = 16.dp),
            painter = painterResource(
                id = logoRes
            ),
            contentDescription = "Mudita Logo"
        )
        Text(
            text = stringResource(id = titleRes),
            style = MaterialTheme.typography.h3,
            color = PrimaryTextColor
        )
        Text(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp),
            text = stringResource(id = subtitleRes),
            style = MaterialTheme.typography.subtitle1,
            color = PrimaryTextColor,
            textAlign = TextAlign.Center
        )
    }
}
