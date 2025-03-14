package de.thomaskuenneth.cmpunitconverter

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import de.thomaskuenneth.cmpunitconverter.composeapp.generated.resources.Res
import de.thomaskuenneth.cmpunitconverter.composeapp.generated.resources.convert
import de.thomaskuenneth.cmpunitconverter.composeapp.generated.resources.learn_more
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource


@Composable
fun NumberTextField(
    value: String,
    placeholder: StringResource,
    modifier: Modifier = Modifier,
    keyboardActionCallback: () -> Unit,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = value,
        onValueChange = {
            onValueChange(it)
        }, placeholder = {
            Text(text = stringResource(placeholder))
        }, modifier = modifier,
        keyboardActions = KeyboardActions(onAny = {
            keyboardActionCallback()
        }),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
        ),
        singleLine = true
    )
}

@Composable
fun Result(value: Float, unit: StringResource) {
    val result = if (value.isNaN()) "" else "${value.convertToLocalizedString()} ${
        stringResource(unit)
    }"
    if (result.isNotEmpty()) {
        Text(
            text = result, style = MaterialTheme.typography.headlineSmall
        )
    }
}

@Composable
fun LearnMoreButton(visible: Boolean, onClick: () -> Unit) {
    if (visible) {
        TextButton(onClick = onClick) {
            Text(text = stringResource(Res.string.learn_more))
        }
    }
}

@Composable
fun ConvertButton(
    enabled: Boolean,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick, enabled = enabled, modifier = Modifier.padding(bottom = 16.dp)
    ) {
        Text(text = stringResource(Res.string.convert))
    }
}
