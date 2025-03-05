package de.thomaskuenneth.cmpunitconverter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyShortcut
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.MenuBar
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import de.thomaskuenneth.cmpunitconverter.app.App
import de.thomaskuenneth.cmpunitconverter.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource

val IS_MACOS = platformName.lowercase().contains("mac os x")

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = stringResource(Res.string.app_name),
    ) {
        App { viewModel ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            CMPUnitConverterMenuBar(
                exit = ::exitApplication,
                showAboutDialog = { viewModel.setShouldShowAboutDialog(true) },
            )
            if (uiState.shouldShowAboutDialog) AboutDialog { viewModel.setShouldShowAboutDialog(false) }
        }
    }
}

@Composable
fun FrameWindowScope.CMPUnitConverterMenuBar(exit: () -> Unit, showAboutDialog: () -> Unit) {
    val backHandlerState by backHandlerState.collectAsStateWithLifecycle()
    MenuBar {
        if (!IS_MACOS) {
            Menu(text = stringResource(Res.string.file)) {
                Item(
                    text = stringResource(Res.string.quit), onClick = exit, shortcut = KeyShortcut(Key.F4, alt = true)
                )
            }
        }
        Menu(text = stringResource(Res.string.navigate)) {
            Item(
                enabled = backHandlerState.enabled,
                shortcut = KeyShortcut(Key.Escape),
                text = stringResource(Res.string.back),
                onClick = {
                    backHandlerState.onBack()
                })
        }
        if (!IS_MACOS) {
            Menu(text = stringResource(Res.string.help)) {
                Item(
                    text = stringResource(Res.string.about), onClick = {
                        showAboutDialog()
                    })
            }
        }
    }
}
