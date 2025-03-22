package de.thomaskuenneth.cmpunitconverter.temperature

import de.thomaskuenneth.cmpunitconverter.AbstractSupportingPaneUseCase
import de.thomaskuenneth.cmpunitconverter.TemperatureUnit
import de.thomaskuenneth.cmpunitconverter.UnitsAndScales

class TemperatureSupportingPaneUseCase :
    AbstractSupportingPaneUseCase(entries = TemperatureUnit, initial = UnitsAndScales.Celsius)
