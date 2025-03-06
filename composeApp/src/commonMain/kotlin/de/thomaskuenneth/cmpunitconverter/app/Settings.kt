package de.thomaskuenneth.cmpunitconverter.app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.thomaskuenneth.cmpunitconverter.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun Settings(viewModel: AppViewModel = koinViewModel(), modifier: Modifier = Modifier) {
    val uiState by viewModel.uiState.collectAsState()
    val onClick: (ColorSchemeMode) -> Unit = { viewModel.setColorSchemeMode(it) }
    Column(
        modifier = modifier.padding(8.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(Res.string.color_scheme),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleMedium,
        )
        SingleChoiceSegmentedButtonRow {
            SegmentedColorSchemeButton(
                selected = uiState.colorSchemeMode == ColorSchemeMode.System, unit = ColorSchemeMode.System, onClick = onClick
            )
            SegmentedColorSchemeButton(
                selected = uiState.colorSchemeMode == ColorSchemeMode.Light, unit = ColorSchemeMode.Light, onClick = onClick
            )
            SegmentedColorSchemeButton(
                selected = uiState.colorSchemeMode == ColorSchemeMode.Dark, unit = ColorSchemeMode.Dark, onClick = onClick
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsBottomSheet(visible: Boolean, closeSheet: () -> Unit) {
    if (visible) {
        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        ModalBottomSheet(sheetState = sheetState, onDismissRequest = { closeSheet() }) {
            Settings(modifier = Modifier.align(Alignment.CenterHorizontally))
        }
    }
}

@Composable
fun SingleChoiceSegmentedButtonRowScope.SegmentedColorSchemeButton(
    selected: Boolean, unit: ColorSchemeMode, onClick: (ColorSchemeMode) -> Unit
) {
    SegmentedButton(
        selected = selected, onClick = { onClick(unit) }, shape = SegmentedButtonDefaults.itemShape(
            index = unit.ordinal, count = ColorSchemeMode.entries.size
        ), label = {
            Text(
                text = stringResource(
                    when (unit) {
                        ColorSchemeMode.System -> Res.string.system
                        ColorSchemeMode.Light -> Res.string.light
                        ColorSchemeMode.Dark -> Res.string.dark
                    }
                )
            )
        })
}

