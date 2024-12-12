package edu.ucne.composedemo.presentation.gastos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.composedemo.data.remote.Resource
import edu.ucne.composedemo.data.remote.dto.GastoDto
import edu.ucne.composedemo.data.repository.Gastosrepository
import edu.ucne.composedemo.data.repository.SuplidorGastoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GastosViewModel @Inject constructor(
    private val gastosRepository: Gastosrepository,
    private val suplidorGastoRepository: SuplidorGastoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(GastosUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: GastosEvent) {
        when (event) {
            is GastosEvent.GetGasto -> loadSuplidorData(event.idSuplidor)
            is GastosEvent.FechaChange -> _uiState.update { it.copy(fecha = event.fecha) }
            is GastosEvent.NcfChange -> _uiState.update { it.copy(ncf = event.ncf) }
            is GastosEvent.ConceptoChange -> _uiState.update { it.copy(concepto = event.concepto) }
            is GastosEvent.DescuentoChange -> validateDescuento(event.descuento)
            is GastosEvent.ItbisChange -> validateItbis(event.itbis)
            is GastosEvent.MontoChange -> validateMonto(event.monto)
            is GastosEvent.SaveGasto -> saveGasto(event.navigateBack)
        }
    }

    private fun loadSuplidorData(idSuplidor: Int) {
        viewModelScope.launch {
            suplidorGastoRepository.getSuplidoresGastos().collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }

                    is Resource.Success -> {
                        val suplidor = result.data?.find { it.idSuplidor == idSuplidor }
                        if (suplidor != null) {
                            _uiState.update {
                                it.copy(
                                    idSuplidor = suplidor.idSuplidor,
                                    esRecurrente = suplidor.esRecurrente,
                                    monto = if (suplidor.esRecurrente) suplidor.monto!! else 0.0,
                                    isLoading = false
                                )
                            }
                        } else {
                            _uiState.update {
                                it.copy(
                                    errorMessage = "No se encontró el suplidor",
                                    isLoading = false
                                )
                            }
                        }
                    }

                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                errorMessage = result.message ?: "Error al cargar suplidor",
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

    private fun validateDescuento(descuentoStr: String) {
        val descuento = descuentoStr.toDoubleOrNull() ?: 0.0
        _uiState.update { it.copy(descuento = descuento) }
    }

    private fun validateItbis(itbisStr: String) {
        val itbis = itbisStr.toDoubleOrNull() ?: 0.0
        _uiState.update { it.copy(itbis = itbis) }
    }

    private fun validateMonto(montoStr: String) {
        val monto = montoStr.toDoubleOrNull() ?: 0.0
        _uiState.update { it.copy(monto = monto) }
    }

    private fun validateFields(): Boolean {
        var isValid = true

        if (_uiState.value.fecha.isBlank()) {
            _uiState.update { it.copy(errorFecha = "La fecha no puede estar vacía") }
            isValid = false
        } else {
            _uiState.update { it.copy(errorFecha = null) }
        }

        if (_uiState.value.ncf.isBlank()) {
            _uiState.update { it.copy(errorNcf = "El NCF no puede estar vacío") }
            isValid = false
        } else {
            _uiState.update { it.copy(errorNcf = null) }
        }

        if (_uiState.value.concepto.isBlank()) {
            _uiState.update { it.copy(errorConcepto = "El concepto no puede estar vacío") }
            isValid = false
        } else {
            _uiState.update { it.copy(errorConcepto = null) }
        }

        if (!_uiState.value.esRecurrente && _uiState.value.monto <= 0.0) {
            _uiState.update { it.copy(errorMonto = "El monto debe ser mayor a 0") }
            isValid = false
        } else {
            _uiState.update { it.copy(errorMonto = null) }
        }

        return isValid
    }

    private fun saveGasto(navigateBack: () -> Unit) {
        if (!validateFields()) return

        val gasto = GastoDto(
            idGasto = _uiState.value.idGasto,
            fecha = _uiState.value.fecha,
            idSuplidor = _uiState.value.idSuplidor,
            suplidor = _uiState.value.suplidor,
            ncf = _uiState.value.ncf,
            concepto = _uiState.value.concepto,
            descuento = _uiState.value.descuento,
            itbis = _uiState.value.itbis,
            monto = _uiState.value.monto,
        )
        navigateBack()

        viewModelScope.launch {
            gastosRepository.createGasto(gasto).collect { result ->
                when (result) {
                    is Resource.Loading ->
                        _uiState.update {
                            it.copy(
                                isLoading = true
                            )
                        }

                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false
                            )
                        }
                    }

                    is Resource.Error ->
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = result.message ?: "Error al guardar gasto"
                            )
                        }
                }
            }
        }
    }
}
