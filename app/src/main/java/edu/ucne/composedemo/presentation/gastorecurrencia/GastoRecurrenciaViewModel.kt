package edu.ucne.composedemo.presentation.gastorecurrencia

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.composedemo.data.remote.Resource
import edu.ucne.composedemo.data.remote.dto.GastoRecurrenciaDto
import edu.ucne.composedemo.data.remote.dto.SuplidorGastoDto
import edu.ucne.composedemo.data.repository.GastoRecurrenciaRepository
import edu.ucne.composedemo.data.repository.SuplidorGastoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class GastoRecurrenciaViewModel @Inject constructor(
    private val gastoRecurrenciaRepository: GastoRecurrenciaRepository,
    private val suplidorGastoRepository: SuplidorGastoRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(GastoRecurrenciaUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getGastosRecurrencia()
        getSuplidorGastos()
    }

    fun onEvent(event: GastoRecurrenciaEvent) {
        when (event) {
            is GastoRecurrenciaEvent.GetGastosRecurrencia -> cargarSuplidor(event.idSuplidor)
            is GastoRecurrenciaEvent.DiaChange -> onDiaChanged(event.dia)
            is GastoRecurrenciaEvent.MontoChange -> onMontoChanged(event.monto)
            is GastoRecurrenciaEvent.PeriodicidadChange -> onPeriodicidadChanged(event.periodicidad)
            is GastoRecurrenciaEvent.EsRecurenteChange -> onEsRecurrenteChanged(event.esRecurente)
            is GastoRecurrenciaEvent.SaveGastoRecurrencia -> save(event.goSuplidoresGastos)
        }
    }

    private fun getGastosRecurrencia() {
        viewModelScope.launch {
            gastoRecurrenciaRepository.getGastostoRecurrencias().collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = true,
                            errorMessage = ""
                        )
                    }

                    is Resource.Success -> {
                        _uiState.value = _uiState.value.copy(
                            gastosRecurrencia = result.data ?: emptyList(),
                            errorMessage = ""
                        )
                    }

                    is Resource.Error -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMessage = result.message ?: "Error desconocido"
                        )
                    }
                }
            }
        }
    }

    private fun getSuplidorGastos() {
        viewModelScope.launch {
            suplidorGastoRepository.getSuplidoresGastos().collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = true,
                            errorMessage = ""
                        )
                    }

                    is Resource.Success -> {
                        _uiState.value = _uiState.value.copy(
                            suplidoresGastos = result.data ?: emptyList(),
                            errorMessage = ""
                        )
                    }

                    is Resource.Error -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMessage = result.message ?: "Error desconocido"
                        )
                    }
                }
            }
        }
    }

    private fun cargarSuplidor(id: Int){
        _uiState.value = _uiState.value.copy(
            gastoRecurrencia = _uiState.value.gastosRecurrencia.find { it.idSuplidor == id }
                ?: GastoRecurrenciaDto(),
            suplidorgasto = _uiState.value.suplidoresGastos.find { it.idSuplidor == id }
                ?: SuplidorGastoDto(),
        )
        _uiState.value = _uiState.value.copy(
            id = _uiState.value.gastoRecurrencia.id,
        )
        _uiState.value = _uiState.value.copy(
            idSuplidor = id,
            periodicidad = _uiState.value.gastoRecurrencia.periodicidad,
            dia = _uiState.value.gastoRecurrencia.dia,
            monto = _uiState.value.gastoRecurrencia.monto,
            esRecurrente = _uiState.value.suplidorgasto.esRecurrente,
            isLoading = false
        )
    }

    private fun onEsRecurrenteChanged(value: Boolean) {
        _uiState.value = _uiState.value.copy(
            esRecurrente = value
        )
    }

    private fun onPeriodicidadChanged(periodicidadStr: String) {
        val periodicidad = periodicidadStr.toIntOrNull() ?: 0
        _uiState.value = _uiState.value.copy(
            periodicidad = periodicidad
        )
        if (periodicidad < 1 || periodicidad > 2) {
            _uiState.value = _uiState.value.copy(
                errorPeriodicidad = "Periodicidad no puede estar vacio"
            )
        }else
            _uiState.value = _uiState.value.copy(
                errorPeriodicidad = ""
            )
    }

    private fun onDiaChanged(diaStr: String) {
        val dia = diaStr.toIntOrNull() ?: 0
        _uiState.value = _uiState.value.copy(
            dia = dia
        )
        if (dia < 1 || dia > 31) {
            _uiState.value = _uiState.value.copy(
                errorDia = "Dia no puede estar vacio"
            )
        }else
            _uiState.value = _uiState.value.copy(
                errorDia = ""
            )
    }

    private fun onMontoChanged(montoStr: String) {
        val regex = Regex("^([0-9]{1,6}(\\.[0-9]{1,2})?|1000000(\\.00?)?)$")
        val parsedMonto = montoStr.toDoubleOrNull()

        if (montoStr.isEmpty() || parsedMonto == null || parsedMonto < 0.01) {
            _uiState.value = _uiState.value.copy(
                errorMonto = "El monto debe ser mayor a 0.01"
            )
            return
        }

        if (!regex.matches(montoStr)) {
            _uiState.value = _uiState.value.copy(
                errorMonto = "El monto debe ser menor o igual a 1,000,000"
            )
            return
        }

        _uiState.value = _uiState.value.copy(
            monto = parsedMonto,
            errorMonto = ""
        )
    }

    private fun deleteRecurrencia(goSuplidoresGastos: () -> Unit){
        val id = _uiState.value.id
        if(id != 0){
            viewModelScope.launch {
                gastoRecurrenciaRepository.deleteGastoRecurencia(id).collectLatest { result ->
                    when (result) {
                        is Resource.Loading -> {
                            _uiState.value = _uiState.value.copy(
                                isLoading = true,
                                errorMessage = ""
                            )

                        }

                        is Resource.Success -> {
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                errorMessage = ""
                            )
                            goSuplidoresGastos()
                        }

                        is Resource.Error -> {
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                errorMessage = result.message ?: "Error desconocido"
                            )
                        }
                    }
                }
            }
        }else{
            goSuplidoresGastos()
        }
    }

    private fun saveGastoRecurrencia(goSuplidoresGastos: () -> Unit) {
        if(validation()){
            _uiState.value = _uiState.value.copy(
                ultimaRecurencia = getNextMonthDate(_uiState.value.dia)
            )
            val gastoRecurrencia = _uiState.value.toDto()
            viewModelScope.launch {
                gastoRecurrenciaRepository.createGastoRecurencia(gastoRecurrencia).collectLatest { result ->
                    when (result) {
                        is Resource.Loading -> {
                            _uiState.value = _uiState.value.copy(
                                isLoading = true,
                                errorMessage = ""
                            )
                        }

                        is Resource.Success -> {
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                errorMessage = ""
                            )
                            goSuplidoresGastos()
                        }

                        is Resource.Error -> {
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                errorMessage = result.message ?: "Error desconocido"
                            )
                        }
                    }
                }
            }
        }
    }

    private fun getNextMonthDate(day: Int): String {
        val today = LocalDate.now()
        val currentYear = today.year
        val currentMonth = today.monthValue

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        if(_uiState.value.periodicidad == 2){
            return LocalDate.of(currentYear, currentMonth, day).plusYears(1).format(formatter)
        }
        return LocalDate.of(currentYear, currentMonth, day).plusMonths(1).format(formatter)
    }

    private fun save(goSuplidoresGastos: () -> Unit) {
        if(_uiState.value.esRecurrente){
            saveGastoRecurrencia(goSuplidoresGastos)
        }else{
            deleteRecurrencia(goSuplidoresGastos)
        }
    }

    private fun validation(): Boolean {
        var isValid = true
        val regex = Regex("^([0-9]{1,6}(\\.[0-9]{1,2})?|1000000(\\.00?)?)$")
        val monto = _uiState.value.monto.toString()
        val parsedMonto = monto.toDoubleOrNull()

        if (_uiState.value.periodicidad !in 1..2) {
            _uiState.value = _uiState.value.copy(
                errorPeriodicidad = "Periodicidad no puede estar vacía o debe ser 1 (Mensual) o 2 (Anual)"
            )
            isValid = false
        } else {
            _uiState.value = _uiState.value.copy(errorPeriodicidad = "")
        }

        if (_uiState.value.dia !in 1..31) {
            _uiState.value = _uiState.value.copy(
                errorDia = "El día debe estar entre 1 y 31"
            )
            isValid = false
        } else {
            _uiState.value = _uiState.value.copy(errorDia = "")
        }

        if (monto.isEmpty() || parsedMonto == null || parsedMonto < 0.01) {
            _uiState.value = _uiState.value.copy(
                errorMonto = "El monto debe ser mayor a 0.01"
            )
            isValid = false
        } else if (!regex.matches(monto)) {
            _uiState.value = _uiState.value.copy(
                errorMonto = "El monto debe ser menor o igual a 1,000,000"
            )
            isValid = false
        } else {
            _uiState.value = _uiState.value.copy(errorMonto = "")
        }
        return isValid
    }
}