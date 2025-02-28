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
import androidx.lifecycle.viewmodel.compose.viewModel
import de.thomaskuenneth.cmp.de.thomaskuenneth.cmpunitconverter.distance.DistanceConverter
import de.thomaskuenneth.cmp.de.thomaskuenneth.cmpunitconverter.distance.DistanceRepository
import de.thomaskuenneth.cmp.de.thomaskuenneth.cmpunitconverter.distance.DistanceViewModel
import de.thomaskuenneth.cmp.de.thomaskuenneth.cmpunitconverter.temperature.TemperatureConverter
import de.thomaskuenneth.cmp.de.thomaskuenneth.cmpunitconverter.temperature.TemperatureRepository
import de.thomaskuenneth.cmp.de.thomaskuenneth.cmpunitconverter.temperature.TemperatureViewModel
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val temperatureRepository = TemperatureRepository()
    val distanceRepository = DistanceRepository()
    MaterialTheme {
        MaterialAdaptiveDemo(
            temperatureRepository = temperatureRepository,
            distanceRepository = distanceRepository
        )
    }
}

@Composable
fun MaterialAdaptiveDemo(
    temperatureRepository: TemperatureRepository, distanceRepository: DistanceRepository
) {
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
                TemperatureConverter(viewModel = viewModel { TemperatureViewModel(temperatureRepository) })
            }

            AppDestinations.Distance -> {
                DistanceConverter(viewModel = viewModel { DistanceViewModel(distanceRepository) })
            }
        }
    }
}
