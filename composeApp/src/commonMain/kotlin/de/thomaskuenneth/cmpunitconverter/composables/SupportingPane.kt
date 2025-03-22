package de.thomaskuenneth.cmpunitconverter.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.unit.dp
import de.thomaskuenneth.cmpunitconverter.AbstractSupportingPaneUseCase
import de.thomaskuenneth.cmpunitconverter.composeapp.generated.resources.*
import kotlinx.datetime.*
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun ThreePaneScaffoldScope.SupportingPane(
    uiState: AbstractSupportingPaneUseCase.UiState,
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
