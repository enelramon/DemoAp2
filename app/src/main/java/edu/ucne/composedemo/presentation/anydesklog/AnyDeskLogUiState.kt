package edu.ucne.composedemo.presentation.anydesklog

import edu.ucne.composedemo.data.remote.dto.AnyDeskLogDto
import java.util.Date

class AnyDeskLogUiState (
    val idCliente: Int? = null,
    val cliente: String? = "",
    val cantidad: Double? = null,
    val minutos: Int? = null,
    val fecha: Date? = null,
    val origen: String? = null,
    val destino: String? = null,
    val desde: Date? = null,
    val hasta: Date? = null,
    val idEncargado: Int? = null,
    val errorMessage: String? = null,
    val anyDeskLogs: List<AnyDeskLogDto> = emptyList()

)