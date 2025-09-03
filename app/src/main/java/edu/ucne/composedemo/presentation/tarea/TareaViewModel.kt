package edu.ucne.composedemo.presentation.tarea

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.composedemo.data.local.entities.TareaEntity
import edu.ucne.composedemo.data.repository.TareaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TareaViewModel @Inject constructor(
    val tareaRepository: TareaRepository
): ViewModel(){
    private val _uiState = MutableStateFlow(TareaUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getTareas()
    }

    fun find(tareaId: Int?){
        viewModelScope.launch {
            if (tareaId != null){
                val tarea = tareaRepository.find(tareaId)
                _uiState.update {
                    it.copy(
                        tareaId = tarea.tareaId,
                        descripcion = tarea.descripcion,
                        tiempo = tarea.tiempo
                    )
                }
            }
        }

    }

    fun onChangeTareaId(id: Int?) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    tareaId = id
                )
            }
        }
    }

    private fun deleteTarea() {
        viewModelScope.launch {
            tareaRepository.delete(_uiState.value.toEntity())
        }
    }

    private fun saveTarea() {
        viewModelScope.launch {
            if (_uiState.value.descripcion.isNullOrBlank()) {
                _uiState.update {
                    it.copy(
                        errorDescripcion = "El nombre no puede estar vacio"
                    )
                }
            } else {
                _uiState.value.tiempo?.let {
                    if (it <= 0) {
                        _uiState.update {
                            it.copy(
                                errorTiempo = "debe asignar un tiempo"
                            )
                        }
                    } else {
                        tareaRepository.save(_uiState.value.toEntity())
                        _uiState.update {
                            it.copy(
                                guardado = "Se ha guardado correctamente"
                            )
                        }
                    }
                }
            }
        }
    }

    fun onEvent(event: TareaEvent) {
        when (event) {
            is TareaEvent.ChangeDescripcion -> changeDescripcion(event.descripcion)
            is TareaEvent.ChangeTiempo -> changeTiempo(event.tiempo)
            TareaEvent.Delete -> deleteTarea()
            TareaEvent.New -> new()
            TareaEvent.Save -> saveTarea()
            is TareaEvent.ChangeTareaId -> onChangeTareaId(event.tareaId)
        }
    }

    private fun changeTiempo(tiempo: Int?) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    tiempo = tiempo
                )
            }
        }

    }

    private fun changeDescripcion(descripcion: String?){
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    descripcion = descripcion
                )
            }
        }
    }

    private fun new(){
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    descripcion = "",
                    tiempo = 0,
                    errorTiempo = "",
                    errorDescripcion = "",
                    tareas = emptyList()
                )
            }
        }
    }

    private fun getTareas(){
        viewModelScope.launch {
            tareaRepository.getAll().collect { tareas ->
                _uiState.update {
                    it.copy(
                        tareas = tareas
                    )
                }
            }
        }
    }

    fun TareaUiState.toEntity() = TareaEntity(
        tareaId = tareaId ?: 0 ,
        descripcion = descripcion ?: "",
        tiempo = tiempo ?: 0
    )
}