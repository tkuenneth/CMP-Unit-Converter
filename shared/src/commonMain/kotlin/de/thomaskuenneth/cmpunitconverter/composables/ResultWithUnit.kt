package de.thomaskuenneth.cmpunitconverter.composables

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import de.thomaskuenneth.cmpunitconverter.UnitsAndScales

@Composable
fun ResultWithUnit(value: Float, unit: UnitsAndScales) {
    val result = value.convertToStringWithUnit(unit.unit)
    if (result.isNotEmpty()) {
        Text(
            text = result, style = MaterialTheme.typography.headlineSmall
        )
    }
}
