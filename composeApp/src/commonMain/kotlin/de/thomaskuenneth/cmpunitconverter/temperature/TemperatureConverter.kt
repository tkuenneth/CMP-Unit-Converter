package de.thomaskuenneth.cmpunitconverter.temperature

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import de.thomaskuenneth.cmpunitconverter.NumberTextField
import de.thomaskuenneth.cmpunitconverter.Result
import de.thomaskuenneth.cmpunitconverter.composeapp.generated.resources.*
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TemperatureConverter(
    viewModel: TemperatureViewModel,
    scrollBehavior: TopAppBarScrollBehavior,
    shouldShowButton: Boolean,
    navigateToSupportingPane: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val convertedValue by viewModel.convertedTemperature.collectAsStateWithLifecycle()
    val enabled = remember(uiState.temperatureAsString, uiState.sourceUnit, uiState.destinationUnit) {
        !viewModel.getTemperatureAsFloat().isNaN() && uiState.sourceUnit != uiState.destinationUnit
    }
    Column(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NumberTextField(
            value = uiState.temperatureAsString,
            placeholder = Res.string.placeholder_temperature,
            modifier = Modifier.padding(bottom = 16.dp),
            keyboardActionCallback = { viewModel.convert() },
            onValueChange = { viewModel.setTemperatureAsString(it) })
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
        Result(
            value = convertedValue,
            unit = if (uiState.destinationUnit == TemperatureUnit.Celsius) Res.string.celsius else Res.string.fahrenheit
        )
        if (shouldShowButton) {
            TextButton(onClick = navigateToSupportingPane) {
                Text(text = stringResource(Res.string.learn_more))
            }
        }
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
