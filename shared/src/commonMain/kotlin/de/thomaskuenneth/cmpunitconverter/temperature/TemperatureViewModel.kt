package de.thomaskuenneth.cmpunitconverter.temperature

import de.thomaskuenneth.cmpunitconverter.AbstractConverterViewModel
import de.thomaskuenneth.cmpunitconverter.TemperatureUnit
import de.thomaskuenneth.cmpunitconverter.UnitsAndScales
import de.thomaskuenneth.cmpunitconverter.shared.generated.resources.Res
import de.thomaskuenneth.cmpunitconverter.shared.generated.resources.placeholder_temperature

class TemperatureViewModel(
    repository: TemperatureRepository, supportingPaneUseCase: TemperatureSupportingPaneUseCase
) : AbstractConverterViewModel(
    entries = TemperatureUnit,
    placeholder = Res.string.placeholder_temperature,
    repository = repository,
    supportingPaneUseCase = supportingPaneUseCase
) {

    override fun convert() {
        convertSourceUnitToDestinationUnit(sourceUnitToBaseUnit = { sourceValue, unit ->
            when (unit) {
                UnitsAndScales.Celsius -> sourceValue
                UnitsAndScales.Fahrenheit -> sourceValue.convertFahrenheitToCelsius()
                UnitsAndScales.Kelvin -> sourceValue.convertKelvinToCelsius()
                else -> Float.NaN
            }
        }, baseUnitToDestinationUnit = { destinationValue, destinationUnit ->
            when (destinationUnit) {
                UnitsAndScales.Celsius -> destinationValue
                UnitsAndScales.Fahrenheit -> destinationValue.convertCelsiusToFahrenheit()
                UnitsAndScales.Kelvin -> destinationValue.convertCelsiusToKelvin()
                else -> Float.NaN
            }
        })
    }

    private fun Float.convertFahrenheitToCelsius() = (this - 32F) / 1.8F

    private fun Float.convertCelsiusToFahrenheit() = (this * 1.8F) + 32F

    private fun Float.convertKelvinToCelsius() = this - 273.15F

    private fun Float.convertCelsiusToKelvin() = this + 273.15f
}
