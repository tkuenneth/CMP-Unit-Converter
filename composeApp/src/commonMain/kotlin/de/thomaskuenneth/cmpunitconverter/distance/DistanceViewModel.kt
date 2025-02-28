package de.thomaskuenneth.cmpunitconverter.distance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class UiState(
    val sourceUnit: DistanceUnit, val destinationUnit: DistanceUnit, val distance: String
)

class DistanceViewModel(private val repository: DistanceRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(
        UiState(
            sourceUnit = DistanceUnit.Meter, destinationUnit = DistanceUnit.Mile, distance = ""
        )
    )
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            combineTransform(
                repository.sourceUnit, repository.destinationUnit, repository.temperature
            ) { sourceUnit, destinationUnit, temperature ->
                emit(Triple(sourceUnit, destinationUnit, temperature))
            }.collect { (sourceUnit, destinationUnit, temperature) ->
                _uiState.update { current ->
                    current.copy(
                        sourceUnit = sourceUnit, destinationUnit = destinationUnit, distance = temperature
                    )
                }
            }
        }
    }

    fun setSourceUnit(value: DistanceUnit) {
        _uiState.update { it.copy(sourceUnit = value) }
        viewModelScope.launch {
            repository.setDistanceSourceUnit(value)
        }
    }

    fun setDestinationUnit(value: DistanceUnit) {
        _uiState.update { it.copy(destinationUnit = value) }
        viewModelScope.launch {
            repository.setDistanceDestinationUnit(value)
        }
    }

    fun getDistanceAsFloat(): Float = uiState.value.distance.let {
        return try {
            it.toFloat()
        } catch (e: NumberFormatException) {
            Float.NaN
        }
    }

    fun setDistance(value: String) {
        _uiState.update { it.copy(distance = value) }
        viewModelScope.launch {
            repository.setDistance(value)
        }
    }

    private val _convertedDistance: MutableStateFlow<Float> = MutableStateFlow(Float.NaN)
    val convertedDistance: StateFlow<Float> = _convertedDistance.asStateFlow()

    fun convert() {
        getDistanceAsFloat().let { value ->
            val valueInCelsius = when (uiState.value.sourceUnit) {
                DistanceUnit.Meter -> value
                DistanceUnit.Mile -> value.convertMileToMeter()
            }
            _convertedDistance.update {
                when (uiState.value.destinationUnit) {
                    DistanceUnit.Meter -> valueInCelsius
                    DistanceUnit.Mile -> valueInCelsius.convertMeterToMile()
                }
            }
        }
    }

    private fun Float.convertMileToMeter() = this / 0.00062137F

    private fun Float.convertMeterToMile() = this * 0.00062137F
}
