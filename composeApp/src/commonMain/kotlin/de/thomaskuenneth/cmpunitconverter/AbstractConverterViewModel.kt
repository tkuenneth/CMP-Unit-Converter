package de.thomaskuenneth.cmpunitconverter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.thomaskuenneth.cmpunitconverter.composeapp.generated.resources.Res
import de.thomaskuenneth.cmpunitconverter.composeapp.generated.resources.something_went_wrong
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combineTransform
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.StringResource

abstract class AbstractConverterViewModel(
    entries: List<UnitsAndScales>,
    placeholder: StringResource,
    private val repository: ConverterRepository,
    val supportingPaneUseCase: AbstractSupportingPaneUseCase
) : ViewModel() {

    sealed class SnackbarVisibility {
        data object Hidden : SnackbarVisibility()
        data class Message(val message: StringResource) : SnackbarVisibility()
    }

    data class UiState(
        val sourceUnit: UnitsAndScales,
        val destinationUnit: UnitsAndScales,
        val value: Float,
        val entries: List<UnitsAndScales>,
        val placeholder: StringResource,
        val snackbarVisibility: SnackbarVisibility
    )

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(
        UiState(
            sourceUnit = UnitsAndScales.Undefined,
            destinationUnit = UnitsAndScales.Undefined,
            value = Float.NaN,
            entries = entries,
            placeholder = placeholder,
            snackbarVisibility = SnackbarVisibility.Hidden
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

    fun setSnackbarVisibility(visibility: SnackbarVisibility) {
        _uiState.update { state -> state.copy(snackbarVisibility = visibility) }
    }

    fun handleOpenInBrowserResult(result: Boolean) {
        if (result) {
            setSnackbarVisibility(SnackbarVisibility.Message(Res.string.something_went_wrong))
        }
    }

    fun setSourceUnit(value: UnitsAndScales) {
        _convertedValue.update { Float.NaN }
        _uiState.update { it.copy(sourceUnit = value) }
        supportingPaneUseCase.update(value)
        viewModelScope.launch {
            repository.setSourceUnit(value)
        }
    }

    fun setDestinationUnit(value: UnitsAndScales) {
        _convertedValue.update { Float.NaN }
        _uiState.update { it.copy(destinationUnit = value) }
        supportingPaneUseCase.update(value)
        viewModelScope.launch {
            repository.setDestinationUnit(value)
        }
    }

    fun getValueAsFloat(): Float = uiState.value.value

    fun setValue(value: Float) {
        viewModelScope.launch {
            repository.setValue(value)
        }
    }

    abstract fun convert()

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
