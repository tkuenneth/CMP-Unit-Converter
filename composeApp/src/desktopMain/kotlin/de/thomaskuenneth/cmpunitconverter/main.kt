package de.thomaskuenneth.cmpunitconverter

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyShortcut
import androidx.compose.ui.window.MenuBar
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import de.thomaskuenneth.cmpunitconverter.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource

val IS_MACOS = platformName.lowercase().contains("mac os x")

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = stringResource(Res.string.app_name),
    ) {
        var showAboutDialog by remember { mutableStateOf(false) }
        if (!IS_MACOS) {
            MenuBar {
                Menu(text = stringResource(Res.string.file)) {
                    Item(
                        text = stringResource(Res.string.quit),
                        onClick = ::exitApplication,
                        shortcut = KeyShortcut(Key.F4, alt = true)
                    )
                }
                Menu(text = stringResource(Res.string.help)) {
                    Item(
                        text = stringResource(Res.string.about), onClick = {
                            showAboutDialog = true
                        })
                }
            }
        }
        App()
        if (showAboutDialog) AboutDialog { showAboutDialog = false }
    }
}
