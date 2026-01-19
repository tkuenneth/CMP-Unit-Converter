package de.thomaskuenneth.cmpunitconverter

import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation.ThreePaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun <T> NavigationHelper(
    navigator: ThreePaneScaffoldNavigator<T>,
    navigationState: NavigationState,
    coroutineScope: CoroutineScope
) {
    LaunchedEffect(navigator.canNavigateBack()) {
        navigationState.update(
            canNavigateBack = navigator.canNavigateBack(),
            navigateBack = { coroutineScope.launch { navigator.navigateBack() } }
        )
    }
    DisposableEffect(Unit) {
        onDispose {
            navigationState.clear()
        }
    }
    BackHandler(navigator.canNavigateBack()) { coroutineScope.launch { navigator.navigateBack() } }
}
