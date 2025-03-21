package de.thomaskuenneth.cmpunitconverter.temperature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.thomaskuenneth.cmpunitconverter.TemperatureSupportingPaneUseCase
import de.thomaskuenneth.cmpunitconverter.UnitsAndScales
import de.thomaskuenneth.cmpunitconverter.convertLocalizedStringToFloat
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class UiState(
    val sourceUnit: UnitsAndScales,
    val destinationUnit: UnitsAndScales,
    val temperature: Float
)

class TemperatureViewModel(
    private val repository: TemperatureRepository, val supportingPaneUseCase: TemperatureSupportingPaneUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(
        UiState(
            sourceUnit = UnitsAndScales.Celsius,
            destinationUnit = UnitsAndScales.Fahrenheit,
            temperature = Float.NaN
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
                        temperature = temperature
                    )
                }
                updateSupportingPaneState(sourceUnit)
            }
        }
    }

    fun setSourceUnit(value: UnitsAndScales) {
        _uiState.update { it.copy(sourceUnit = value) }
        updateSupportingPaneState(value)
        viewModelScope.launch {
            repository.setTemperatureSourceUnit(value)
        }
    }

    fun setDestinationUnit(value: UnitsAndScales) {
        _uiState.update { it.copy(destinationUnit = value) }
        updateSupportingPaneState(value)
        viewModelScope.launch {
            repository.setTemperatureDestinationUnit(value)
        }
    }

    fun getTemperatureAsFloat(): Float = uiState.value.temperature

    fun setTemperature(value: String) {
        viewModelScope.launch {
            with(value.convertLocalizedStringToFloat()) {
                if (!isNaN()) {
                    repository.setTemperature(this)
                }
            }
        }
    }

    private val _convertedTemperature: MutableStateFlow<Float> = MutableStateFlow(Float.NaN)
    val convertedTemperature: StateFlow<Float> = _convertedTemperature.asStateFlow()

    fun convert() {
        getTemperatureAsFloat().let { sourceValue ->
            val valueInCelsius = when (uiState.value.sourceUnit) {
                UnitsAndScales.Celsius -> sourceValue
                UnitsAndScales.Fahrenheit -> sourceValue.convertFahrenheitToCelsius()
                else -> Float.NaN
            }
            with(uiState.value) {
                _convertedTemperature.update {
                    when (destinationUnit) {
                        UnitsAndScales.Celsius -> valueInCelsius
                        UnitsAndScales.Fahrenheit -> valueInCelsius.convertCelsiusToFahrenheit()
                        else -> Float.NaN
                    }.also { destinationValue ->
                        viewModelScope.launch {
                            supportingPaneUseCase.persist(
                                sourceUnit = sourceUnit,
                                sourceValue = sourceValue,
                                destinationUnit = destinationUnit,
                                destinationValue = destinationValue
                            )
                        }
                    }
                }
            }
        }
    }

    private fun Float.convertFahrenheitToCelsius() = (this - 32F) / 1.8F

    private fun Float.convertCelsiusToFahrenheit() = (this * 1.8F) + 32F

    private fun updateSupportingPaneState(unit: UnitsAndScales) {
        supportingPaneUseCase.update(unit)
    }
}
