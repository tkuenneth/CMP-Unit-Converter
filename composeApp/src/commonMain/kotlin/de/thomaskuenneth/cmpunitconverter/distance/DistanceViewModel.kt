package de.thomaskuenneth.cmpunitconverter.distance

import de.thomaskuenneth.cmpunitconverter.AbstractViewModel
import de.thomaskuenneth.cmpunitconverter.DistanceSupportingPaneUseCase
import de.thomaskuenneth.cmpunitconverter.UnitsAndScales

class DistanceViewModel(
    repository: DistanceRepository, val supportingPaneUseCase: DistanceSupportingPaneUseCase
) : AbstractViewModel(repository = repository, supportingPaneUseCase = supportingPaneUseCase) {

    fun convert() {
        convertSourceUnitToDestinationUnit(sourceUnitToBaseUnit = { sourceValue, unit ->
            when (unit) {
                UnitsAndScales.Meter -> sourceValue
                UnitsAndScales.Mile -> sourceValue.convertMileToMeter()
                else -> Float.NaN
            }
        }, baseUnitToDestinationUnit = { valueInMeter, destinationUnit ->
            when (destinationUnit) {
                UnitsAndScales.Meter -> valueInMeter
                UnitsAndScales.Mile -> valueInMeter.convertMeterToMile()
                else -> Float.NaN
            }
        })
    }

    private fun Float.convertMileToMeter() = this / 0.00062137F

    private fun Float.convertMeterToMile() = this * 0.00062137F
}
