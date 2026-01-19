package de.thomaskuenneth.cmpunitconverter.app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.SingleChoiceSegmentedButtonRowScope
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.thomaskuenneth.cmpunitconverter.CheckboxWithLabel
import de.thomaskuenneth.cmpunitconverter.shared.generated.resources.Res
import de.thomaskuenneth.cmpunitconverter.shared.generated.resources.color_scheme
import de.thomaskuenneth.cmpunitconverter.shared.generated.resources.dark
import de.thomaskuenneth.cmpunitconverter.shared.generated.resources.light
import de.thomaskuenneth.cmpunitconverter.shared.generated.resources.show_extended_about_dialog
import de.thomaskuenneth.cmpunitconverter.shared.generated.resources.system
import de.thomaskuenneth.cmpunitconverter.shouldShowExtendedAboutDialogCheckbox
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun Settings(viewModel: AppViewModel = koinViewModel(), modifier: Modifier = Modifier) {
    val uiState by viewModel.uiState.collectAsState()
    val onClick: (ColorSchemeMode) -> Unit = { viewModel.setColorSchemeMode(it) }
    Column(
        modifier = modifier
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = stringResource(Res.string.color_scheme),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleMedium,
            )
            SingleChoiceSegmentedButtonRow {
                SegmentedColorSchemeButton(
                    selected = uiState.colorSchemeMode == ColorSchemeMode.System,
                    unit = ColorSchemeMode.System,
                    onClick = onClick
                )
                SegmentedColorSchemeButton(
                    selected = uiState.colorSchemeMode == ColorSchemeMode.Light,
                    unit = ColorSchemeMode.Light,
                    onClick = onClick
                )
                SegmentedColorSchemeButton(
                    selected = uiState.colorSchemeMode == ColorSchemeMode.Dark,
                    unit = ColorSchemeMode.Dark,
                    onClick = onClick
                )
            }
        }
        if (shouldShowExtendedAboutDialogCheckbox()) {
            CheckboxWithLabel(
                label = stringResource(Res.string.show_extended_about_dialog),
                checked = uiState.showExtendedAboutDialog
            ) { checked -> viewModel.setShowExtendedAboutDialog(checked) }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsBottomSheet(visible: Boolean, closeSheet: () -> Unit) {
    if (visible) {
        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
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
