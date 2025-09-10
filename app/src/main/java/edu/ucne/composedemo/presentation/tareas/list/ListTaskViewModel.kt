package edu.ucne.composedemo.presentation.tareas.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.composedemo.domain.tareas.usecase.DeleteTaskUseCase
import edu.ucne.composedemo.domain.tareas.usecase.ObserveTasksUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListTaskViewModel @Inject constructor(
    private val observeTasksUseCase: ObserveTasksUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(ListTaskUiState(isLoading = true))
    val state: StateFlow<ListTaskUiState> = _state.asStateFlow()

    init {
        onEvent(ListTaskUiEvent.Load)
    }

    fun onEvent(event: ListTaskUiEvent) {
        when (event) {
            ListTaskUiEvent.Load -> observe()
            is ListTaskUiEvent.Delete -> onDelete(event.id)
            ListTaskUiEvent.CreateNew -> _state.update { it.copy(navigateToCreate = true) }
            is ListTaskUiEvent.Edit -> _state.update { it.copy(navigateToEditId = event.id) }
            is ListTaskUiEvent.ShowMessage -> _state.update { it.copy(message = event.message) }
        }
    }

    private fun observe() {
        viewModelScope.launch {
            observeTasksUseCase().collectLatest { list ->
                _state.update { it.copy(isLoading = false, tasks = list, message = null) }
            }
        }
    }

    private fun onDelete(id: Int) {
        viewModelScope.launch {
            deleteTaskUseCase(id)
            onEvent(ListTaskUiEvent.ShowMessage("Eliminado"))
        }
    }

    fun onNavigationHandled() {
        _state.update { it.copy(navigateToCreate = false, navigateToEditId = null) }
    }
}