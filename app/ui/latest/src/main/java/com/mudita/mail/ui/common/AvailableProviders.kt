package com.mudita.mail.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mudita.mail.R
import com.mudita.mail.repository.providers.model.ProviderTile
import com.mudita.mail.repository.providers.model.ProviderType
import com.mudita.mail.ui.theme.PrimaryTextColor
import com.mudita.mail.ui.util.resolverProviderImageRes
import resolveStringKey

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
