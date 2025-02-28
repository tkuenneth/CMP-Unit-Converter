package de.thomaskuenneth.cmpunitconverter.distance

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DistanceViewModel(private val repository: DistanceRepository) : ViewModel() {

    private val _sourceUnit: MutableStateFlow<DistanceUnit> = MutableStateFlow(
        repository.getDistanceSourceUnit()
    )

    val sourceUnit: StateFlow<DistanceUnit>
        get() = _sourceUnit

    fun setSourceUnit(value: DistanceUnit) {
        _sourceUnit.update { value }
        repository.setDistanceSourceUnit(value)
    }

    private val _destinationUnit: MutableStateFlow<DistanceUnit> = MutableStateFlow(
        repository.getDistanceDestinationUnit()
    )

    val destinationUnit: StateFlow<DistanceUnit>
        get() = _destinationUnit

    fun setDestinationUnit(value: DistanceUnit) {
        _destinationUnit.update { value }
        repository.setDistanceDestinationUnit(value)
    }

    private val _distance: MutableStateFlow<String> = MutableStateFlow(
        repository.getDistance()
    )

    val distance: StateFlow<String>
        get() = _distance

    fun getDistanceAsFloat(): Float = _distance.value.let {
        return try {
            it.toFloat()
        } catch (e: NumberFormatException) {
            Float.NaN
        }
    }

    fun setDistance(value: String) {
        _distance.update { value }
        repository.setDistance(value)
    }

    private val _convertedDistance: MutableStateFlow<Float> = MutableStateFlow(Float.NaN)
    val convertedDistance: StateFlow<Float> = _convertedDistance.asStateFlow()

    fun convert() {
        getDistanceAsFloat().let { value ->
            val valueInMeter = when (_sourceUnit.value) {
                DistanceUnit.Meter -> value
                DistanceUnit.Mile -> value.convertMileToMeter()
            }
            _convertedDistance.update {
                when (_destinationUnit.value) {
                    DistanceUnit.Meter -> valueInMeter
                    DistanceUnit.Mile -> valueInMeter.convertMeterToMile()
                }
            }
        }
    }

    private fun Float.convertMileToMeter() = this / 0.00062137F

    private fun Float.convertMeterToMile() = this * 0.00062137F
}
