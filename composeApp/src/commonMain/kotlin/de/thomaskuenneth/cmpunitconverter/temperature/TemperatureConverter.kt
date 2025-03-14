package de.thomaskuenneth.cmpunitconverter.temperature

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import de.thomaskuenneth.cmpunitconverter.*
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
    var temperatureAsString by remember(uiState.temperature) { mutableStateOf(uiState.temperature.convertToLocalizedString()) }
    val convertedValue by viewModel.convertedTemperature.collectAsStateWithLifecycle()
    val enabled = remember(temperatureAsString, uiState.sourceUnit, uiState.destinationUnit) {
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
            value = temperatureAsString,
            placeholder = Res.string.placeholder_temperature,
            modifier = Modifier.padding(bottom = 16.dp),
            keyboardActionCallback = { viewModel.convert() },
            onValueChange = {
                temperatureAsString = it
                viewModel.setTemperature(it)
            })
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
        ConvertButton(enabled = enabled) { viewModel.convert() }
        Result(
            value = convertedValue,
            unit = if (uiState.destinationUnit == TemperatureUnit.Celsius) Res.string.celsius else Res.string.fahrenheit
        )
        LearnMoreButton(visible = shouldShowButton, onClick = navigateToSupportingPane)
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
