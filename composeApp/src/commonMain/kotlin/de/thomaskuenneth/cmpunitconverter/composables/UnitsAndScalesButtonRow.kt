package de.thomaskuenneth.cmpunitconverter.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import de.thomaskuenneth.cmpunitconverter.UnitsAndScales
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun UnitsAndScalesButtonRow(
    entries: List<UnitsAndScales>,
    selected: UnitsAndScales,
    label: StringResource,
    modifier: Modifier = Modifier,
    onClick: (UnitsAndScales) -> Unit
) {
    Row {
        Text(
            modifier = Modifier.alignByBaseline().width(80.dp),
            text = stringResource(label),
            textAlign = TextAlign.Start
        )
        SingleChoiceSegmentedButtonRow(modifier = modifier.alignByBaseline()) {
            entries.forEach { unit ->
                SegmentedUnitsAndScalesButton(
                    selected = selected == unit, unit = unit, entries = entries, onClick = onClick
                )
            }
        }
    }
}
