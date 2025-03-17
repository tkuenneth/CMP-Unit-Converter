package de.thomaskuenneth.cmpunitconverter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyShortcut
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import de.thomaskuenneth.cmpunitconverter.app.AboutVisibility
import de.thomaskuenneth.cmpunitconverter.app.App
import de.thomaskuenneth.cmpunitconverter.app.SettingsVisibility
import de.thomaskuenneth.cmpunitconverter.composeapp.generated.resources.*
import de.thomaskuenneth.cmpunitconverter.di.initKoin
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import java.awt.Desktop

fun main() = application {
    initKoin {}
    Window(
        onCloseRequest = ::exitApplication,
        title = stringResource(Res.string.app_name),
        icon = painterResource(Res.drawable.app_icon),
    ) {
        App { viewModel ->
            LaunchedEffect(Unit) {
                with(Desktop.getDesktop()) {
                    installPreferencesHandler { viewModel.setShouldShowSettings(true) }
                }
            }
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            CMPUnitConverterMenuBar(
                currentDestination = uiState.currentDestination,
                exit = ::exitApplication,
                showAbout = { viewModel.setShouldShowAbout(true) },
                showSettings = { viewModel.setShouldShowSettings(true) },
                navigateToTemperature = { viewModel.setCurrentDestination(AppDestinations.Temperature) },
                navigateToDistance = { viewModel.setCurrentDestination(AppDestinations.Distance) },
            )
            AboutWindow(visible = uiState.aboutVisibility == AboutVisibility.Window) {
                viewModel.setShouldShowAbout(
                    false
                )
            }
            SettingsWindow(visible = uiState.settingsVisibility == SettingsVisibility.Window) {
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
