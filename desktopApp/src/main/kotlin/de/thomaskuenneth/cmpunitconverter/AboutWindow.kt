package de.thomaskuenneth.cmpunitconverter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.DialogWindow
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.rememberDialogState
import de.thomaskuenneth.cmpunitconverter.app.About
import de.thomaskuenneth.cmpunitconverter.shared.generated.resources.Res
import de.thomaskuenneth.cmpunitconverter.shared.generated.resources.about_short
import de.thomaskuenneth.cmpunitconverter.shared.generated.resources.app_icon
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun FrameWindowScope.AboutWindow(visible: Boolean, onCloseRequest: () -> Unit) {
    if (visible) DialogWindow(
        state = rememberDialogState(
            position = getCenteredPosition()
        ),
        onCloseRequest = onCloseRequest,
        icon = painterResource(Res.drawable.app_icon),
        resizable = false,
        title = stringResource(Res.string.about_short)
    ) {
        About(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surface))
    }
}
