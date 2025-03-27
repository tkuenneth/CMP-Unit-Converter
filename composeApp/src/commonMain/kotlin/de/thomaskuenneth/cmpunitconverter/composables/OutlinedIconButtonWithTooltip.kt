package de.thomaskuenneth.cmpunitconverter.composables

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutlinedIconButtonWithTooltip(
    icon: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier
) {
    TooltipBox(
        positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(), tooltip = {
            PlainTooltip {
                Text(text = contentDescription)
            }
        }, state = rememberTooltipState()
    ) {
        OutlinedButton(onClick = onClick, modifier = modifier, enabled = enabled) {
            Icon(
                icon, contentDescription = contentDescription
            )
        }
    }
}
