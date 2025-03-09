package de.thomaskuenneth.cmpunitconverter.app

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation.ThreePaneScaffoldNavigator
import androidx.compose.material3.adaptive.navigation.rememberSupportingPaneScaffoldNavigator
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import de.thomaskuenneth.cmpunitconverter.AppDestinations
import de.thomaskuenneth.cmpunitconverter.ScaffoldWithBackArrow
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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun CMPUnitConverter(appViewModel: AppViewModel) {
    val uiState by appViewModel.uiState.collectAsStateWithLifecycle()
    val navigatorStateMap = remember {
        mutableStateMapOf<AppDestinations, ThreePaneScaffoldNavigator<Nothing>>()
    }
    key(navigatorStateMap) {
        navigatorStateMap[AppDestinations.Temperature] = rememberSupportingPaneScaffoldNavigator()
        navigatorStateMap[AppDestinations.Distance] = rememberSupportingPaneScaffoldNavigator()
    }
    ScaffoldWithBackArrow(
        shouldShowBack = navigatorStateMap[uiState.currentDestination]!!.canNavigateBack(),
        navigateBack = navigatorStateMap[uiState.currentDestination]!!::navigateBack,
        viewModel = appViewModel
    ) { paddingValues, scrollBehavior ->
        Box(modifier = Modifier.background(color = MaterialTheme.colorScheme.surface)) {
            NavigationSuiteScaffold(
                modifier = Modifier.padding(paddingValues),
                navigationSuiteItems = {
                    AppDestinations.entries.forEach {
                        item(
                            selected = it == uiState.currentDestination,
                            onClick = { appViewModel.setCurrentDestination(it) },
                            icon = {
                                Icon(
                                    imageVector = it.icon,
                                    contentDescription = stringResource(it.contentDescription)
                                )
                            },
                            label = {
                                Text(text = stringResource(it.labelRes))
                            },
                        )
                    }
                }) {
                val navigator = navigatorStateMap[uiState.currentDestination]!!
                when (uiState.currentDestination) {
                    AppDestinations.Temperature -> {
                        TemperatureConverterScreen(
                            navigator = navigator,
                            viewModel = koinViewModel(),
                            scrollBehavior = scrollBehavior
                        )
                    }

                    AppDestinations.Distance -> {
                        DistanceConverterScreen(
                            navigator = navigator,
                            viewModel = koinViewModel(),
                            scrollBehavior = scrollBehavior
                        )
                    }
                }
            }
            AboutBottomSheet(visible = uiState.aboutVisibility == AboutVisibility.Sheet) {
                appViewModel.setShouldShowAbout(
                    false
                )
            }
            SettingsBottomSheet(visible = uiState.settingsVisibility == SettingsVisibility.Sheet) {
                appViewModel.setShouldShowSettings(
                    false
                )
            }
        }
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
