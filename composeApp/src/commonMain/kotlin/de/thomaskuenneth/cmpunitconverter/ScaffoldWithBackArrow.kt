package de.thomaskuenneth.cmpunitconverter

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation.ThreePaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import de.thomaskuenneth.cmpunitconverter.composeapp.generated.resources.Res
import de.thomaskuenneth.cmpunitconverter.composeapp.generated.resources.app_name
import de.thomaskuenneth.cmpunitconverter.composeapp.generated.resources.back
import org.jetbrains.compose.resources.stringResource


@OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun <T> ScaffoldWithBackArrow(
    navigator: ThreePaneScaffoldNavigator<T>, scrollBehavior: TopAppBarScrollBehavior, content: @Composable () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(navigationIcon = {
                if (navigator.canNavigateBack()) IconButton(onClick = { navigator.navigateBack() }) {
                    Icon(
                        Icons.AutoMirrored.Default.ArrowBack, contentDescription = stringResource(Res.string.back)
                    )
                }
            }, scrollBehavior = scrollBehavior, title = { Text(text = stringResource(Res.string.app_name)) })
        }) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            BackHandler(navigator.canNavigateBack()) {
                navigator.navigateBack()
            }
            content()
        }
    }
}
