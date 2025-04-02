package de.thomaskuenneth.cmpunitconverter.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ThreePaneScaffoldScope
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.thomaskuenneth.cmpunitconverter.AbstractSupportingPaneUseCase
import de.thomaskuenneth.cmpunitconverter.UnitsAndScales
import de.thomaskuenneth.cmpunitconverter.composeapp.generated.resources.*
import de.thomaskuenneth.cmpunitconverter.convertToLocalizedDate
import de.thomaskuenneth.cmpunitconverter.convertToLocalizedTime
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun ThreePaneScaffoldScope.SupportingPane(
    uiState: AbstractSupportingPaneUseCase.UiState,
    showUnits: Boolean,
    readMoreOnWikipedia: (UnitsAndScales) -> Unit = {},
    clearConversionsHistory: () -> Unit = {}
) {
    var current by remember(uiState.current) { mutableStateOf(uiState.current) }
    AnimatedPane {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.background(MaterialTheme.colorScheme.background).padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            if (showUnits) {
                UnitsAndScalesButtonRow(
                    entries = uiState.entries,
                    selected = current,
                    onClick = { current = it })
            }
            Text(
                text = stringResource(current.info, stringResource(current.unit)),
                color = MaterialTheme.colorScheme.onBackground
            )
            OutlinedButton(onClick = { readMoreOnWikipedia(current) }) {
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
                OutlinedIconButtonWithTooltip(
                    icon = Icons.Default.Clear,
                    contentDescription = stringResource(Res.string.clear_conversions_history),
                    onClick = clearConversionsHistory,
                    enabled = uiState.elements.isNotEmpty()
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                if (uiState.elements.isNotEmpty()) {
                    uiState.elements.forEach { element ->
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
                            with(element.timestamp) {
                                Text(
                                    text = stringResource(
                                        Res.string.converted_on,
                                        convertToLocalizedDate(),
                                        convertToLocalizedTime()
                                    ),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }
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
