package de.thomaskuenneth.cmpunitconverter.distance

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.PaneAdaptedValue
import androidx.compose.material3.adaptive.layout.SupportingPaneScaffold
import androidx.compose.material3.adaptive.layout.SupportingPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.ThreePaneScaffoldNavigator
import androidx.compose.runtime.Composable
import de.thomaskuenneth.cmpunitconverter.temperature.SupportingPane

@OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DistanceConverterScreen(
    navigator: ThreePaneScaffoldNavigator<Nothing>,
    viewModel: DistanceViewModel,
    scrollBehavior: TopAppBarScrollBehavior
) {
    SupportingPaneScaffold(
        directive = navigator.scaffoldDirective,
        mainPane = {
            DistanceConverter(
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
