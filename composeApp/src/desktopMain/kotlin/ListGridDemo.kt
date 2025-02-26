package de.thomaskuenneth.cmp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import cmpunitconverter.composeapp.generated.resources.Res
import cmpunitconverter.composeapp.generated.resources.list_grid_demo
import org.jetbrains.compose.resources.stringResource

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = stringResource(Res.string.list_grid_demo),
    ) {
        ListGridDemo()
    }
}
