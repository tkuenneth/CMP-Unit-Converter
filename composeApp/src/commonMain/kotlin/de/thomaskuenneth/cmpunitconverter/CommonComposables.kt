package de.thomaskuenneth.cmpunitconverter

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
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

fun Float.convertToString(): String = if (this.isNaN()) {
    ""
} else {
    if (this % 1 == 0F) {
        toInt().toString()
    } else {
        toString()
    }
}

@Composable
fun Result(value: Float, unit: StringResource) {
    val result = if (value.isNaN()) "" else "$value ${
        stringResource(unit)
    }"
    if (result.isNotEmpty()) {
        Text(
            text = result, style = MaterialTheme.typography.headlineSmall
        )
    }
}
