package de.thomaskuenneth.cmpunitconverter.temperature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.thomaskuenneth.cmpunitconverter.convertToString
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class UiState(
    val sourceUnit: TemperatureUnit,
    val destinationUnit: TemperatureUnit,
    val temperatureAsString: String
)

class TemperatureViewModel(private val repository: TemperatureRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(
        UiState(
            sourceUnit = TemperatureUnit.Celsius,
            destinationUnit = TemperatureUnit.Fahrenheit,
            temperatureAsString = Float.NaN.convertToString()
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
                        sourceUnit = sourceUnit,
                        destinationUnit = destinationUnit,
                        temperatureAsString = temperature.convertToString()
                    )
                }
            }
        }
    }

    fun setSourceUnit(value: TemperatureUnit) {
        _uiState.update { it.copy(sourceUnit = value) }
        viewModelScope.launch {
            repository.setTemperatureSourceUnit(value)
        }
    }

    fun setDestinationUnit(value: TemperatureUnit) {
        _uiState.update { it.copy(destinationUnit = value) }
        viewModelScope.launch {
            repository.setTemperatureDestinationUnit(value)
        }
    }

    fun getTemperatureAsFloat(): Float = uiState.value.temperatureAsString.let {
        return try {
            it.toFloat()
        } catch (e: NumberFormatException) {
            Float.NaN
        }
    }

    fun setTemperatureAsString(value: String) {
        _uiState.update { it.copy(temperatureAsString = value) }
        viewModelScope.launch {
            repository.setTemperature(getTemperatureAsFloat())
        }
    }

    private val _convertedTemperature: MutableStateFlow<Float> = MutableStateFlow(Float.NaN)
    val convertedTemperature: StateFlow<Float> = _convertedTemperature.asStateFlow()

    fun convert() {
        getTemperatureAsFloat().let { value ->
            val valueInCelsius = when (uiState.value.sourceUnit) {
                TemperatureUnit.Celsius -> value
                TemperatureUnit.Fahrenheit -> value.convertFahrenheitToCelsius()
            }
            _convertedTemperature.update {
                when (uiState.value.destinationUnit) {
                    TemperatureUnit.Celsius -> valueInCelsius
                    TemperatureUnit.Fahrenheit -> valueInCelsius.convertCelsiusToFahrenheit()
                }
            }
        }
    }

    private fun Float.convertFahrenheitToCelsius() = (this - 32F) / 1.8F

    private fun Float.convertCelsiusToFahrenheit() = (this * 1.8F) + 32F
}
