package edu.ucne.composedemo.presentation.deposito

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.composedemo.data.remote.Resource
import edu.ucne.composedemo.data.remote.dto.DepositoDto
import edu.ucne.composedemo.data.repository.DepositoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class DepositoViewModel @Inject constructor(
    private val depositoRepository: DepositoRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(DepositoUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getDepositos()
    }

    fun save() {
        viewModelScope.launch {
            if (isValid()) {
                depositoRepository.save(_uiState.value.toEntity())
            }
        }
    }

    fun delete(id: Int) {
        viewModelScope.launch {
            depositoRepository.delete(id)
        }
    }

    fun update() {
        viewModelScope.launch {
            _uiState.value.depositoId.let {
                if (it != null) {
                    depositoRepository.update(
                        it, DepositoDto(
                            idDeposito = _uiState.value.depositoId,
                            fecha = _uiState.value.fecha,
                            concepto = _uiState.value.concepto,
                            monto = _uiState.value.monto.toDouble(),
                            idCuenta = _uiState.value.idCuenta.toInt()
                        )
                    )
                }
            }
        }
    }

    fun new() {
        _uiState.value = DepositoUiState()
    }

    fun onConceptoChange(concepto: String) {
        _uiState.update {
            it.copy(
                concepto = concepto,
                errorMessage = if (concepto.isBlank()) "Debes rellenar el campo Concepto"
                else null
            )
        }
    }

    fun onMontoChange(monto: String) {
        _uiState.update {
            val montoDouble = monto.toDouble()
            it.copy(
                monto = monto,
                errorMessage = when {
                    montoDouble <= 0 -> "Debe ingresar un valor mayor a 0"
                    else -> null
                }
            )
        }
    }

    fun onCuentaChange(cuenta: String) {
        _uiState.update {
            val cuentaInt = cuenta.toInt()
            it.copy(
                idCuenta = cuenta,
                errorMessage = when {
                    cuentaInt <= 0 -> "Debe ingresar un valor mayor a 0"
                    else -> null
                }
            )
        }
    }

    fun onFechaChange(fecha: String) {
        val formatoEntrada = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val formatoSalida = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        val date = formatoEntrada.parse(fecha)
        val formattedDate = formatoSalida.format(date!!)

        _uiState.update {
            it.copy(
                fecha = formattedDate,
                errorMessage = if (fecha.isBlank()) "Debes rellenar el campo Fecha" else null
            )
        }
    }

    fun find(depositoId: Int) {
        viewModelScope.launch {
            if (depositoId > 0) {
                val depositoDto = depositoRepository.find(depositoId)
                if (depositoDto.idDeposito != 0) {
                    _uiState.update {
                        it.copy(
                            depositoId = depositoDto.idDeposito,
                            fecha = depositoDto.fecha,
                            concepto = depositoDto.concepto,
                            monto = depositoDto.monto.toString(),
                            idCuenta = depositoDto.idCuenta.toString()
                        )
                    }
                }
            }
        }
    }

    fun getDepositos() {
        viewModelScope.launch {
            depositoRepository.getEntidades().collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.update {
                            it.copy(isLoading = true)
                        }
                    }

                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                depositos = result.data ?: emptyList(),
                                isLoading = false
                            )
                        }
                    }

                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                errorMessage = result.message ?: "Error desconocido",
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

    fun isValid(): Boolean {
        return uiState.value.concepto.isNotBlank()
                && uiState.value.fecha.isNotBlank()
                && uiState.value.monto.isNotBlank()
                && uiState.value.idCuenta.isNotBlank()
    }

}

fun DepositoUiState.toEntity() = DepositoDto(
    idDeposito = depositoId,
    fecha = fecha,
    concepto = concepto,
    monto = monto.toDouble(),
    idCuenta = idCuenta.toInt()
)