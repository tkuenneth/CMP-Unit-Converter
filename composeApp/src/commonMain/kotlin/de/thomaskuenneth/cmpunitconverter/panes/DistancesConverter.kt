package de.thomaskuenneth.cmpunitconverter.panes

import androidx.compose.foundation.layout.*
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
import de.thomaskuenneth.cmpunitconverter.DistanceUnit
import de.thomaskuenneth.cmpunitconverter.viewmodels.DistancesViewModel
import org.jetbrains.compose.resources.stringResource

@Composable
fun DistancesConverter(viewModel: DistancesViewModel) {
    val strMeter = stringResource(Res.string.meter)
    val strMile = stringResource(Res.string.mile)
    val currentValue by viewModel.distance.collectAsStateWithLifecycle()
    val unit by viewModel.unit.collectAsStateWithLifecycle()
    val convertedValue by viewModel.convertedDistance.collectAsStateWithLifecycle()
    val result by remember(convertedValue) {
        mutableStateOf(
            if (convertedValue.isNaN()) ""
            else "$convertedValue ${
                if (unit == DistanceUnit.meters) strMile else strMeter
            }"
        )
    }
    val enabled = remember(currentValue) { !viewModel.getDistanceAsFloat().isNaN() }
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DistanceTextField(
            distance = currentValue,
            modifier = Modifier.padding(bottom = 16.dp),
            onValueChange = { viewModel.setDistance(it) },
            keyboardActionCallback = { viewModel.convert() }
        )
        DistanceButtonGroup(
            selected = unit,
            modifier = Modifier.padding(bottom = 16.dp)
        ) { unit: DistanceUnit ->
            viewModel.setUnit(unit)
        }
        Button(
            onClick = { viewModel.convert() },
            enabled = enabled
        ) {
            Text(text = stringResource(Res.string.convert))
        }
        if (result.isNotEmpty()) {
            Text(
                text = result,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}

@Composable
fun DistanceTextField(
    distance: String,
    modifier: Modifier = Modifier,
    keyboardActionCallback: () -> Unit,
    onValueChange: (String) -> Unit
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
fun DistanceButtonGroup(
    selected: DistanceUnit, modifier: Modifier = Modifier, onClick: (DistanceUnit) -> Unit
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        DistanceRadioButton(
            selected = selected == DistanceUnit.meters,
            unit = DistanceUnit.meters,
            onClick = onClick
        )
        DistanceRadioButton(
            selected = selected == DistanceUnit.miles,
            unit = DistanceUnit.miles,
            onClick = onClick
        )
    }
}

@Composable
fun DistanceRadioButton(
    selected: Boolean, unit: DistanceUnit, onClick: (DistanceUnit) -> Unit, modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        RadioButton(
            selected = selected,
            onClick = { onClick(unit) })
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = stringResource(
                when (unit) {
                    DistanceUnit.meters -> Res.string.meter
                    DistanceUnit.miles -> Res.string.mile
                }
            )
        )
    }
}
