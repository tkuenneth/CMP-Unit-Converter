package de.thomaskuenneth.cmpunitconverter.distance

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class DistanceViewModel(private val repository: DistanceRepository) : ViewModel() {

    private val _unit: MutableStateFlow<DistanceUnit> = MutableStateFlow(
        repository.getDistanceSourceUnit()
    )

    val unit: StateFlow<DistanceUnit>
        get() = _unit

    fun setUnit(value: DistanceUnit) {
        _unit.update { value }
        repository.setDistanceSourceUnit(value)
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

    val convertedDistance: StateFlow<Float>
        get() = _convertedDistance

    fun convert() {
        getDistanceAsFloat().let { value ->
            _convertedDistance.update {
                if (!value.isNaN()) if (_unit.value == DistanceUnit.Meter) value * 0.00062137F
                else value / 0.00062137F
                else Float.NaN
            }
        }
    }
}
