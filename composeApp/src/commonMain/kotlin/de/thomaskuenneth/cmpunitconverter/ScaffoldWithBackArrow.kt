package de.thomaskuenneth.cmpunitconverter

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import de.thomaskuenneth.cmpunitconverter.app.AppViewModel
import de.thomaskuenneth.cmpunitconverter.composeapp.generated.resources.Res
import de.thomaskuenneth.cmpunitconverter.composeapp.generated.resources.about_short
import de.thomaskuenneth.cmpunitconverter.composeapp.generated.resources.app_name
import de.thomaskuenneth.cmpunitconverter.composeapp.generated.resources.back
import de.thomaskuenneth.cmpunitconverter.composeapp.generated.resources.settings_short
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldWithBackArrow(
    shouldShowBack: Boolean,
    navigateBack: suspend () -> Unit,
    viewModel: AppViewModel,
    content: @Composable (PaddingValues, TopAppBarScrollBehavior) -> Unit
) {
    val scope = rememberCoroutineScope()
    val navigateBackAsync: () -> Unit = { scope.launch { navigateBack() } }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            if (shouldUseScaffold()) TopAppBar(
                navigationIcon = {
                    if (shouldShowBack) IconButton(onClick = navigateBackAsync) {
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
        BackHandler(shouldShowBack) {
            navigateBackAsync()
        }
    }
}
