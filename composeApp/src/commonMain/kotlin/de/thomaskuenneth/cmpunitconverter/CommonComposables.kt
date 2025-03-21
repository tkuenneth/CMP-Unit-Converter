package de.thomaskuenneth.cmpunitconverter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
    info: StringResource = Res.string.learn_more,
    unit: StringResource = Res.string.learn_more,
    elements: List<HistoryEntity> = emptyList(),
    readMoreOnWikipedia: () -> Unit = {}
) {
    AnimatedPane {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.background(MaterialTheme.colorScheme.background).padding(all = 24.dp)
        ) {
            Text(
                text = stringResource(info, stringResource(unit)),
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextButton(onClick = readMoreOnWikipedia) {
                Text(
                    text = stringResource(Res.string.read_more_on_wikipedia),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth().weight(1F)
            ) {
                items(elements) { element ->
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
                                    .toLocalDateTime(TimeZone.currentSystemDefault()).format(LocalDateTime.Format {
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
            }
        }
    }
}

@Composable
fun Float.convertToStringWithUnit(unit: StringResource): String = if (isNaN()) "" else "${convertToLocalizedString()} ${
    stringResource(unit)
}"
