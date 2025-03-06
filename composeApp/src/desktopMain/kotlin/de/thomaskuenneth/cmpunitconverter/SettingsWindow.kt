package de.thomaskuenneth.cmpunitconverter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.DialogWindow
import de.thomaskuenneth.cmpunitconverter.app.Settings
import de.thomaskuenneth.cmpunitconverter.composeapp.generated.resources.Res
import de.thomaskuenneth.cmpunitconverter.composeapp.generated.resources.app_icon
import de.thomaskuenneth.cmpunitconverter.composeapp.generated.resources.settings
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun SettingsWindow(visible: Boolean, onCloseRequest: () -> Unit) {
    DialogWindow(
        visible = visible,
        onCloseRequest = onCloseRequest,
        icon = painterResource(Res.drawable.app_icon),
        resizable = false,
        title = stringResource(Res.string.settings)
    ) {
        Settings(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surface))
    }
}
