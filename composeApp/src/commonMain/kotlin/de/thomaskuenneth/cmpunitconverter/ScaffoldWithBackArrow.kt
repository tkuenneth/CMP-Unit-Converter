package de.thomaskuenneth.cmpunitconverter

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import de.thomaskuenneth.cmpunitconverter.app.AppViewModel
import de.thomaskuenneth.cmpunitconverter.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldWithBackArrow(
    shouldShowBack: Boolean,
    navigateBack: () -> Unit,
    viewModel: AppViewModel,
    content: @Composable (PaddingValues, TopAppBarScrollBehavior) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    if (shouldUseScaffold()) {
        Scaffold(
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            topBar = {
                CenterAlignedTopAppBar(
                    navigationIcon = {
                        if (shouldShowBack) IconButton(onClick = navigateBack) {
                            Icon(
                                Icons.AutoMirrored.Default.ArrowBack,
                                contentDescription = stringResource(Res.string.back)
                            )
                        }
                    },
                    scrollBehavior = scrollBehavior,
                    actions = {
                        IconButton(onClick = { viewModel.setShouldShowSettings(true) }) {
                            Icon(
                                Icons.Default.Settings,
                                contentDescription = stringResource(Res.string.settings_short)
                            )
                        }
                        IconButton(onClick = { viewModel.setShouldShowAbout(true) }) {
                            Icon(
                                Icons.Default.Info,
                                contentDescription = stringResource(Res.string.about_short)
                            )
                        }
                    },
                    title = { Text(text = stringResource(Res.string.app_name)) })
            }) { innerPadding ->
            content(innerPadding, scrollBehavior)
        }
    } else {
        content(PaddingValues.Absolute(0.dp, 0.dp, 0.dp, 0.dp), scrollBehavior)
    }
    BackHandler(shouldShowBack) {
        navigateBack()
    }
}
