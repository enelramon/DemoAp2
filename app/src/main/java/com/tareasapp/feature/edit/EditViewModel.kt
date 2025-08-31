package com.tareasapp.feature.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tareasapp.domain.model.Task
import com.tareasapp.domain.usecase.DeleteTask
import com.tareasapp.domain.usecase.GetTask
import com.tareasapp.domain.usecase.UpsertTask
import com.tareasapp.domain.usecase.validateDescripcion
import com.tareasapp.domain.usecase.validateTiempo
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EditViewModel(
	private val getTask: GetTask,
	private val upsertTask: UpsertTask,
	private val deleteTask: DeleteTask
) : ViewModel() {
	private val _state = MutableStateFlow(EditState())
	val state: StateFlow<EditState> = _state.asStateFlow()

	private val _effects = Channel<EditEffect>(Channel.BUFFERED)
	val effects = _effects

	fun dispatch(intent: EditIntent) {
		when (intent) {
			is EditIntent.Load -> onLoad(intent.id)
			is EditIntent.DescripcionChanged -> _state.update { it.copy(descripcion = intent.value, descripcionError = null) }
			is EditIntent.TiempoChanged -> _state.update { it.copy(tiempo = intent.value, tiempoError = null) }
			EditIntent.Save -> onSave()
			EditIntent.Delete -> onDelete()
		}
	}

	private fun onLoad(id: Int?) {
		if (id == null || id == 0) {
			_state.update { it.copy(isNew = true, tareaId = null) }
			return
		}
		viewModelScope.launch {
			val task = getTask(id)
			if (task != null) {
				_state.update { it.copy(
					isNew = false,
					tareaId = task.tareaId,
					descripcion = task.descripcion,
					tiempo = task.tiempo.toString()
				) }
			}
		}
	}

	private fun onSave() {
		val descripcion = state.value.descripcion
		val tiempoStr = state.value.tiempo
		val d = validateDescripcion(descripcion)
		val t = validateTiempo(tiempoStr)
		if (!d.isValid || !t.isValid) {
			_state.update { it.copy(
				descripcionError = d.error,
				tiempoError = t.error
			) }
			return
		}
		viewModelScope.launch {
			_state.update { it.copy(isSaving = true) }
			val id = state.value.tareaId ?: 0
			val task = Task(id, descripcion, tiempoStr.toInt())
			val result = upsertTask(task)
			result.onSuccess { newId ->
				_state.update { it.copy(isSaving = false, saved = true, tareaId = newId) }
				_effects.send(EditEffect.ShowMessage("Guardado"))
			}.onFailure { e ->
				_state.update { it.copy(isSaving = false) }
				_effects.send(EditEffect.ShowMessage(e.message ?: "Error al guardar"))
			}
		}
	}

	private fun onDelete() {
		val id = state.value.tareaId ?: return
		viewModelScope.launch {
			_state.update { it.copy(isDeleting = true) }
			deleteTask(id)
			_state.update { it.copy(isDeleting = false, deleted = true) }
			_effects.send(EditEffect.ShowMessage("Eliminado"))
		}
	}
}