package edu.ucne.composedemo.presentation.tareas.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.composedemo.domain.tareas.model.Task
import edu.ucne.composedemo.domain.tareas.usecase.DeleteTaskUseCase
import edu.ucne.composedemo.domain.tareas.usecase.GetTaskUseCase
import edu.ucne.composedemo.domain.tareas.usecase.UpsertTaskUseCase
import edu.ucne.composedemo.domain.tareas.usecase.validateDescripcion
import edu.ucne.composedemo.domain.tareas.usecase.validateTiempo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditTaskViewModel @Inject constructor(
    private val getTaskUseCase: GetTaskUseCase,
    private val upsertTaskUseCase: UpsertTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(EditTaskUiState())
    val state: StateFlow<EditTaskUiState> = _state.asStateFlow()

    fun onEvent(event: EditTaskUiEvent) {
        when (event) {
            is EditTaskUiEvent.Load -> onLoad(event.id)
            is EditTaskUiEvent.DescripcionChanged -> _state.update {
                it.copy(descripcion = event.value, descripcionError = null)
            }

            is EditTaskUiEvent.TiempoChanged -> _state.update {
                it.copy(tiempo = event.value, tiempoError = null)
            }

            EditTaskUiEvent.Save -> onSave()
            EditTaskUiEvent.Delete -> onDelete()
        }
    }

    private fun onLoad(id: Int?) {
        if (id == null || id == 0) {
            _state.update { it.copy(isNew = true, tareaId = null) }
            return
        }
        viewModelScope.launch {
            val task = getTaskUseCase(id)
            if (task != null) {
                _state.update {
                    it.copy(
                        isNew = false,
                        tareaId = task.tareaId,
                        descripcion = task.descripcion,
                        tiempo = task.tiempo.toString()
                    )
                }
            }
        }
    }

    private fun onSave() {
        val descripcion = state.value.descripcion
        val descriptionValidation = validateDescripcion(descripcion)
        val t = validateTiempo(state.value.tiempo)
        if (!descriptionValidation.isValid || !t.isValid) {
            _state.update {
                it.copy(
                    descripcionError = descriptionValidation.error,
                    tiempoError = t.error
                )
            }
            return
        }
        viewModelScope.launch {
            _state.update { it.copy(isSaving = true) }
            val id = state.value.tareaId ?: 0
            val task = Task(
                tareaId = id,
                descripcion = descripcion,
                tiempo = state.value.tiempo.toInt()
            )
            val result = upsertTaskUseCase(task)
            result.onSuccess { newId ->
                _state.update { it.copy(isSaving = false, saved = true, tareaId = newId) }
            }.onFailure { e ->
                _state.update { it.copy(isSaving = false) }
            }
        }
    }

    private fun onDelete() {
        val id = state.value.tareaId ?: return
        viewModelScope.launch {
            _state.update { it.copy(isDeleting = true) }
            deleteTaskUseCase(id)
            _state.update { it.copy(isDeleting = false, deleted = true) }
        }
    }
}
