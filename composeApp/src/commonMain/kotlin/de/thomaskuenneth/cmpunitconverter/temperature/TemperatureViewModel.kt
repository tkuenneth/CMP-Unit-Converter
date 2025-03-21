package de.thomaskuenneth.cmpunitconverter.temperature

import de.thomaskuenneth.cmpunitconverter.AbstractViewModel
import de.thomaskuenneth.cmpunitconverter.TemperatureSupportingPaneUseCase
import de.thomaskuenneth.cmpunitconverter.UnitsAndScales

class TemperatureViewModel(
    repository: TemperatureRepository, val supportingPaneUseCase: TemperatureSupportingPaneUseCase
) : AbstractViewModel(repository = repository, supportingPaneUseCase = supportingPaneUseCase) {

    override fun convert() {
        convertSourceUnitToDestinationUnit(sourceUnitToBaseUnit = { sourceValue, unit ->
            when (unit) {
                UnitsAndScales.Celsius -> sourceValue
                UnitsAndScales.Fahrenheit -> sourceValue.convertFahrenheitToCelsius()
                else -> Float.NaN
            }
        }, baseUnitToDestinationUnit = { destinationValue, destinationUnit ->
            when (destinationUnit) {
                UnitsAndScales.Celsius -> destinationValue
                UnitsAndScales.Fahrenheit -> destinationValue.convertCelsiusToFahrenheit()
                else -> Float.NaN
            }
        })
    }

    private fun Float.convertFahrenheitToCelsius() = (this - 32F) / 1.8F

    private fun Float.convertCelsiusToFahrenheit() = (this * 1.8F) + 32F
}
