package de.thomaskuenneth.cmpunitconverter

import de.thomaskuenneth.cmpunitconverter.shared.generated.resources.*
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

enum class AppDestinations(
    val labelRes: StringResource,
    val iconRes: DrawableResource,
    val contentDescription: StringResource = labelRes,
) {
    Temperature(
        labelRes = Res.string.temperature,
        iconRes = AppIcons.Thermostat
    ),
    Distance(
        labelRes = Res.string.distance,
        iconRes = AppIcons.SquareFoot
    ),
}
