package de.thomaskuenneth.cmpunitconverter.composables

import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRowScope
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import de.thomaskuenneth.cmpunitconverter.UnitsAndScales
import org.jetbrains.compose.resources.stringResource

@Composable
fun SingleChoiceSegmentedButtonRowScope.SegmentedUnitsAndScalesButton(
    selected: Boolean, unit: UnitsAndScales, entries: List<UnitsAndScales>, onClick: (UnitsAndScales) -> Unit
) {
    SegmentedButton(
        selected = selected, onClick = { onClick(unit) }, shape = SegmentedButtonDefaults.itemShape(
            index = entries.indexOf(unit), count = entries.size
        ), label = {
            Text(
                text = stringResource(unit.unit)
            )
        })
}
