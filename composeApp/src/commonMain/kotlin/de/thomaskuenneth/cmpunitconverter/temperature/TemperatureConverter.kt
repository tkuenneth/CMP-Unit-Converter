package de.thomaskuenneth.cmpunitconverter.temperature

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
import de.thomaskuenneth.cmpunitconverter.composeapp.generated.resources.*
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun TemperatureConverter(viewModel: TemperatureViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val convertedValue by viewModel.convertedTemperature.collectAsStateWithLifecycle()
    val enabled = remember(uiState.temperature, uiState.sourceUnit, uiState.destinationUnit) {
        !viewModel.getTemperatureAsFloat().isNaN() && uiState.sourceUnit != uiState.destinationUnit
    }
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TemperatureTextField(
            temperature = uiState.temperature,
            modifier = Modifier.padding(bottom = 16.dp),
            keyboardActionCallback = { viewModel.convert() },
            onValueChange = { viewModel.setTemperature(it) })
        TemperatureButtonRow(
            selected = uiState.sourceUnit, label = Res.string.from, modifier = Modifier.padding(bottom = 16.dp)
        ) { unit: TemperatureUnit ->
            viewModel.setSourceUnit(unit)
        }
        TemperatureButtonRow(
            selected = uiState.destinationUnit, label = Res.string.to, modifier = Modifier.padding(bottom = 16.dp)
        ) { unit: TemperatureUnit ->
            viewModel.setDestinationUnit(unit)
        }
        Button(
            onClick = { viewModel.convert() }, enabled = enabled, modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text(text = stringResource(Res.string.convert))
        }
        Result(value = convertedValue, unit = uiState.destinationUnit)
    }
}

@Composable
fun TemperatureTextField(
    temperature: String,
    modifier: Modifier = Modifier,
    keyboardActionCallback: () -> Unit,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = temperature, onValueChange = {
            onValueChange(it)
        }, placeholder = {
            Text(text = stringResource(Res.string.placeholder_temperature))
        }, modifier = modifier, keyboardActions = KeyboardActions(onAny = {
            keyboardActionCallback()
        }), keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
        ), singleLine = true
    )
}

@Composable
fun Result(value: Float, unit: TemperatureUnit) {
    val result = if (value.isNaN()) "" else "$value ${
        if (unit == TemperatureUnit.Celsius) stringResource(Res.string.celsius) else stringResource(Res.string.fahrenheit)
    }"
    if (result.isNotEmpty()) {
        Text(
            text = result, style = MaterialTheme.typography.headlineSmall
        )
    }
}

@Composable
fun TemperatureButtonRow(
    selected: TemperatureUnit, label: StringResource, modifier: Modifier = Modifier, onClick: (TemperatureUnit) -> Unit
) {
    Row {
        Text(
            modifier = Modifier.alignByBaseline().width(80.dp),
            text = stringResource(label),
            textAlign = TextAlign.Start
        )
        SingleChoiceSegmentedButtonRow(modifier = modifier.alignByBaseline()) {
            SegmentedTemperatureButton(
                selected = selected == TemperatureUnit.Celsius, unit = TemperatureUnit.Celsius, onClick = onClick
            )
            SegmentedTemperatureButton(
                selected = selected == TemperatureUnit.Fahrenheit, unit = TemperatureUnit.Fahrenheit, onClick = onClick
            )
        }
    }
}

@Composable
fun SingleChoiceSegmentedButtonRowScope.SegmentedTemperatureButton(
    selected: Boolean, unit: TemperatureUnit, onClick: (TemperatureUnit) -> Unit
) {
    SegmentedButton(
        selected = selected, onClick = { onClick(unit) }, shape = SegmentedButtonDefaults.itemShape(
            index = unit.ordinal, count = TemperatureUnit.entries.size
        ), label = {
            Text(
                text = stringResource(
                    when (unit) {
                        TemperatureUnit.Celsius -> Res.string.celsius
                        TemperatureUnit.Fahrenheit -> Res.string.fahrenheit
                    }
                )
            )
        })
}
