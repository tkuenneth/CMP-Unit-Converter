package de.thomaskuenneth.cmpunitconverter

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.SquareFoot
import androidx.compose.material.icons.twotone.Thermostat
import androidx.compose.ui.graphics.vector.ImageVector
import cmpunitconverter.composeapp.generated.resources.Res
import cmpunitconverter.composeapp.generated.resources.tab_distance
import cmpunitconverter.composeapp.generated.resources.tab_temperature
import org.jetbrains.compose.resources.StringResource

enum class AppDestinations(
    val labelRes: StringResource,
    val icon: ImageVector,
    val contentDescription: StringResource = labelRes,
) {
    Temperature(
        labelRes = Res.string.tab_temperature,
        icon = Icons.TwoTone.Thermostat
    ),
    Distance(
        labelRes = Res.string.tab_distance,
        icon = Icons.TwoTone.SquareFoot
    ),
}
