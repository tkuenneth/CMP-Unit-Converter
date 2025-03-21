package de.thomaskuenneth.cmpunitconverter

import de.thomaskuenneth.cmpunitconverter.composeapp.generated.resources.*
import org.jetbrains.compose.resources.StringResource

enum class UnitsAndScales(val unit: StringResource, val info: StringResource, val url: String) {
    Undefined(
      unit = Res.string.undefined, info = Res.string.undefined, url = ""
    ),

    // Temperature
    Celsius(
        unit = Res.string.celsius, info = Res.string.celsius_info, url = "https://en.wikipedia.org/wiki/Celsius"
    ),
    Fahrenheit(
        unit = Res.string.fahrenheit, info = Res.string.fahrenheit_info, url = "https://en.wikipedia.org/wiki/Fahrenheit"
    ),

    // Distance
    Meter(
        unit = Res.string.meter, info = Res.string.meter_info, url = "https://en.wikipedia.org/wiki/Metre"
    ),
    Mile(
        unit = Res.string.mile, info = Res.string.mile_info, url = "https://en.wikipedia.org/wiki/Mile"
    ),
}

val TemperatureUnit = listOf(UnitsAndScales.Celsius, UnitsAndScales.Fahrenheit)

val DistanceUnit = listOf(UnitsAndScales.Meter, UnitsAndScales.Mile)
