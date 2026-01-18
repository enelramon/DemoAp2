package edu.ucne.composedemo.presentation.tareas.edit

import androidx.lifecycle.SavedStateHandle
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
    private val deleteTaskUseCase: DeleteTaskUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val taskId: Int = savedStateHandle["taskId"] ?: 0

    private val _state = MutableStateFlow(EditTaskUiState())
    val state: StateFlow<EditTaskUiState> = _state.asStateFlow()

    init {
        loadTask(taskId)
    }

    fun onEvent(event: EditTaskUiEvent) {
        when (event) {
            is EditTaskUiEvent.Load -> loadTask(event.id)
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

    private fun loadTask(id: Int?) {
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
            } else {
                _state.update { it.copy(isNew = true, tareaId = null) }
            }
        }
    }

    private fun onSave() {
        val descripcion = state.value.descripcion
        val descripcionValidation = validateDescripcion(descripcion)
        val tiempoValidation = validateTiempo(state.value.tiempo)

        if (!descripcionValidation.isValid || !tiempoValidation.isValid) {
            _state.update {
                it.copy(
                    descripcionError = descripcionValidation.error,
                    tiempoError = tiempoValidation.error
                )
            }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isSaving = true) }

            val task = Task(
                tareaId = state.value.tareaId ?: 0,
                descripcion = descripcion,
                tiempo = state.value.tiempo.toInt()
            )

            val result = upsertTaskUseCase(task)
            result.onSuccess { newId ->
                _state.update {
                    it.copy(
                        isSaving = false,
                        saved = true,
                        tareaId = newId,
                        isNew = false
                    )
                }
            }.onFailure {
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
