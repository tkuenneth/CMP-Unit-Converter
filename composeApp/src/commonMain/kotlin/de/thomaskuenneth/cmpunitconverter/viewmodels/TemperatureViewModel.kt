package de.thomaskuenneth.cmpunitconverter.viewmodels

import androidx.lifecycle.ViewModel
import de.thomaskuenneth.cmpunitconverter.Repository
import de.thomaskuenneth.cmpunitconverter.TemperatureUnit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TemperatureViewModel(private val repository: Repository) : ViewModel() {

    private val _unit: MutableStateFlow<TemperatureUnit> = MutableStateFlow(
        repository.getTemperatureUnit()
    )

    val unit: StateFlow<TemperatureUnit>
        get() = _unit

    fun setUnit(value: TemperatureUnit) {
        _unit.update { value }
        repository.setTemperatureUnit(value)
    }

    private val _temperature: MutableStateFlow<String> = MutableStateFlow(
        repository.getTemperature()
    )

    val temperature: StateFlow<String>
        get() = _temperature

    fun getTemperatureAsFloat(): Float = _temperature.value.let {
        return try {
            it.toFloat()
        } catch (e: NumberFormatException) {
            Float.NaN
        }
    }

    fun setTemperature(value: String) {
        _temperature.update { value }
        repository.setTemperature(value)
    }

    private val _convertedTemperature: MutableStateFlow<Float> = MutableStateFlow(Float.NaN)
    val convertedTemperature: StateFlow<Float> = _convertedTemperature.asStateFlow()

    fun convert() {
        getTemperatureAsFloat().let { value ->
            _convertedTemperature.update {
                if (!value.isNaN()) {
                    if (_unit.value == TemperatureUnit.celsius) (value * 1.8F) + 32F
                    else (value - 32F) / 1.8F
                } else Float.NaN
            }
        }
    }
}
