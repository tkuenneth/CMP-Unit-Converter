package de.thomaskuenneth.cmpunitconverter.composables

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.PaneAdaptedValue
import androidx.compose.material3.adaptive.layout.SupportingPaneScaffold
import androidx.compose.material3.adaptive.layout.SupportingPaneScaffoldRole
import androidx.compose.material3.adaptive.layout.rememberPaneExpansionState
import androidx.compose.material3.adaptive.navigation.ThreePaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import de.thomaskuenneth.cmpunitconverter.AbstractConverterViewModel
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ConverterScreen(
    navigator: ThreePaneScaffoldNavigator<Any>,
    viewModel: AbstractConverterViewModel,
    scrollBehavior: TopAppBarScrollBehavior
) {
    val viewModelState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember(viewModelState) { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) {
        SupportingPaneScaffold(
            directive = navigator.scaffoldDirective,
            value = navigator.scaffoldValue,
            mainPane = {
                Converter(
                    viewModel = viewModel,
                    scrollBehavior = scrollBehavior,
                    shouldShowButton = navigator.scaffoldValue[SupportingPaneScaffoldRole.Supporting] == PaneAdaptedValue.Hidden
                ) {
                    scope.launch { navigator.navigateTo(SupportingPaneScaffoldRole.Supporting) }
                }
            },
            supportingPane = {
                with(viewModel.supportingPaneUseCase) {
                    val supportingPaneState by stateFlow.collectAsStateWithLifecycle()
                    SupportingPane(
                        uiState = supportingPaneState,
                        showUnits = navigator.scaffoldValue[SupportingPaneScaffoldRole.Main] == PaneAdaptedValue.Hidden,
                        readMoreOnWikipedia = {
                            openInBrowser(
                                value = supportingPaneState.current,
                                completionHandler = viewModel::handleOpenInBrowserResult
                            )
                        },
                        clearConversionsHistory = ::clearConversionsHistory
                    )
                }
            },
            paneExpansionState = rememberPaneExpansionState()
        )
        viewModelState.snackbarVisibility.let { visibility ->
            if (visibility is AbstractConverterViewModel.SnackbarVisibility.Message) {
                val message = stringResource(visibility.message)
                LaunchedEffect(visibility) {
                    snackbarHostState.showSnackbar(
                        message = message,
                        withDismissAction = true,
                        duration = SnackbarDuration.Indefinite
                    )
                    snackbarHostState.currentSnackbarData?.dismiss()
                    viewModel.setSnackbarVisibility(AbstractConverterViewModel.SnackbarVisibility.Hidden)
                }
            }
        }
    }
}
