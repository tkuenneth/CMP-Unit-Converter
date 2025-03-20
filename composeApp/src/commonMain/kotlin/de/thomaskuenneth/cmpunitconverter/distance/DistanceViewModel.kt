package de.thomaskuenneth.cmpunitconverter.distance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.thomaskuenneth.cmpunitconverter.DistanceSupportingPaneUseCase
import de.thomaskuenneth.cmpunitconverter.composeapp.generated.resources.*
import de.thomaskuenneth.cmpunitconverter.convertLocalizedStringToFloat
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class UiState(
    val sourceUnit: DistanceUnit,
    val destinationUnit: DistanceUnit,
    val distance: Float
)

const val URL_WIKIPEDIA_METER = "https://en.wikipedia.org/wiki/Metre"
const val URL_WIKIPEDIA_MILE = "https://en.wikipedia.org/wiki/Mile"

class DistanceViewModel(
    private val repository: DistanceRepository, val supportingPaneUseCase: DistanceSupportingPaneUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(
        UiState(
            sourceUnit = DistanceUnit.Meter,
            destinationUnit = DistanceUnit.Mile,
            distance = Float.NaN
        )
    )
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            combineTransform(
                repository.sourceUnit, repository.destinationUnit, repository.distance
            ) { sourceUnit, destinationUnit, distance ->
                emit(Triple(sourceUnit, destinationUnit, distance))
            }.collect { (sourceUnit, destinationUnit, distance) ->
                _uiState.update { current ->
                    current.copy(
                        sourceUnit = sourceUnit,
                        destinationUnit = destinationUnit,
                        distance = distance
                    )
                }
                updateSupportingPaneState(sourceUnit)
            }
        }
    }

    fun setSourceUnit(value: DistanceUnit) {
        _uiState.update { it.copy(sourceUnit = value) }
        updateSupportingPaneState(value)
        viewModelScope.launch {
            repository.setDistanceSourceUnit(value)
        }
    }

    fun setDestinationUnit(value: DistanceUnit) {
        _uiState.update { it.copy(destinationUnit = value) }
        updateSupportingPaneState(value)
        viewModelScope.launch {
            repository.setDistanceDestinationUnit(value)
        }
    }

    fun getDistanceAsFloat(): Float = uiState.value.distance

    fun setDistance(value: String) {
        viewModelScope.launch {
            with(value.convertLocalizedStringToFloat()) {
                if (!isNaN()) {
                    repository.setDistance(this)
                }
            }
        }
    }

    private val _convertedDistance: MutableStateFlow<Float> = MutableStateFlow(Float.NaN)
    val convertedDistance: StateFlow<Float> = _convertedDistance.asStateFlow()

    fun convert() {
        getDistanceAsFloat().let { sourceValue ->
            val valueInMeter = when (uiState.value.sourceUnit) {
                DistanceUnit.Meter -> sourceValue
                DistanceUnit.Mile -> sourceValue.convertMileToMeter()
            }
            with(uiState.value) {
                _convertedDistance.update {
                    when (destinationUnit) {
                        DistanceUnit.Meter -> valueInMeter
                        DistanceUnit.Mile -> valueInMeter.convertMeterToMile()
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

    private fun Float.convertMileToMeter() = this / 0.00062137F

    private fun Float.convertMeterToMile() = this * 0.00062137F

    private fun updateSupportingPaneState(unit: DistanceUnit) {
        supportingPaneUseCase.update(
            info = when (unit) {
                DistanceUnit.Meter -> Res.string.meter_info

                DistanceUnit.Mile -> Res.string.mile_info
            }, lastClicked = when (unit) {
                DistanceUnit.Meter -> Res.string.meter

                DistanceUnit.Mile -> Res.string.mile
            }, url = when (unit) {
                DistanceUnit.Meter -> URL_WIKIPEDIA_METER

                DistanceUnit.Mile -> URL_WIKIPEDIA_MILE
            }
        )
    }
}
