package de.thomaskuenneth.cmpunitconverter

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogWindow
import de.thomaskuenneth.cmpunitconverter.composeapp.generated.resources.Res
import de.thomaskuenneth.cmpunitconverter.composeapp.generated.resources.app_icon
import de.thomaskuenneth.cmpunitconverter.composeapp.generated.resources.app_name
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun AboutDialog(onCloseRequest: () -> Unit) {
    DialogWindow(
        onCloseRequest = onCloseRequest,
        icon = painterResource(Res.drawable.app_icon),
        resizable = false,
        title = stringResource(Res.string.app_name)
    ) {
        Column(
            modifier = Modifier.background(MaterialTheme.colorScheme.surface).fillMaxSize().padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(Res.drawable.app_icon), null, modifier = Modifier.requiredSize(96.dp)
            )
            Text(
                text = stringResource(Res.string.app_name), modifier = Modifier.padding(top = 16.dp)
            )
            Text(
                text = VERSION
            )
        }
    }
}
