package edu.ucne.composedemo.presentation.cobro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.composedemo.data.repository.CobroRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CobroViewModel @Inject constructor(
    private  val  cobroRepository: CobroRepository
) : ViewModel(){

    private val _uiState = MutableStateFlow(CobroUiState())
    val uiState = _uiState.asStateFlow()


    init {
        getCobros()
    }

    private fun getCobros(){
        viewModelScope.launch {
           cobroRepository.getCobro().collect { cobros ->
               _uiState.update {
                   it.copy(cobros = cobros)
               }

           }
        }
    }

    fun onEvent(event: CobroEvent){
        when(event){
            is CobroEvent.CobroChange -> TODO()
            is CobroEvent.CodigoClienteChange -> TODO()
            CobroEvent.Delete -> TODO()
            is CobroEvent.MontoChange -> TODO()
            CobroEvent.New -> TODO()
            is CobroEvent.ObservacionesChange -> TODO()
            CobroEvent.Save -> TODO()
        }
    }

}