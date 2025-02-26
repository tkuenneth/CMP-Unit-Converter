package de.thomaskuenneth.cmpunitconverter.panes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cmpunitconverter.composeapp.generated.resources.*
import de.thomaskuenneth.cmpunitconverter.TemperatureUnit
import de.thomaskuenneth.cmpunitconverter.viewmodels.TemperatureViewModel
import org.jetbrains.compose.resources.stringResource

@Composable
fun TemperatureConverter(viewModel: TemperatureViewModel) {
    val strCelsius = stringResource(Res.string.celsius)
    val strFahrenheit = stringResource(Res.string.fahrenheit)
    val currentValue by viewModel.temperature.collectAsStateWithLifecycle()
    val unit by viewModel.unit.collectAsStateWithLifecycle()
    val convertedValue by viewModel.convertedTemperature.collectAsStateWithLifecycle()
    val result by remember(convertedValue) {
        mutableStateOf(
            if (convertedValue.isNaN()) ""
            else "$convertedValue ${
                if (unit == TemperatureUnit.celsius) strCelsius else strFahrenheit
            }"
        )
    }
    val enabled = remember(currentValue) { !viewModel.getTemperatureAsFloat().isNaN() }
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TemperatureTextField(
            temperature = currentValue,
            modifier = Modifier.padding(bottom = 16.dp),
            keyboardActionCallback = { viewModel.convert() },
            onValueChange = { viewModel.setTemperature(it) })
        TemperatureButtonGroup(
            selected = unit, modifier = Modifier.padding(bottom = 16.dp)
        ) { unit: TemperatureUnit ->
            viewModel.setUnit(unit)
        }
        Button(
            onClick = { viewModel.convert() },
            enabled = enabled,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text(text = stringResource(Res.string.convert))
        }
        if (result.isNotEmpty()) {
            Text(
                text = result, style = MaterialTheme.typography.headlineSmall
            )
        }
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
            Text(text = stringResource(Res.string.placeholder))
        }, modifier = modifier, keyboardActions = KeyboardActions(onAny = {
            keyboardActionCallback()
        }), keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
        ), singleLine = true
    )
}

@Composable
fun TemperatureButtonGroup(
    selected: TemperatureUnit, modifier: Modifier = Modifier, onClick: (TemperatureUnit) -> Unit
) {
    Row(modifier = modifier) {
        TemperatureRadioButton(
            selected = selected == TemperatureUnit.celsius, unit = TemperatureUnit.celsius, onClick = onClick
        )
        TemperatureRadioButton(
            selected = selected == TemperatureUnit.fahrenheit,
            unit = TemperatureUnit.fahrenheit,
            onClick = onClick,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Composable
fun TemperatureRadioButton(
    selected: Boolean, unit: TemperatureUnit, onClick: (TemperatureUnit) -> Unit, modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = modifier
    ) {
        RadioButton(
            selected = selected, onClick = {
                onClick(unit)
            })
        Text(
            text = stringResource(
                when (unit) {
                    TemperatureUnit.celsius -> Res.string.celsius
                    TemperatureUnit.fahrenheit -> Res.string.fahrenheit
                }
            ), modifier = Modifier.padding(start = 8.dp)
        )
    }
}
