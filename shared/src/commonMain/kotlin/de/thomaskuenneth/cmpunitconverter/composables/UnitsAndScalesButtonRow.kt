package de.thomaskuenneth.cmpunitconverter.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import de.thomaskuenneth.cmpunitconverter.AppIcons
import org.jetbrains.compose.resources.vectorResource
import androidx.compose.material3.ButtonGroup
import androidx.compose.material3.ButtonGroupDefaults
import androidx.compose.material3.ButtonGroupMenuState
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import de.thomaskuenneth.cmpunitconverter.UnitsAndScales
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun UnitsAndScalesButtonRow(
    entries: List<UnitsAndScales>,
    selected: UnitsAndScales,
    modifier: Modifier = Modifier,
    label: StringResource? = null,
    onClick: (UnitsAndScales) -> Unit
) {
    Row {
        label?.run {
            Text(
                modifier = Modifier.alignByBaseline().width(80.dp),
                text = stringResource(this),
                textAlign = TextAlign.Start
            )
        }
        val overflowMenuButton: @Composable (ButtonGroupMenuState) -> Unit = { menuState ->
            OutlinedIconButton(
                onClick = {
                    if (menuState.isShowing) {
                        menuState.dismiss()
                    } else {
                        menuState.show()
                    }
                }
            ) {
                Icon(
                    imageVector = vectorResource(AppIcons.MoreVert),
                    contentDescription = null
                )
            }
        }
        ButtonGroup(
            overflowIndicator = overflowMenuButton,
            modifier = modifier.alignByBaseline(),
            horizontalArrangement = Arrangement.spacedBy(ButtonGroupDefaults.ConnectedSpaceBetween)
        ) {
            entries.forEachIndexed { index, unit ->
//                toggleableItem(
//                    onCheckedChange = {
//                        onClick(entries[index])
//                    },
//                    checked = entries[index] == selected,
//                    label = entries[index].name
//                )
                customItem(
                    buttonGroupContent = {
                        ToggleButton(
                            checked = selected == unit,
                            onCheckedChange = { onClick(unit) },
                            modifier = Modifier.semantics { role = Role.RadioButton },
                            shapes =
                                when (index) {
                                    0 -> ButtonGroupDefaults.connectedLeadingButtonShapes()
                                    entries.lastIndex -> ButtonGroupDefaults.connectedTrailingButtonShapes()
                                    else -> ButtonGroupDefaults.connectedMiddleButtonShapes()
                                }
                        ) {
                            Text(unit.name)
                        }
                    },
                    menuContent = { menuContent ->
                        DropdownMenuItem(
                            text = { Text(unit.name) },
                            onClick = {
                                onClick(unit)
                                menuContent.dismiss()
                            },
                        )
                    }
                )
            }
        }
    }
}
