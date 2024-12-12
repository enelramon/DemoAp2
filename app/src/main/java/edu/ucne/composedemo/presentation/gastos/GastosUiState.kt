package edu.ucne.composedemo.presentation.gastos

import edu.ucne.composedemo.data.remote.dto.GastoDto

data class GastosUiState(
    val idGasto: Int = 0,
    val fecha: String = "",
    val idSuplidor: Int = 0,
    val suplidor: String = "",
    val ncf: String = "",
    val concepto: String = "",
    val descuento: Double = 0.0,
    val itbis: Double = 0.0,
    val monto: Double = 0.0,
    val esRecurrente: Boolean = false,
    val errorFecha: String? = null,
    val errorNcf: String? = null,
    val errorConcepto: String? = null,
    val errorDescuento: String? = null,
    val errorItbis: String? = null,
    val errorMonto: String? = null,
    val errorMessage: String = "",
    val isLoading: Boolean = false,
    val gastos: List<GastoDto> = emptyList()
)
