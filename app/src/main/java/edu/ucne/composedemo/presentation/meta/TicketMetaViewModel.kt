package edu.ucne.composedemo.presentation.meta

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.composedemo.data.remote.Resource
import edu.ucne.composedemo.data.remote.dto.TicketMetaRequestDto
import edu.ucne.composedemo.data.repository.TicketMetaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TicketMetaViewModel @Inject constructor(
    private val ticketMetaRepository: TicketMetaRepository
): ViewModel(){
    private val _uiState = MutableStateFlow(TicketMetaUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getTicketMetas(1)
    }

    private fun getTicketMetas(idUsuario: Int){
        viewModelScope.launch {
            ticketMetaRepository.getMetasUsuario(idUsuario).collectLatest { result ->
                when(result){
                    is Resource.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }
                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                ticketMetas = result.data ?: emptyList(),
                                isLoading = false
                            )
                        }
                    }
                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                errorMessage = result.message,
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

    fun onEvent(event: TicketMetaUiEvent){
        when(event){
            is TicketMetaUiEvent.idTicketChanged -> {
                _uiState.update { it.copy(idTicket = event.idTicket) }
            }
            TicketMetaUiEvent.Save -> {
                viewModelScope.launch {
                    ticketMetaRepository.addTicketMeta(uiState.value.toRequestDto())
                }
            }
            TicketMetaUiEvent.Delete -> TODO()
        }
    }

    private fun TicketMetaUiState.toRequestDto() = TicketMetaRequestDto(
        idTicket = idTicket,
        idUsuario = idUsuario
    )
}