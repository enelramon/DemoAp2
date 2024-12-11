package edu.ucne.composedemo.presentation.conversacion

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.ucne.composedemo.data.remote.Resource
import edu.ucne.composedemo.data.remote.dto.TicketGetMen
import edu.ucne.composedemo.data.remote.dto.TicketPostMen
import edu.ucne.composedemo.data.repository.MensajeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MensajeViewModel @Inject constructor(
    private val repository: MensajeRepository

) : ViewModel(){

    private val _postResult = MutableStateFlow<Resource<TicketPostMen>>(Resource.Loading())
    val postResult: StateFlow<Resource<TicketPostMen>> get() = _postResult

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun enviarMensaje(ticketPostMen: TicketPostMen) {
        viewModelScope.launch {
            repository.postMensaje(ticketPostMen).collect { resource ->
                _postResult.value = resource
            }
        }
    }

    private val _mensajeResult = MutableStateFlow<Resource<List<TicketGetMen>>>(Resource.Loading())
    val mensajeResult: StateFlow<Resource<List<TicketGetMen>>> get() = _mensajeResult

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun obtenerMensajes(idTicket: Double) {
        viewModelScope.launch {
            repository.getMensajes(idTicket).collect { resource ->
                _mensajeResult.value = resource
            }
        }
    }
}