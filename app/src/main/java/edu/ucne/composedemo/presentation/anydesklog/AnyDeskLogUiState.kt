package edu.ucne.composedemo.presentation.anydesklog

import edu.ucne.composedemo.data.remote.dto.AnyDeskLogDto


data class AnyDeskLogUiState(
    val errorMessage: String = "",
    val anyDeskLogs: List<AnyDeskLogDto> = emptyList(),
    val isLoading: Boolean = false
)