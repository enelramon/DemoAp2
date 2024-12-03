package edu.ucne.composedemo.presentation.gastorecurrencia

import edu.ucne.composedemo.data.remote.dto.GastoRecurrenciaDto
import edu.ucne.composedemo.data.remote.dto.SuplidorGastoDto

data class GastoRecurrenciaUiState(
    val id: Int = 0,
    val idSuplidor: Int = 0,
    val periodicidad: Int = 0,
    val errorPeriodicidad: String = "",
    val dia: Int = 0,
    val errorDia: String = "",
    val monto: Double = 0.0,
    val errorMonto: String = "",
    val esRecurrente: Boolean = false,
    val ultimaRecurencia: String = "",
    val isLoading: Boolean = false,
    val gastosRecurrencia: List<GastoRecurrenciaDto> = emptyList(),
    val suplidoresGastos: List<SuplidorGastoDto> = emptyList(),
    val errorMessage: String = "",
    val suplidorgasto : SuplidorGastoDto = SuplidorGastoDto(),
    val gastoRecurrencia: GastoRecurrenciaDto = GastoRecurrenciaDto()
)

fun GastoRecurrenciaUiState.toDto() = GastoRecurrenciaDto(
    id = id,
    idSuplidor = idSuplidor,
    periodicidad = periodicidad,
    dia = dia,
    monto = monto,
    ultimaRecurencia = ultimaRecurencia
)