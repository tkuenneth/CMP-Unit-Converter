package de.thomaskuenneth.cmpunitconverter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ThreePaneScaffoldScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import de.thomaskuenneth.cmpunitconverter.composeapp.generated.resources.*
import kotlinx.datetime.*
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource


@Composable
fun NumberTextField(
    value: TextFieldValue,
    placeholder: StringResource,
    modifier: Modifier = Modifier,
    keyboardActionCallback: () -> Unit,
    onValueChange: (TextFieldValue) -> Unit
) {
    TextField(
        value = value,
        onValueChange = { textFieldValue ->
            onValueChange(
                textFieldValue.copy(
                    text = textFieldValue.text.filter { it.isDigit() || it == '.' || it == ',' }
                )
            )
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

@Composable
fun Result(value: Float, unit: StringResource) {
    val result = value.convertToStringWithUnit(unit)
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

@Composable
fun SingleChoiceSegmentedButtonRowScope.SegmentedUnitsAndScalesButton(
    selected: Boolean, unit: UnitsAndScales, entries: List<UnitsAndScales>, onClick: (UnitsAndScales) -> Unit
) {
    SegmentedButton(
        selected = selected, onClick = { onClick(unit) }, shape = SegmentedButtonDefaults.itemShape(
            index = entries.indexOf(unit), count = entries.size
        ), label = {
            Text(
                text = stringResource(unit.unit)
            )
        })
}

@Composable
fun UnitsAndScalesButtonRow(
    entries: List<UnitsAndScales>,
    selected: UnitsAndScales,
    label: StringResource,
    modifier: Modifier = Modifier,
    onClick: (UnitsAndScales) -> Unit
) {
    Row {
        Text(
            modifier = Modifier.alignByBaseline().width(80.dp),
            text = stringResource(label),
            textAlign = TextAlign.Start
        )
        SingleChoiceSegmentedButtonRow(modifier = modifier.alignByBaseline()) {
            entries.forEach { unit ->
                SegmentedUnitsAndScalesButton(
                    selected = selected == unit, unit = unit, entries = entries, onClick = onClick
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun ThreePaneScaffoldScope.SupportingPane(
    uiState: SupportingPaneUseCase.UiState,
    readMoreOnWikipedia: () -> Unit = {},
    clearConversionsHistory: () -> Unit = {}
) {
    with(uiState) {
        AnimatedPane {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.background(MaterialTheme.colorScheme.background).padding(horizontal = 24.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(current.info, stringResource(current.unit)),
                    color = MaterialTheme.colorScheme.onBackground
                )
                OutlinedButton(onClick = readMoreOnWikipedia) {
                    Text(
                        text = stringResource(Res.string.read_more_on_wikipedia)
                    )
                }
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = stringResource(Res.string.conversions_history),
                        modifier = Modifier.weight(1F),
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.headlineSmall
                    )
                    OutlinedIconButton(onClick = clearConversionsHistory, enabled = elements.isNotEmpty()) {
                        Icon(
                            Icons.Default.Clear,
                            contentDescription = stringResource(Res.string.clear_history)
                        )
                    }
                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (elements.isNotEmpty()) {
                        elements.forEach { element ->
                            Column {
                                Text(
                                    text = stringResource(
                                        Res.string.conversion_summary,
                                        element.sourceValue.convertToStringWithUnit(element.sourceUnit.unit),
                                        element.destinationValue.convertToStringWithUnit(
                                            element.destinationUnit.unit
                                        )
                                    ),
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                                Text(
                                    text =
                                        Instant.fromEpochMilliseconds(element.timestamp)
                                            .toLocalDateTime(TimeZone.currentSystemDefault())
                                            .format(LocalDateTime.Format {
                                                chars("Converted on ")
                                                year()
                                                chars("-")
                                                monthNumber()
                                                chars("-")
                                                dayOfMonth()
                                                chars(" at ")
                                                hour()
                                                chars(":")
                                                minute()
                                            }),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }
                        }
                    } else {
                        Text(
                            text = stringResource(Res.string.no_conversions),
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun Float.convertToStringWithUnit(unit: StringResource): String = if (isNaN()) "" else "${convertToLocalizedString()} ${
    stringResource(unit)
}"
