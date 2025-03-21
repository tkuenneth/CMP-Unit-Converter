package de.thomaskuenneth.cmpunitconverter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combineTransform
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class AbstractViewModel(
    private val repository: BaseRepository, private val supportingPaneUseCase: DistanceSupportingPaneUseCase
) : ViewModel() {

    data class UiState(
        val sourceUnit: UnitsAndScales, val destinationUnit: UnitsAndScales, val value: Float
    )

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(
        UiState(
            sourceUnit = UnitsAndScales.Undefined, destinationUnit = UnitsAndScales.Undefined, value = Float.NaN
        )
    )
    val uiState = _uiState.asStateFlow()

    private val _convertedValue: MutableStateFlow<Float> = MutableStateFlow(Float.NaN)
    val convertedValue = _convertedValue.asStateFlow()

    init {
        viewModelScope.launch {
            combineTransform(
                repository.sourceUnit, repository.destinationUnit, repository.value
            ) { sourceUnit, destinationUnit, value ->
                emit(Triple(sourceUnit, destinationUnit, value))
            }.collect { (sourceUnit, destinationUnit, value) ->
                _uiState.update { current ->
                    current.copy(
                        sourceUnit = sourceUnit, destinationUnit = destinationUnit, value = value
                    )
                }
            }
        }
    }

    fun setSourceUnit(value: UnitsAndScales) {
        _uiState.update { it.copy(sourceUnit = value) }
        supportingPaneUseCase.update(value)
        viewModelScope.launch {
            repository.setSourceUnit(value)
        }
    }

    fun setDestinationUnit(value: UnitsAndScales) {
        _uiState.update { it.copy(destinationUnit = value) }
        supportingPaneUseCase.update(value)
        viewModelScope.launch {
            repository.setDestinationUnit(value)
        }
    }

    fun getValueAsFloat(): Float = uiState.value.value

    fun setValue(value: String) {
        viewModelScope.launch {
            with(value.convertLocalizedStringToFloat()) {
                if (!isNaN()) {
                    repository.setValue(this)
                }
            }
        }
    }

    protected fun convertSourceUnitToDestinationUnit(
        sourceUnitToBaseUnit: (Float, UnitsAndScales) -> Float,
        baseUnitToDestinationUnit: (Float, UnitsAndScales) -> Float
    ) {
        getValueAsFloat().let { sourceValue ->
            val valueInMeter = sourceUnitToBaseUnit(sourceValue, uiState.value.sourceUnit)
            with(uiState.value) {
                _convertedValue.update {
                    baseUnitToDestinationUnit(valueInMeter, destinationUnit).also { destinationValue ->
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
}
