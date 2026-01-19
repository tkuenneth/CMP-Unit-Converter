package de.thomaskuenneth.cmpunitconverter.distance

import de.thomaskuenneth.cmpunitconverter.DistanceUnit
import de.thomaskuenneth.cmpunitconverter.AbstractSupportingPaneUseCase
import de.thomaskuenneth.cmpunitconverter.UnitsAndScales

class DistanceSupportingPaneUseCase :
    AbstractSupportingPaneUseCase(entries = DistanceUnit, initial = UnitsAndScales.Meter)
