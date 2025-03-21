package de.thomaskuenneth.cmpunitconverter.distance

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import de.thomaskuenneth.cmpunitconverter.*
import de.thomaskuenneth.cmpunitconverter.composeapp.generated.resources.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DistanceConverter(
    viewModel: DistanceViewModel,
    scrollBehavior: TopAppBarScrollBehavior,
    shouldShowButton: Boolean,
    navigateToSupportingPane: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var textFieldValue by remember(uiState.value) {
        mutableStateOf(
            TextFieldValue(
                text = uiState.value.convertToLocalizedString(), selection = TextRange(Int.MAX_VALUE)
            )
        )
    }
    val convertedValue by viewModel.convertedValue.collectAsStateWithLifecycle()
    val enabled = remember(textFieldValue, uiState.sourceUnit, uiState.destinationUnit) {
        !viewModel.getValueAsFloat().isNaN() && uiState.sourceUnit != uiState.destinationUnit
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
            value = textFieldValue,
            placeholder = Res.string.placeholder_distance,
            modifier = Modifier.padding(bottom = 16.dp),
            keyboardActionCallback = { viewModel.convert() },
            onValueChange = {
                textFieldValue = it
                viewModel.setValue(it.text)
            })
        UnitsAndScalesButtonRow(
            entries = DistanceUnit,
            selected = uiState.sourceUnit,
            label = Res.string.from,
            modifier = Modifier.padding(bottom = 16.dp)
        ) { unit: UnitsAndScales ->
            viewModel.setSourceUnit(unit)
        }
        UnitsAndScalesButtonRow(
            entries = DistanceUnit,
            selected = uiState.destinationUnit,
            label = Res.string.to,
            modifier = Modifier.padding(bottom = 16.dp)
        ) { unit: UnitsAndScales ->
            viewModel.setDestinationUnit(unit)
        }
        ConvertButton(enabled = enabled) { viewModel.convert() }
        Result(
            value = convertedValue,
            unit = if (uiState.destinationUnit == UnitsAndScales.Meter) Res.string.meter else Res.string.mile
        )
        LearnMoreButton(visible = shouldShowButton, onClick = navigateToSupportingPane)
    }
}
