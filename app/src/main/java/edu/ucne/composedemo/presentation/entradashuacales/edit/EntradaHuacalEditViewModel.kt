package edu.ucne.composedemo.presentation.entradashuacales.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.composedemo.domain.entradashuacales.model.EntradaHuacal
import edu.ucne.composedemo.domain.entradashuacales.usecase.*
import edu.ucne.composedemo.presentation.navigation.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EntradaHuacalEditViewModel @Inject constructor(
    private val getEntradaHuacalUseCase: GetEntradaHuacalUseCase,
    private val upsertEntradaHuacalUseCase: UpsertEntradaHuacalUseCase,
    private val deleteEntradaHuacalUseCase: DeleteEntradaHuacalUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = MutableStateFlow(EntradaHuacalEditUiState())
    val state: StateFlow<EntradaHuacalEditUiState> = _state.asStateFlow()

    init {
        val id = savedStateHandle.toRoute<Screen.EntradaHuacalEdit>().idEntrada
        onEvent(EntradaHuacalEditUiEvent.Load(id))
    }

    fun onEvent(event: EntradaHuacalEditUiEvent) {
        when (event) {
            is EntradaHuacalEditUiEvent.Load -> onLoad(event.id)
            is EntradaHuacalEditUiEvent.FechaChanged -> _state.update {
                it.copy(fecha = event.value, fechaError = null)
            }
            is EntradaHuacalEditUiEvent.NombreClienteChanged -> _state.update {
                it.copy(nombreCliente = event.value, nombreClienteError = null)
            }
            is EntradaHuacalEditUiEvent.CantidadChanged -> _state.update {
                it.copy(cantidad = event.value, cantidadError = null)
            }
            is EntradaHuacalEditUiEvent.PrecioChanged -> _state.update {
                it.copy(precio = event.value, precioError = null)
            }
            EntradaHuacalEditUiEvent.Save -> onSave()
            EntradaHuacalEditUiEvent.Delete -> onDelete()
        }
    }

    private fun onLoad(id: Int?) {
        if (id == null || id == 0) {
            _state.update { 
                it.copy(
                    isNew = true, 
                    idEntrada = null,
                    fecha = java.time.LocalDate.now().toString()
                ) 
            }
            return
        }
        viewModelScope.launch {
            val entrada = getEntradaHuacalUseCase(id)
            if (entrada != null) {
                _state.update {
                    it.copy(
                        isNew = false,
                        idEntrada = entrada.idEntrada,
                        fecha = entrada.fecha,
                        nombreCliente = entrada.nombreCliente,
                        cantidad = entrada.cantidad.toString(),
                        precio = entrada.precio.toString()
                    )
                }
            }
        }
    }

    private fun onSave() {
        val currentState = state.value
        
        val fechaValidation = validateFecha(currentState.fecha)
        val nombreValidation = validateNombreCliente(currentState.nombreCliente)
        val cantidadValidation = validateCantidad(currentState.cantidad)
        val precioValidation = validatePrecio(currentState.precio)

        if (!fechaValidation.isValid || !nombreValidation.isValid || 
            !cantidadValidation.isValid || !precioValidation.isValid) {
            _state.update {
                it.copy(
                    fechaError = fechaValidation.error,
                    nombreClienteError = nombreValidation.error,
                    cantidadError = cantidadValidation.error,
                    precioError = precioValidation.error
                )
            }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isSaving = true) }
            val id = currentState.idEntrada ?: 0
            val entrada = EntradaHuacal(
                idEntrada = id,
                fecha = currentState.fecha,
                nombreCliente = currentState.nombreCliente,
                cantidad = currentState.cantidad.toInt(),
                precio = currentState.precio.toDouble()
            )
            val result = upsertEntradaHuacalUseCase(entrada)
            result.onSuccess { newId ->
                _state.update { it.copy(isSaving = false, saved = true, idEntrada = newId) }
            }.onFailure {
                _state.update { it.copy(isSaving = false) }
            }
        }
    }

    private fun onDelete() {
        val id = state.value.idEntrada ?: return
        viewModelScope.launch {
            _state.update { it.copy(isDeleting = true) }
            deleteEntradaHuacalUseCase(id)
            _state.update { it.copy(isDeleting = false, deleted = true) }
        }
    }
}
