package edu.ucne.composedemo.presentation.cobro

import edu.ucne.composedemo.data.remote.Resource
import edu.ucne.composedemo.data.remote.dto.CobroDto
import java.util.Date

data class CobroUiState(
    val idCobro: Int? = null,
    val fecha: Date? = Date(),
    val monto: Int? = null,
    val codigoCliente: Int?= null,
    val observaciones: String? = " ",
    val cobros: Resource<List<CobroDto>> = emptyList()

    )