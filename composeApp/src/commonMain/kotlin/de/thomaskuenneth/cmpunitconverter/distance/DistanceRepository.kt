package de.thomaskuenneth.cmpunitconverter.distance

enum class DistanceUnit {
    Meter, Mile
}

class DistanceRepository {

    fun getDistanceSourceUnit(): DistanceUnit = DistanceUnit.Meter

    fun setDistanceSourceUnit(value: DistanceUnit) {
    }

    fun getDistanceDestinationUnit(): DistanceUnit = DistanceUnit.Meter

    fun setDistanceDestinationUnit(value: DistanceUnit) {
    }

    fun getDistance(): String = ""

    fun setDistance(value: String) {
    }
}
