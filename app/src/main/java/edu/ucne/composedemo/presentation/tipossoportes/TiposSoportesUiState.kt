package edu.ucne.composedemo.presentation.tipossoportes

import edu.ucne.composedemo.data.remote.dto.TiposSoportesDto

data class TiposSoportesUiState (
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val tiposSoportes: List<TiposSoportesDto> = emptyList()
)