package de.thomaskuenneth.cmpunitconverter.distance

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import de.thomaskuenneth.cmpunitconverter.*
import de.thomaskuenneth.cmpunitconverter.composeapp.generated.resources.*
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DistanceConverter(
    viewModel: DistanceViewModel,
    scrollBehavior: TopAppBarScrollBehavior,
    shouldShowButton: Boolean,
    navigateToSupportingPane: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var textFieldValue by remember(uiState.distance) {
        mutableStateOf(
            TextFieldValue(
                text = uiState.distance.convertToLocalizedString(), selection = TextRange(Int.MAX_VALUE)
            )
        )
    }
    val convertedValue by viewModel.convertedDistance.collectAsStateWithLifecycle()
    val enabled = remember(textFieldValue, uiState.sourceUnit, uiState.destinationUnit) {
        !viewModel.getDistanceAsFloat().isNaN() && uiState.sourceUnit != uiState.destinationUnit
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
                viewModel.setDistance(it.text)
            })
        DistanceButtonRow(
            selected = uiState.sourceUnit, label = Res.string.from, modifier = Modifier.padding(bottom = 16.dp)
        ) { unit: DistanceUnit ->
            viewModel.setSourceUnit(unit)
        }
        DistanceButtonRow(
            selected = uiState.destinationUnit, label = Res.string.to, modifier = Modifier.padding(bottom = 16.dp)
        ) { unit: DistanceUnit ->
            viewModel.setDestinationUnit(unit)
        }
        ConvertButton(enabled = enabled) { viewModel.convert() }
        Result(
            value = convertedValue,
            unit = if (uiState.destinationUnit == DistanceUnit.Meter) Res.string.meter else Res.string.mile
        )
        LearnMoreButton(visible = shouldShowButton, onClick = navigateToSupportingPane)
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
