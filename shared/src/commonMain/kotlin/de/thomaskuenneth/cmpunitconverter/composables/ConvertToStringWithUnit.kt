package de.thomaskuenneth.cmpunitconverter.composables

import androidx.compose.runtime.Composable
import de.thomaskuenneth.cmpunitconverter.convertToLocalizedString
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun Float.convertToStringWithUnit(unit: StringResource): String = if (isNaN()) "" else "${convertToLocalizedString()}\u00a0${
    stringResource(unit)
}"
