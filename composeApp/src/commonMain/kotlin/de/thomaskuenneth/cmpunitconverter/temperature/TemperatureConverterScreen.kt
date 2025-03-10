package de.thomaskuenneth.cmpunitconverter.temperature

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.*
import androidx.compose.material3.adaptive.navigation.ThreePaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun TemperatureConverterScreen(
    navigator: ThreePaneScaffoldNavigator<Nothing>,
    viewModel: TemperatureViewModel,
    scrollBehavior: TopAppBarScrollBehavior
) {
    SupportingPaneScaffold(
        directive = navigator.scaffoldDirective,
        mainPane = {
            TemperatureConverter(
                viewModel = viewModel,
                scrollBehavior = scrollBehavior,
                shouldShowButton = navigator.scaffoldValue[SupportingPaneScaffoldRole.Supporting] == PaneAdaptedValue.Hidden
            ) {
                navigator.navigateTo(SupportingPaneScaffoldRole.Supporting)
            }
        },
        supportingPane = { SupportingPane() },
        value = navigator.scaffoldValue
    )
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun ThreePaneScaffoldScope.SupportingPane() {
    AnimatedPane {
        Box(
            modifier = Modifier.background(MaterialTheme.colorScheme.tertiary)
        )
    }
}
