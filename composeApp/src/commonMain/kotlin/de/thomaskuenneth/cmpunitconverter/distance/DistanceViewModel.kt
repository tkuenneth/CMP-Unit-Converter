package de.thomaskuenneth.cmpunitconverter.distance

import de.thomaskuenneth.cmpunitconverter.AbstractConverterViewModel
import de.thomaskuenneth.cmpunitconverter.DistanceUnit
import de.thomaskuenneth.cmpunitconverter.UnitsAndScales
import de.thomaskuenneth.cmpunitconverter.composeapp.generated.resources.Res
import de.thomaskuenneth.cmpunitconverter.composeapp.generated.resources.placeholder_distance

class DistanceViewModel(
    repository: DistanceRepository, supportingPaneUseCase: DistanceSupportingPaneUseCase
) : AbstractConverterViewModel(
    entries = DistanceUnit,
    placeholder = Res.string.placeholder_distance,
    repository = repository,
    supportingPaneUseCase = supportingPaneUseCase
) {

    override fun convert() {
        convertSourceUnitToDestinationUnit(sourceUnitToBaseUnit = { sourceValue, unit ->
            when (unit) {
                UnitsAndScales.Meter -> sourceValue
                UnitsAndScales.Mile -> sourceValue.convertMileToMeter()
                else -> Float.NaN
            }
        }, baseUnitToDestinationUnit = { destinationValue, destinationUnit ->
            when (destinationUnit) {
                UnitsAndScales.Meter -> destinationValue
                UnitsAndScales.Mile -> destinationValue.convertMeterToMile()
                else -> Float.NaN
            }
        })
    }

    private fun Float.convertMileToMeter() = this / 0.00062137F

    private fun Float.convertMeterToMile() = this * 0.00062137F
}
