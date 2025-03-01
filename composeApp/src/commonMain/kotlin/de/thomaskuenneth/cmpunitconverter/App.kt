package de.thomaskuenneth.cmpunitconverter

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import cmpunitconverter.composeapp.generated.resources.Res
import cmpunitconverter.composeapp.generated.resources.app_name
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

@OptIn(ExperimentalMaterial3Api::class)
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
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(title = { Text(text = stringResource(Res.string.app_name)) })
            }) { innerPadding ->
            Box(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(innerPadding)) {
                Crossfade(targetState = currentDestination) {
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
        }
    }
}
