package de.thomaskuenneth.cmpunitconverter.app

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.thomaskuenneth.cmpunitconverter.VERSION
import de.thomaskuenneth.cmpunitconverter.composeapp.generated.resources.Res
import de.thomaskuenneth.cmpunitconverter.composeapp.generated.resources.app_icon
import de.thomaskuenneth.cmpunitconverter.composeapp.generated.resources.app_name
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun About(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(Res.drawable.app_icon), null, modifier = Modifier.size(96.dp)
        )
        Text(
            text = stringResource(Res.string.app_name), modifier = Modifier.padding(top = 16.dp)
        )
        Text(
            text = VERSION
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutBottomSheet(visible: Boolean, closeSheet: () -> Unit) {
    if (visible) {
        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        ModalBottomSheet(sheetState = sheetState, onDismissRequest = { closeSheet() }) {
            About(modifier = Modifier.align(Alignment.CenterHorizontally))
        }
    }
}
