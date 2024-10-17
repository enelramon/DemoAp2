package edu.ucne.composedemo.presentation.equiposanydesk

import edu.ucne.composedemo.data.remote.dto.EquiposAnyDeskDto

data class EquiposAnyDeskUiState(
    val equiposAnyDesks: List<EquiposAnyDeskDto> = emptyList(),
    val errorMessage: String? = "",
    val isLoading: Boolean = false
)
