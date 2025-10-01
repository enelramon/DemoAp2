package edu.ucne.composedemo.presentation.entradashuacales.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.composedemo.domain.entradashuacales.usecase.DeleteEntradaHuacalUseCase
import edu.ucne.composedemo.domain.entradashuacales.usecase.ObserveEntradasHuacalesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EntradaHuacalListViewModel @Inject constructor(
    private val observeEntradasHuacalesUseCase: ObserveEntradasHuacalesUseCase,
    private val deleteEntradaHuacalUseCase: DeleteEntradaHuacalUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(EntradaHuacalListUiState(isLoading = true))
    val state: StateFlow<EntradaHuacalListUiState> = _state.asStateFlow()

    init {
        onEvent(EntradaHuacalListUiEvent.Load)
    }

    fun onEvent(event: EntradaHuacalListUiEvent) {
        when (event) {
            EntradaHuacalListUiEvent.Load -> observe()
            is EntradaHuacalListUiEvent.Delete -> onDelete(event.id)
            EntradaHuacalListUiEvent.CreateNew -> _state.update { it.copy(navigateToCreate = true) }
            is EntradaHuacalListUiEvent.Edit -> _state.update { it.copy(navigateToEditId = event.id) }
            is EntradaHuacalListUiEvent.ShowMessage -> _state.update { it.copy(message = event.message) }
            is EntradaHuacalListUiEvent.FilterByCliente -> {
                _state.update { it.copy(filterNombreCliente = event.nombreCliente) }
                applyFilters()
            }
            is EntradaHuacalListUiEvent.FilterByFechaInicio -> {
                _state.update { it.copy(filterFechaInicio = event.fecha) }
                applyFilters()
            }
            is EntradaHuacalListUiEvent.FilterByFechaFin -> {
                _state.update { it.copy(filterFechaFin = event.fecha) }
                applyFilters()
            }
            EntradaHuacalListUiEvent.ClearFilters -> {
                _state.update {
                    it.copy(
                        filterNombreCliente = "",
                        filterFechaInicio = "",
                        filterFechaFin = ""
                    )
                }
                observe()
            }
        }
    }

    private fun observe() {
        viewModelScope.launch {
            observeEntradasHuacalesUseCase().collectLatest { list ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        entradas = list,
                        message = null,
                        totalEntradas = list.sumOf { entrada -> entrada.cantidad },
                        totalPrecio = list.sumOf { entrada -> entrada.precio * entrada.cantidad }
                    )
                }
            }
        }
    }

    private fun applyFilters() {
        val currentState = _state.value
        val nombreCliente = if (currentState.filterNombreCliente.isBlank()) null 
                           else currentState.filterNombreCliente
        val fechaInicio = if (currentState.filterFechaInicio.isBlank()) null 
                         else currentState.filterFechaInicio
        val fechaFin = if (currentState.filterFechaFin.isBlank()) null 
                      else currentState.filterFechaFin

        viewModelScope.launch {
            observeEntradasHuacalesUseCase.filtered(
                nombreCliente,
                fechaInicio,
                fechaFin
            ).collectLatest { list ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        entradas = list,
                        message = null,
                        totalEntradas = list.sumOf { entrada -> entrada.cantidad },
                        totalPrecio = list.sumOf { entrada -> entrada.precio * entrada.cantidad }
                    )
                }
            }
        }
    }

    private fun onDelete(id: Int) {
        viewModelScope.launch {
            deleteEntradaHuacalUseCase(id)
            onEvent(EntradaHuacalListUiEvent.ShowMessage("Eliminado"))
        }
    }

    fun onNavigationHandled() {
        _state.update { it.copy(navigateToCreate = false, navigateToEditId = null) }
    }
}
