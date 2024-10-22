package edu.ucne.composedemo.presentation.cxc

import edu.ucne.composedemo.data.remote.dto.CxcDto

data class CxcUiState(
    val idVenta: Int? = null,
    val fecha: String? = "",
    val monto: Float? = 0f,
    val balance: Float?  = 0f,
    val cxc: List<CxcDto> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String = ""
)
