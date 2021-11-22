package com.mudita.mail.ui.usecase.signIn.view

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mudita.mail.R
import com.mudita.mail.ui.theme.BlackPure
import com.mudita.mail.ui.theme.GreyDark
import com.mudita.mail.ui.theme.GreyLight
import com.mudita.mail.ui.theme.MuditaTheme

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Provider(
    modifier: Modifier = Modifier,
    name: String,
    description: String,
    @DrawableRes imageRes: Int,
    onTapAction: () -> Unit
) {
    val showCaption = remember { mutableStateOf(false) }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clickable { onTapAction() }
                .border(
                    shape = RoundedCornerShape(4.dp),
                    border = BorderStroke(1.dp, GreyLight)
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = modifier
                    .padding(16.dp)
                    .width(32.dp)
                    .height(32.dp),
                painter = painterResource(id = imageRes),
                contentDescription = "Provider logo"
            )
            Divider(
                color = GreyLight,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp)
            )
            Text(
                modifier = Modifier
                    .width(IntrinsicSize.Max)
                    .weight(1f)
                    .padding(16.dp),
                text = name,
                color = BlackPure,
                fontSize = MaterialTheme.typography.subtitle1.fontSize
            )
            Image(
                modifier = Modifier
                    .padding(16.dp)
                    .clickable {
                        showCaption.value = !showCaption.value
                    },
                painter = painterResource(
                    id = if (!showCaption.value) {
                        R.drawable.ic_help
                    } else {
                        R.drawable.ic_close
                    }
                ),
                contentDescription = "Help icon"
            )
        }
        AnimatedVisibility(visible = showCaption.value) {
            Text(
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
                text = description,
                color = GreyDark,
                fontSize = MaterialTheme.typography.caption.fontSize
            )
        }
    }
}

@Preview
@Composable
fun ProviderPreview() {
    MuditaTheme {
        Provider(
            name = "Google mail",
            description = "Strong security (OAUTH2). This app won’t store and transmit mail password.",
            imageRes = R.drawable.ic_gmail
        ) {
        }
    }
}
