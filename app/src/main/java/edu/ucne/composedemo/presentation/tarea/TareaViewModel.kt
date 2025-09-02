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
    private val repository: TareaRepository,
) : ViewModel() {
    private val uiStatePrivado = MutableStateFlow(TareaUiState())
    val uiState = uiStatePrivado.asStateFlow()

    companion object {
        private const val REQUIRED_FIELD = "Este campo es obligatorio *"
        private const val MIN_TIEMPO_NUMBER = 0
        private const val MIN_TIEMPO = "El tiempo debe ser mayor a 0 *"
        private const val MAX_DESCRIPCION_LENGTH = 25
        private const val MAX_DESCRIPCION = "La descripción no puede tener más de $MAX_DESCRIPCION_LENGTH caracteres *"
        private const val DESCRIPCION_EXISTENTE = "Ya existe una tarea con esta descripción *"
    }

    init {
        getTareas()
    }

    fun onEvent(event: TareaEvent) {
        when (event) {
            is TareaEvent.TareaChange -> onTareaChange(event.tareaId)
            is TareaEvent.DescripcionChange -> onDecripcionChange(event.descripcion)
            is TareaEvent.TiempoChange -> onTiempoChange(event.tiempo)
            TareaEvent.GoBackAfterSave -> onGoBackAfterSave()

            is TareaEvent.Delete -> delete(event.tarea)
            TareaEvent.Save -> save()
            TareaEvent.LimpiarTodo -> limpiarTodosLosCampos()
        }
    }

    private fun onGoBackAfterSave() {
        viewModelScope.launch {
            uiStatePrivado.update {
                it.copy(guardado = false)
            }
        }
    }

    private fun limpiarTodosLosCampos() {
        viewModelScope.launch {
            uiStatePrivado.update {
                it.copy(
                    errorMessageTiempo = "",
                    errorMessageDescripcion = "",
                    tiempo = null,
                    descripcion = ""
                )
            }
        }
    }

    private fun getTareas() {
        viewModelScope.launch {
            repository.getAll().collect { lista ->
                uiStatePrivado.update {
                    it.copy(listaTareas = lista)
                }
            }
        }
    }

    private fun TareaUiState.toEntity() =
        TareaEntity(
            tareaId = tareaId,
            descripcion = descripcion ?: "",
            tiempo = tiempo ?: 0
        )

    private fun save() {
        viewModelScope.launch {
            val descripcion = uiStatePrivado.value.descripcion.orEmpty()
            val tiempo = uiStatePrivado.value.tiempo

            var errorDescripcion: String? = null
            var errorTiempo: String? = null

            when {
                descripcion.isBlank() ->
                    errorDescripcion = REQUIRED_FIELD

                descripcion.length > MAX_DESCRIPCION_LENGTH ->
                    errorDescripcion = MAX_DESCRIPCION

                uiStatePrivado.value.listaTareas.any {
                    it.descripcion.equals(descripcion, ignoreCase = true) &&
                            it.tareaId != uiStatePrivado.value.tareaId
                } -> errorDescripcion = DESCRIPCION_EXISTENTE
            }

            when {
                tiempo == null ->
                    errorTiempo = REQUIRED_FIELD

                tiempo <= MIN_TIEMPO_NUMBER ->
                    errorTiempo = MIN_TIEMPO
            }

            uiStatePrivado.update {
                it.copy(
                    errorMessageDescripcion = errorDescripcion,
                    errorMessageTiempo = errorTiempo
                )
            }

            if (errorDescripcion != null || errorTiempo != null) return@launch

            uiStatePrivado.update {
                it.copy(guardado = true)
            }

            repository.save(uiStatePrivado.value.toEntity())
            limpiarTodosLosCampos()
        }
    }

    fun selectedTarea(tareaId: Int) {
        viewModelScope.launch {
            if (tareaId > 0) {
                val tarea = repository.find(tareaId)
                uiStatePrivado.update {
                    it.copy(
                        tareaId = tarea.tareaId,
                        descripcion = tarea.descripcion,
                        tiempo = tarea.tiempo,
                    )
                }
            }
        }
    }

    private fun delete(tarea: TareaEntity) {
        viewModelScope.launch {
            repository.delete(tarea)
            getTareas()
        }
    }

    private fun onTiempoChange(tiempo: Int?) {
        viewModelScope.launch {
            uiStatePrivado.update {
                it.copy(
                    tiempo = tiempo,
                    errorMessageTiempo = ""
                )
            }
        }
    }

    private fun onDecripcionChange(descripcion: String) {
        viewModelScope.launch {
            uiStatePrivado.update {
                it.copy(
                    descripcion = descripcion,
                    errorMessageDescripcion = ""
                )
            }
        }
    }


    private fun onTareaChange(id: Int?) {
        viewModelScope.launch {
            uiStatePrivado.update {
                it.copy(tareaId = id)
            }
        }
    }
}