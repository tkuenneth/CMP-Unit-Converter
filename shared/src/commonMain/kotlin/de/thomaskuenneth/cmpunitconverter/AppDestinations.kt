package de.thomaskuenneth.cmpunitconverter

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.SquareFoot
import androidx.compose.material.icons.twotone.Thermostat
import androidx.compose.ui.graphics.vector.ImageVector
import de.thomaskuenneth.cmpunitconverter.shared.generated.resources.*
import org.jetbrains.compose.resources.StringResource

enum class AppDestinations(
    val labelRes: StringResource,
    val icon: ImageVector,
    val contentDescription: StringResource = labelRes,
) {
    Temperature(
        labelRes = Res.string.temperature,
        icon = Icons.TwoTone.Thermostat
    ),
    Distance(
        labelRes = Res.string.distance,
        icon = Icons.TwoTone.SquareFoot
    ),
}
