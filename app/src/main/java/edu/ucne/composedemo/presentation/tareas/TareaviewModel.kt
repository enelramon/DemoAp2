package edu.ucne.composedemo.presentation.tareas

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.composedemo.data.local.entities.TareaEntity
import edu.ucne.composedemo.data.repository.TareaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TareaviewModel @Inject constructor(
    private val tareaRespository: TareaRepository
) : ViewModel() {

    private val _tarealist = MutableStateFlow<List<TareaEntity>>(emptyList())
    val tarealist: StateFlow<List<TareaEntity>> = _tarealist.asStateFlow()

    init {
        loadTareas()
    }

    fun loadTareas() {
        viewModelScope.launch {
            tareaRespository.getAll().collect { lista ->
                Log.d("TareaviewModel", "Lista recibida: ${lista.size}")
                _tarealist.value = lista
            }
        }
    }

    fun agregarTarea(descripcion: String, tiempo: Int, id: Int? = null) {
        val tarea = TareaEntity(
            tareaid = id ?: 0,
            descripcion = descripcion,
            tiempo = tiempo
        )
        saveTarea(tarea)
    }

    private fun saveTarea(tarea: TareaEntity) {
        viewModelScope.launch {
            tareaRespository.save(tarea)
            loadTareas()
        }
    }

    fun deleteTarea(tarea: TareaEntity) {
        viewModelScope.launch {
            tareaRespository.delete(tarea)
            loadTareas()
        }
    }

    fun getTareaById(id: Int): TareaEntity? {
        return _tarealist.value.find { it.tareaid == id }
    }

    fun validarTarea(descripcion: String, tiempo: String): Pair<Boolean, Map<String, String>> {
        val errores = mutableMapOf<String, String>()

        if (descripcion.isBlank()) errores["descripcion"] = "La descripción no puede estar vacía"
        val tiempoInt = tiempo.toIntOrNull()
        if (tiempoInt == null || tiempoInt <= 0) errores["tiempo"] = "El tiempo debe ser un número mayor que 0"

        return Pair(errores.isEmpty(), errores)
    }
}