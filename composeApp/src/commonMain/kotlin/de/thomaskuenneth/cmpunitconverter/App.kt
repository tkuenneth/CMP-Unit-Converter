package de.thomaskuenneth.cmpunitconverter

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import de.thomaskuenneth.cmpunitconverter.distance.DistanceConverter
import de.thomaskuenneth.cmpunitconverter.temperature.TemperatureConverter
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    KoinApplication(
        application = {
            modules(appModule)
        }) {
        MaterialTheme(
            colorScheme = defaultColorScheme()
        ) {
            CMPUnitConverter()
        }
    }
}

@Composable
fun CMPUnitConverter() {
    var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.Temperature) }
    NavigationSuiteScaffold(
        navigationSuiteItems = {
            AppDestinations.entries.forEach {
                item(
                    selected = it == currentDestination,
                    onClick = { currentDestination = it },
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
        when (currentDestination) {
            AppDestinations.Temperature -> {
                TemperatureConverter(viewModel = koinViewModel())
            }

            AppDestinations.Distance -> {
                DistanceConverter(viewModel = koinViewModel())
            }
        }
    }
}
