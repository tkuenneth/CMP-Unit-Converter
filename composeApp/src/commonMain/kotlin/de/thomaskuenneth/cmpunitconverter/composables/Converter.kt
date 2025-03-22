package de.thomaskuenneth.cmpunitconverter.composables

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
import de.thomaskuenneth.cmpunitconverter.composeapp.generated.resources.Res
import de.thomaskuenneth.cmpunitconverter.composeapp.generated.resources.from
import de.thomaskuenneth.cmpunitconverter.composeapp.generated.resources.to

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Converter(
    viewModel: AbstractConverterViewModel,
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
    val enabled = remember(uiState.value, uiState.sourceUnit, uiState.destinationUnit) {
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
            placeholder = uiState.placeholder,
            modifier = Modifier.padding(bottom = 16.dp),
            keyboardActionCallback = { if (enabled) viewModel.convert() },
            onValueChange = {
                textFieldValue = it
                viewModel.setValue(it.text)
            })
        UnitsAndScalesButtonRow(
            entries = uiState.entries,
            selected = uiState.sourceUnit,
            label = Res.string.from,
            modifier = Modifier.padding(bottom = 16.dp)
        ) { unit: UnitsAndScales ->
            viewModel.setSourceUnit(unit)
        }
        UnitsAndScalesButtonRow(
            entries = uiState.entries,
            selected = uiState.destinationUnit,
            label = Res.string.to,
            modifier = Modifier.padding(bottom = 16.dp)
        ) { unit: UnitsAndScales ->
            viewModel.setDestinationUnit(unit)
        }
        ConvertButton(enabled = enabled) { viewModel.convert() }
        ResultWithUnit(
            value = convertedValue,
            unit = uiState.destinationUnit
        )
        LearnMoreButton(visible = shouldShowButton, onClick = navigateToSupportingPane)
    }
}
