package de.thomaskuenneth.cmpunitconverter.distance

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.layout.PaneAdaptedValue
import androidx.compose.material3.adaptive.layout.SupportingPaneScaffold
import androidx.compose.material3.adaptive.layout.SupportingPaneScaffoldRole
import androidx.compose.material3.adaptive.layout.calculatePaneScaffoldDirective
import androidx.compose.material3.adaptive.navigation.rememberSupportingPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import de.thomaskuenneth.cmpunitconverter.ScaffoldWithBackArrow
import de.thomaskuenneth.cmpunitconverter.app.AppViewModel
import de.thomaskuenneth.cmpunitconverter.temperature.SupportingPane

@OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DistanceConverterScreen(appViewModel: AppViewModel, distanceViewModel: DistanceViewModel) {
    val navigator = rememberSupportingPaneScaffoldNavigator(
        scaffoldDirective = calculatePaneScaffoldDirective(currentWindowAdaptiveInfo())
    )
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    ScaffoldWithBackArrow(
        navigator = navigator,
        scrollBehavior = scrollBehavior,
        infoClicked = { appViewModel.setShouldShowAbout(true) },
        settingsClicked = { appViewModel.setShouldShowSettings(true) }) {
        SupportingPaneScaffold(
            directive = navigator.scaffoldDirective, mainPane = {
                DistanceConverter(
                    viewModel = distanceViewModel,
                    scrollBehavior = scrollBehavior,
                    shouldShowButton = navigator.scaffoldValue[SupportingPaneScaffoldRole.Supporting] == PaneAdaptedValue.Hidden
                ) {
                    navigator.navigateTo(SupportingPaneScaffoldRole.Supporting)
                }
            }, supportingPane = { SupportingPane() }, value = navigator.scaffoldValue
        )
    }
}
