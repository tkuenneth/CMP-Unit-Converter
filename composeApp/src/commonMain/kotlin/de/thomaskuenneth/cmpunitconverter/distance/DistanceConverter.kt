package de.thomaskuenneth.cmpunitconverter.distance

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cmpunitconverter.composeapp.generated.resources.*
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource


@Composable
fun DistanceConverter(viewModel: DistanceViewModel) {
    val currentValue by viewModel.distance.collectAsStateWithLifecycle()
    val sourceUnit by viewModel.sourceUnit.collectAsStateWithLifecycle()
    val destinationUnit by viewModel.destinationUnit.collectAsStateWithLifecycle()
    val convertedValue by viewModel.convertedDistance.collectAsStateWithLifecycle()
    val enabled = remember(currentValue, sourceUnit, destinationUnit) {
        !viewModel.getDistanceAsFloat().isNaN() && sourceUnit != destinationUnit
    }
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DistanceTextField(
            distance = currentValue,
            modifier = Modifier.padding(bottom = 16.dp),
            keyboardActionCallback = { viewModel.convert() },
            onValueChange = { viewModel.setDistance(it) })
        DistanceButtonRow(
            selected = sourceUnit, label = Res.string.from, modifier = Modifier.padding(bottom = 16.dp)
        ) { unit: DistanceUnit ->
            viewModel.setSourceUnit(unit)
        }
        DistanceButtonRow(
            selected = destinationUnit, label = Res.string.to, modifier = Modifier.padding(bottom = 16.dp)
        ) { unit: DistanceUnit ->
            viewModel.setDestinationUnit(unit)
        }
        Button(
            onClick = { viewModel.convert() }, enabled = enabled, modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text(text = stringResource(Res.string.convert))
        }
        Result(value = convertedValue, unit = destinationUnit)
    }
}

@Composable
fun DistanceTextField(
    distance: String, modifier: Modifier = Modifier, keyboardActionCallback: () -> Unit, onValueChange: (String) -> Unit
) {
    TextField(
        value = distance, onValueChange = {
        onValueChange(it)
    }, placeholder = {
        Text(text = stringResource(Res.string.placeholder_distance))
    }, modifier = modifier, keyboardActions = KeyboardActions(onAny = {
        keyboardActionCallback()
    }), keyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
    ), singleLine = true
    )
}

@Composable
fun Result(value: Float, unit: DistanceUnit) {
    val result = if (value.isNaN()) "" else "$value ${
        if (unit == DistanceUnit.Meter) stringResource(Res.string.meter) else stringResource(Res.string.mile)
    }"
    if (result.isNotEmpty()) {
        Text(
            text = result, style = MaterialTheme.typography.headlineSmall
        )
    }
}

@Composable
fun DistanceButtonRow(
    selected: DistanceUnit, label: StringResource, modifier: Modifier = Modifier, onClick: (DistanceUnit) -> Unit
) {
    Row {
        Text(
            modifier = Modifier.alignByBaseline().width(80.dp),
            text = stringResource(label),
            textAlign = TextAlign.Start
        )
        SingleChoiceSegmentedButtonRow(modifier = modifier.alignByBaseline()) {
            SegmentedDistanceButton(
                selected = selected == DistanceUnit.Meter, unit = DistanceUnit.Meter, onClick = onClick
            )
            SegmentedDistanceButton(
                selected = selected == DistanceUnit.Mile, unit = DistanceUnit.Mile, onClick = onClick
            )
        }
    }
}

@Composable
fun SingleChoiceSegmentedButtonRowScope.SegmentedDistanceButton(
    selected: Boolean, unit: DistanceUnit, onClick: (DistanceUnit) -> Unit
) {
    SegmentedButton(
        selected = selected, onClick = { onClick(unit) }, shape = SegmentedButtonDefaults.itemShape(
            index = unit.ordinal, count = DistanceUnit.entries.size
        ), label = {
            Text(
                text = stringResource(
                    when (unit) {
                        DistanceUnit.Meter -> Res.string.meter
                        DistanceUnit.Mile -> Res.string.mile
                    }
                )
            )
        })
}
