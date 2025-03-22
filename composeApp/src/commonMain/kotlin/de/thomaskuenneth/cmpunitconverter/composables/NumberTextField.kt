package de.thomaskuenneth.cmpunitconverter.composables

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import de.thomaskuenneth.cmpunitconverter.convertLocalizedStringToFloat
import de.thomaskuenneth.cmpunitconverter.convertToLocalizedString
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun NumberTextField(
    value: Float,
    placeholder: StringResource,
    modifier: Modifier = Modifier,
    keyboardActionCallback: () -> Unit,
    onValueChange: (Float) -> Unit
) {
    fun String.countSpecialChars(): Int = filter { !it.isDigit() && it != '-' }.length
    var lastText by remember(value) { mutableStateOf(value.convertToLocalizedString()) }
    var lastIndex by remember { mutableStateOf(Int.MAX_VALUE) }
    var previousSpecialCharsCount by remember { mutableStateOf(lastText.countSpecialChars()) }
    var textFieldValue by remember(lastText) {
        val currentSpecialCharsCount = lastText.countSpecialChars()
        val offset = if (currentSpecialCharsCount != previousSpecialCharsCount) 1 else 0
        mutableStateOf(
            TextFieldValue(
                text = lastText, selection = TextRange((lastIndex + offset).coerceAtLeast(0))
            ).also {
                previousSpecialCharsCount = currentSpecialCharsCount
                lastIndex = it.selection.start
            })
    }
    TextField(
        value = textFieldValue, onValueChange = { newValue ->
            textFieldValue = newValue.copy(
                text = newValue.text.filter { it.isDigit() || it == '.' || it == ',' || it == '-' })
            lastText = textFieldValue.text
            lastIndex = textFieldValue.selection.start
            with(lastText) {
                if (isEmpty()) {
                    onValueChange(Float.NaN)
                } else {
                    if (elementAt(0).isDigit() || length > 1) {
                        convertLocalizedStringToFloat().let { floatValue ->
                            if (!floatValue.isNaN()) {
                                onValueChange(floatValue)
                            }
                        }
                    }
                }
            }
        },
        placeholder = {
            Text(text = stringResource(placeholder))
        },
        modifier = modifier, keyboardActions = KeyboardActions(onAny = {
            keyboardActionCallback()
        }),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
        ),
        singleLine = true
    )
}
