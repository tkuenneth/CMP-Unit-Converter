package de.thomaskuenneth.cmpunitconverter.app

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialExpressiveTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.expressiveLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import de.thomaskuenneth.cmpunitconverter.AppDestinations
import de.thomaskuenneth.cmpunitconverter.NavigationState
import de.thomaskuenneth.cmpunitconverter.ScaffoldWithBackArrow
import de.thomaskuenneth.cmpunitconverter.composables.ConverterScreen
import de.thomaskuenneth.cmpunitconverter.defaultColorScheme
import de.thomaskuenneth.cmpunitconverter.distance.DistanceViewModel
import de.thomaskuenneth.cmpunitconverter.temperature.TemperatureViewModel
import androidx.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
@Preview
fun App(platformContent: @Composable (AppViewModel) -> Unit = {}) {
    val viewModel: AppViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    MaterialExpressiveTheme(
        colorScheme = defaultColorScheme(uiState.colorSchemeMode)
    ) {
        CMPUnitConverter(viewModel)
        platformContent(viewModel)
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun CMPUnitConverter(appViewModel: AppViewModel) {
    val uiState by appViewModel.uiState.collectAsStateWithLifecycle()
    val navigationState = remember { NavigationState() }
    ScaffoldWithBackArrow(
        shouldShowBack = navigationState.canNavigateBack,
        navigateBack = { navigationState.navigateBack() },
        viewModel = appViewModel
    ) { paddingValues, scrollBehavior ->
        NavigationSuiteScaffold(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.surface)
                .padding(paddingValues),
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
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = uiState.currentDestination.name,
                enterTransition = { fadeIn(animationSpec = tween(300)) },
                exitTransition = { fadeOut(animationSpec = tween(300)) },
                popEnterTransition = { fadeIn(animationSpec = tween(300)) },
                popExitTransition = { fadeOut(animationSpec = tween(300)) }) {
                composable(route = AppDestinations.Temperature.name) {
                    ConverterScreen(
                        navigationState = navigationState,
                        viewModel = koinViewModel<TemperatureViewModel>(),
                        scrollBehavior = scrollBehavior
                    )
                }
                composable(route = AppDestinations.Distance.name) {
                    ConverterScreen(
                        navigationState = navigationState,
                        viewModel = koinViewModel<DistanceViewModel>(),
                        scrollBehavior = scrollBehavior
                    )
                }
            }
            LaunchedEffect(uiState.currentDestination) {
                navController.navigate(uiState.currentDestination.name) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
            AboutBottomSheet(visible = uiState.aboutVisibility == DialogOrSheetVisibility.Sheet) {
                appViewModel.setShouldShowAbout(
                    false
                )
            }
            SettingsBottomSheet(visible = uiState.settingsVisibility == DialogOrSheetVisibility.Sheet) {
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

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun colorScheme(colorSchemeMode: ColorSchemeMode) = when (colorSchemeMode.isDark()) {
    false -> expressiveLightColorScheme()

    true -> darkColorScheme()
}
