package de.thomaskuenneth.cmpunitconverter.distance

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
    val convertedValue by viewModel.convertedDistance.collectAsStateWithLifecycle()
    val enabled = remember(uiState.distanceAsString, uiState.sourceUnit, uiState.destinationUnit) {
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
            value = uiState.distanceAsString,
            placeholder = Res.string.placeholder_distance,
            modifier = Modifier.padding(bottom = 16.dp),
            keyboardActionCallback = { viewModel.convert() },
            onValueChange = { viewModel.setDistance(it) })
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
        Button(
            onClick = { viewModel.convert() }, enabled = enabled, modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text(text = stringResource(Res.string.convert))
        }
        de.thomaskuenneth.cmpunitconverter.Result(
            value = convertedValue,
            unit = if (uiState.destinationUnit == DistanceUnit.Meter) Res.string.meter else Res.string.mile
        )
        if (shouldShowButton) {
            TextButton(onClick = navigateToSupportingPane) {
                Text(text = stringResource(Res.string.learn_more))
            }
        }
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
