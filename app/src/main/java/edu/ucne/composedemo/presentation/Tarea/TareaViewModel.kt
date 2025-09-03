package edu.ucne.composedemo.presentation.Tarea

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.composedemo.data.local.entities.TareaEntity
import edu.ucne.composedemo.data.repository.TareaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TareaViewModel @Inject constructor(
    private val tareaRepository: TareaRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(TareaUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getTareas()
    }

    fun onEvent(event: TareaEvent) {
        when (event) {
            is TareaEvent.TareaIdChange -> {
                _uiState.update {
                    it.copy(tareaId = event.tareaId)
                }
            }
            is TareaEvent.DescripcionChange -> {
                _uiState.update {
                    it.copy(descripcion = event.descripcion, validationErrors = it.validationErrors - "descripcion")
                }
            }
            is TareaEvent.TiempoChange -> {
                _uiState.update {
                    it.copy(tiempo = event.tiempo, validationErrors = it.validationErrors - "tiempo")
                }
            }
            TareaEvent.Save -> {
                saveTarea()
            }
            TareaEvent.Delete -> {
                deleteTarea()
            }
            TareaEvent.New -> {
                newTarea()
            }
            is TareaEvent.SelectedTarea -> {
                selectedTarea(event.tareaId)
            }
        }
    }

    private fun validateForm(): Boolean {
        val errors = mutableMapOf<String, String>()
        
        if (_uiState.value.descripcion.isBlank()) {
            errors["descripcion"] = "La descripción es requerida"
        }
        
        if (_uiState.value.tiempo.isBlank()) {
            errors["tiempo"] = "El tiempo es requerido"
        }
        
        _uiState.update {
            it.copy(validationErrors = errors)
        }
        
        return errors.isEmpty()
    }

    private fun saveTarea() {
        if (!validateForm()) return
        
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true, errorMessage = "") }
                
                val tarea = TareaEntity(
                    tareaId = _uiState.value.tareaId,
                    descripcion = _uiState.value.descripcion.trim(),
                    tiempo = _uiState.value.tiempo.trim()
                )
                
                tareaRepository.save(tarea)
                
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isSuccess = true,
                        errorMessage = ""
                    )
                }
                
                newTarea() // Reset form
                
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Error al guardar la tarea: ${e.message}"
                    )
                }
            }
        }
    }

    private fun deleteTarea() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true) }
                
                val currentTarea = TareaEntity(
                    tareaId = _uiState.value.tareaId,
                    descripcion = _uiState.value.descripcion,
                    tiempo = _uiState.value.tiempo
                )
                
                tareaRepository.delete(currentTarea)
                
                _uiState.update {
                    it.copy(isLoading = false, isSuccess = true)
                }
                
                newTarea() // Reset form
                
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Error al eliminar la tarea: ${e.message}"
                    )
                }
            }
        }
    }

    private fun newTarea() {
        _uiState.update {
            it.copy(
                tareaId = 0,
                descripcion = "",
                tiempo = "",
                isSuccess = false,
                errorMessage = "",
                validationErrors = emptyMap()
            )
        }
    }

    private fun selectedTarea(tareaId: Int) {
        viewModelScope.launch {
            try {
                val tarea = tareaRepository.find(tareaId)
                tarea?.let {
                    _uiState.update { currentState ->
                        currentState.copy(
                            tareaId = it.tareaId,
                            descripcion = it.descripcion,
                            tiempo = it.tiempo,
                            validationErrors = emptyMap()
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(errorMessage = "Error al cargar la tarea: ${e.message}")
                }
            }
        }
    }

    private fun getTareas() {
        viewModelScope.launch {
            tareaRepository.getTareas().collectLatest { tareas ->
                _uiState.update {
                    it.copy(tareas = tareas)
                }
            }
        }
    }
}