package de.thomaskuenneth.cmpunitconverter

import de.thomaskuenneth.cmpunitconverter.shared.generated.resources.Res
import de.thomaskuenneth.cmpunitconverter.shared.generated.resources.ic_arrow_back
import de.thomaskuenneth.cmpunitconverter.shared.generated.resources.ic_clear
import de.thomaskuenneth.cmpunitconverter.shared.generated.resources.ic_info
import de.thomaskuenneth.cmpunitconverter.shared.generated.resources.ic_more_vert
import de.thomaskuenneth.cmpunitconverter.shared.generated.resources.ic_settings
import de.thomaskuenneth.cmpunitconverter.shared.generated.resources.ic_square_foot
import de.thomaskuenneth.cmpunitconverter.shared.generated.resources.ic_thermostat
import org.jetbrains.compose.resources.DrawableResource

/**
 * Drawable resources for app icons. Use with [org.jetbrains.compose.resources.vectorResource]
 * to obtain [androidx.compose.ui.graphics.vector.ImageVector] in composables.
 * Icons are from the [Material Icons Library](https://fonts.google.com/icons), downloaded as needed.
 */
object AppIcons {
    val Thermostat: DrawableResource get() = Res.drawable.ic_thermostat
    val SquareFoot: DrawableResource get() = Res.drawable.ic_square_foot
    val MoreVert: DrawableResource get() = Res.drawable.ic_more_vert
    val Clear: DrawableResource get() = Res.drawable.ic_clear
    val ArrowBack: DrawableResource get() = Res.drawable.ic_arrow_back
    val Settings: DrawableResource get() = Res.drawable.ic_settings
    val Info: DrawableResource get() = Res.drawable.ic_info
}
