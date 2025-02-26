package de.thomaskuenneth.cmpunitconverter.viewmodels

import androidx.lifecycle.ViewModel
import de.thomaskuenneth.cmpunitconverter.DistanceUnit
import de.thomaskuenneth.cmpunitconverter.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class DistancesViewModel(private val repository: Repository) : ViewModel() {

    private val _unit: MutableStateFlow<DistanceUnit> = MutableStateFlow(
        repository.getDistanceUnit()
    )

    val unit: StateFlow<DistanceUnit>
        get() = _unit

    fun setUnit(value: DistanceUnit) {
        _unit.update { value }
        repository.setDistanceUnit(value)
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
                if (!value.isNaN()) if (_unit.value == DistanceUnit.meters) value * 0.00062137F
                else value / 0.00062137F
                else Float.NaN
            }
        }
    }
}
