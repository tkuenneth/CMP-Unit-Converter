package de.thomaskuenneth.cmpunitconverter.temperature

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.PaneAdaptedValue
import androidx.compose.material3.adaptive.layout.SupportingPaneScaffold
import androidx.compose.material3.adaptive.layout.SupportingPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.ThreePaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import de.thomaskuenneth.cmpunitconverter.SupportingPane

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
        supportingPane = {
            with(viewModel.supportingPaneUseCase) {
                val uiState by stateFlow.collectAsStateWithLifecycle()
                SupportingPane(
                    info = uiState.info,
                    unit = uiState.lastClicked,
                    elements = uiState.elements
                ) { openInBrowser() }
            }
        },
        value = navigator.scaffoldValue
    )
}
