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
class TareasViewModel @Inject constructor(
    private val tareasRepository: TareaRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(TareaUiState())
    val uiState = _uiState.asStateFlow()


    fun onEvent(event: TareaEvent){
        when (event) {
            TareaEvent.Delete -> delete()
            TareaEvent.New -> nuevo()
            is TareaEvent.DescripcionChange -> onDescripcionChange(event.descripcion)
            TareaEvent.Save -> save()
            is TareaEvent.TiempoChange -> onTiempoChange(event.tiempo)
            is TareaEvent.TareaChange -> onTareaIdChange(event.tareaId)
        }
    }


    init {
        getTarea()
    }

    //saveTarea
    private fun save() {
        viewModelScope.launch {
            if (_uiState.value.descripcion.isNullOrBlank() || _uiState.value.tiempo.toString().isNullOrBlank()){
                _uiState.update {
                    it.copy(errorMessage = "Campo vacios")
                }
            }
            else{
                tareasRepository.save(_uiState.value.toEntity())
            }
        }
    }

    private fun nuevo(){
        _uiState.update {
            it.copy(
                tareaId = 0,
                descripcion = "",
                tiempo = 0
            )
        }
    }

    //findTarea
    fun selectedTarea(tareaId: Int){
        viewModelScope.launch {
            if(tareaId > 0){
                val tarea = tareasRepository.find(tareaId)
                _uiState.update {
                    it.copy(
                        tareaId = tarea?.tareaId,
                        descripcion = tarea?.descripcion ?: "",
                        tiempo = tarea?.tiempo ?: 0
                    )
                }
            }
        }
    }

    //deleteTarea
    private fun delete() {
        viewModelScope.launch {
            tareasRepository.delete(_uiState.value.toEntity())
        }
    }

    private fun getTarea() {
        viewModelScope.launch {
            tareasRepository.getAll().collect { tareas ->
                _uiState.update {
                    it.copy(tareas = tareas)
                }
            }
        }
    }

    private fun onDescripcionChange(descripcion: String) {
        _uiState.update {
            it.copy(descripcion = descripcion)
        }
    }

    private fun onTiempoChange(tiempo: Int) {
        _uiState.update {
            it.copy(tiempo = tiempo)
        }
    }

    private fun onTareaIdChange(tareaId: Int) {
        _uiState.update {
            it.copy(tareaId = tareaId)
        }
    }

}

fun TareaUiState.toEntity() = TareaEntity(
    tareaId = tareaId,
    descripcion = descripcion ?: "",
    tiempo = tiempo ?: 0
)