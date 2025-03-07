package de.thomaskuenneth.cmpunitconverter.app

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import de.thomaskuenneth.cmpunitconverter.AppDestinations
import de.thomaskuenneth.cmpunitconverter.defaultColorScheme
import de.thomaskuenneth.cmpunitconverter.di.appModule
import de.thomaskuenneth.cmpunitconverter.distance.DistanceConverterScreen
import de.thomaskuenneth.cmpunitconverter.temperature.TemperatureConverterScreen
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App(platformContent: @Composable (AppViewModel) -> Unit = {}) {
    KoinApplication(
        application = {
            modules(appModule)
        }) {
        val viewModel: AppViewModel = koinViewModel()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        MaterialTheme(
            colorScheme = defaultColorScheme(uiState.colorSchemeMode)
        ) {
            platformContent(viewModel)
            CMPUnitConverter(viewModel)
        }
    }
}

@Composable
fun CMPUnitConverter(appViewModel: AppViewModel) {
    val uiState by appViewModel.uiState.collectAsStateWithLifecycle()
    NavigationSuiteScaffold(
        navigationSuiteItems = {
            AppDestinations.entries.forEach {
                item(
                    selected = it == uiState.currentDestination,
                    onClick = { appViewModel.setCurrentDestination(it) },
                    icon = {
                        Icon(
                            imageVector = it.icon, contentDescription = stringResource(it.contentDescription)
                        )
                    },
                    label = {
                        Text(text = stringResource(it.labelRes))
                    },
                )
            }
        }) {
        when (uiState.currentDestination) {
            AppDestinations.Temperature -> {
                TemperatureConverterScreen(appViewModel = appViewModel, temperatureViewModel = koinViewModel())
            }

            AppDestinations.Distance -> {
                DistanceConverterScreen(appViewModel = appViewModel, distanceViewModel = koinViewModel())
            }
        }
    }
    AboutBottomSheet(visible = uiState.aboutVisibility == AboutVisibility.Sheet) { appViewModel.setShouldShowAbout(false) }
    SettingsBottomSheet(visible = uiState.settingsVisibility == SettingsVisibility.Sheet) {
        appViewModel.setShouldShowSettings(
            false
        )
    }
}

@Composable
fun ColorSchemeMode.isDark(): Boolean = when (this) {
    ColorSchemeMode.Dark -> true
    ColorSchemeMode.Light -> false
    ColorSchemeMode.System -> isSystemInDarkTheme()
}

@Composable
fun colorScheme(colorSchemeMode: ColorSchemeMode) = when (colorSchemeMode.isDark()) {
    false -> lightColorScheme()

    true -> darkColorScheme()
}
