package de.thomaskuenneth.cmpunitconverter.app

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
        MaterialTheme(
            colorScheme = defaultColorScheme()
        ) {
            val viewModel: AppViewModel = koinViewModel()
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
    if (uiState.aboutVisibility == AboutVisibility.Sheet) {
        AboutBottomSheet { appViewModel.setShouldShowAbout(false) }
    }
}
