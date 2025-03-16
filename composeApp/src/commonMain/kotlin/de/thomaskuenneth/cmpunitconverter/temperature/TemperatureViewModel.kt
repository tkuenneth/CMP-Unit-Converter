package de.thomaskuenneth.cmpunitconverter.temperature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.thomaskuenneth.cmpunitconverter.TemperatureSupportingPaneUseCase
import de.thomaskuenneth.cmpunitconverter.composeapp.generated.resources.*
import de.thomaskuenneth.cmpunitconverter.convertLocalizedStringToFloat
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class UiState(
    val sourceUnit: TemperatureUnit,
    val destinationUnit: TemperatureUnit,
    val temperature: Float
)

const val URL_WIKIPEDIA_CELSIUS = "https://en.wikipedia.org/wiki/Celsius"
const val URL_WIKIPEDIA_FAHRENHEIT = "https://en.wikipedia.org/wiki/Fahrenheit"

class TemperatureViewModel(
    private val repository: TemperatureRepository, val supportingPaneUseCase: TemperatureSupportingPaneUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(
        UiState(
            sourceUnit = TemperatureUnit.Celsius,
            destinationUnit = TemperatureUnit.Fahrenheit,
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

    fun setSourceUnit(value: TemperatureUnit) {
        _uiState.update { it.copy(sourceUnit = value) }
        updateSupportingPaneState(value)
        viewModelScope.launch {
            repository.setTemperatureSourceUnit(value)
        }
    }

    fun setDestinationUnit(value: TemperatureUnit) {
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

    private fun updateSupportingPaneState(unit: TemperatureUnit) {
        supportingPaneUseCase.update(
            info = when (unit) {
                TemperatureUnit.Celsius -> Res.string.celsius_info

                TemperatureUnit.Fahrenheit -> Res.string.fahrenheit_info
            }, lastClicked = when (unit) {
                TemperatureUnit.Celsius -> Res.string.celsius

                TemperatureUnit.Fahrenheit -> Res.string.fahrenheit
            }, url = when (unit) {
                TemperatureUnit.Celsius -> URL_WIKIPEDIA_CELSIUS

                TemperatureUnit.Fahrenheit -> URL_WIKIPEDIA_FAHRENHEIT
            }
        )
    }
}
