package de.thomaskuenneth.cmpunitconverter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyShortcut
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.MenuBar
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import de.thomaskuenneth.cmpunitconverter.app.App
import de.thomaskuenneth.cmpunitconverter.app.DialogOrSheetVisibility
import de.thomaskuenneth.cmpunitconverter.composeapp.generated.resources.Res
import de.thomaskuenneth.cmpunitconverter.composeapp.generated.resources.about
import de.thomaskuenneth.cmpunitconverter.composeapp.generated.resources.app_icon
import de.thomaskuenneth.cmpunitconverter.composeapp.generated.resources.app_name
import de.thomaskuenneth.cmpunitconverter.composeapp.generated.resources.back
import de.thomaskuenneth.cmpunitconverter.composeapp.generated.resources.distance
import de.thomaskuenneth.cmpunitconverter.composeapp.generated.resources.file
import de.thomaskuenneth.cmpunitconverter.composeapp.generated.resources.help
import de.thomaskuenneth.cmpunitconverter.composeapp.generated.resources.navigate
import de.thomaskuenneth.cmpunitconverter.composeapp.generated.resources.quit
import de.thomaskuenneth.cmpunitconverter.composeapp.generated.resources.settings
import de.thomaskuenneth.cmpunitconverter.composeapp.generated.resources.temperature
import de.thomaskuenneth.cmpunitconverter.di.initKoin
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import java.awt.Desktop

fun main() = application {
    initKoin {}
    Window(
        onCloseRequest = ::exitApplication,
        state = rememberWindowState(width = 840.dp, height = 600.dp),
        title = stringResource(Res.string.app_name),
        icon = painterResource(Res.drawable.app_icon),
    ) {
        App { viewModel ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            with(Desktop.getDesktop()) {
                LaunchedEffect(Unit) {
                    installPreferencesHandler { viewModel.setShouldShowSettings(true) }
                }
                LaunchedEffect(uiState.showExtendedAboutDialog) {
                    if (uiState.showExtendedAboutDialog) {
                        installAboutHandler { viewModel.setShouldShowAbout(true) }
                    } else {
                        installAboutHandler(null)
                    }
                }
            }
            CMPUnitConverterMenuBar(
                currentDestination = uiState.currentDestination,
                exit = ::exitApplication,
                showAbout = { viewModel.setShouldShowAbout(true) },
                showSettings = { viewModel.setShouldShowSettings(true) },
                navigateToTemperature = { viewModel.setCurrentDestination(AppDestinations.Temperature) },
                navigateToDistance = { viewModel.setCurrentDestination(AppDestinations.Distance) },
            )
            AboutWindow(visible = uiState.aboutVisibility == DialogOrSheetVisibility.Window) {
                viewModel.setShouldShowAbout(
                    false
                )
            }
            SettingsWindow(visible = uiState.settingsVisibility == DialogOrSheetVisibility.Window) {
                viewModel.setShouldShowSettings(
                    false
                )
            }
        }
    }
}

@Composable
fun FrameWindowScope.CMPUnitConverterMenuBar(
    currentDestination: AppDestinations,
    exit: () -> Unit,
    showAbout: () -> Unit,
    showSettings: () -> Unit,
    navigateToTemperature: () -> Unit,
    navigateToDistance: () -> Unit
) {
    val backHandlerState by backHandlerState.collectAsStateWithLifecycle()
    MenuBar {
        if (operatingSystem != OperatingSystem.MacOS) {
            Menu(text = stringResource(Res.string.file)) {
                Item(
                    text = stringResource(Res.string.settings), onClick = showSettings
                )
                Item(
                    text = stringResource(Res.string.quit), onClick = exit, shortcut = KeyShortcut(Key.F4, alt = true)
                )
            }
        }
        Menu(text = stringResource(Res.string.navigate)) {
            CheckboxItem(
                checked = currentDestination == AppDestinations.Temperature,
                shortcut = KeyShortcut(Key.One, alt = true),
                text = stringResource(Res.string.temperature),
                onCheckedChange = {
                    navigateToTemperature()
                })
            CheckboxItem(
                checked = currentDestination == AppDestinations.Distance,
                shortcut = KeyShortcut(Key.Two, alt = true),
                text = stringResource(Res.string.distance),
                onCheckedChange = {
                    navigateToDistance()
                })
            Separator()
            Item(
                enabled = backHandlerState.enabled,
                shortcut = KeyShortcut(Key.Escape),
                text = stringResource(Res.string.back),
                onClick = {
                    backHandlerState.onBack()
                })
        }
        if (operatingSystem != OperatingSystem.MacOS) {
            Menu(text = stringResource(Res.string.help)) {
                Item(
                    text = stringResource(Res.string.about), onClick = {
                        showAbout()
                    })
            }
        }
    }
}

@Composable
fun FrameWindowScope.getCenteredPosition(): WindowPosition = window.locationOnScreen.let { locationOnScreen ->
    with(LocalDensity.current) {
        window.size.let { size ->
            val (width, height) = Pair(size.width.toDp(), size.height.toDp())
            val (offsetX, offsetY) = Pair(
                (width - 400.dp) / 2, (height - 300.dp) / 2
            )
            WindowPosition.Absolute(
                x = (locationOnScreen.x.toDp() + offsetX), y = (locationOnScreen.y.toDp() + offsetY)
            )
        }
    }
}
