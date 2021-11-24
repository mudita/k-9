import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.mudita.mail.ui.util.stringKey.StringKey

@Composable
fun resolveStringKey(stringKey: StringKey): String =
    LocalContext.current.resolveStringKey(stringKey).let {
        stringResource(id = it)
    }

@StringRes
fun Context.resolveStringKey(stringKey: StringKey): Int =
    resources.getIdentifier(stringKey.name.lowercase(), "string", packageName)
