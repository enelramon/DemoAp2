package com.tareasapp.feature.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tareasapp.domain.usecase.DeleteTask
import com.tareasapp.domain.usecase.ObserveTasks
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ListViewModel(
	private val observeTasks: ObserveTasks,
	private val deleteTask: DeleteTask
) : ViewModel() {
	private val _state = MutableStateFlow(ListState(isLoading = true))
	val state: StateFlow<ListState> = _state.asStateFlow()

	private val _effects = Channel<ListEffect>(Channel.BUFFERED)
	val effects = _effects

	init {
		dispatch(ListIntent.Load)
	}

	fun dispatch(intent: ListIntent) {
		when (intent) {
			ListIntent.Load -> observe()
			is ListIntent.Delete -> onDelete(intent.id)
			ListIntent.CreateNew -> _state.update { it.copy(navigateToCreate = true) }
			is ListIntent.Edit -> _state.update { it.copy(navigateToEditId = intent.id) }
		}
	}

	private fun observe() {
		viewModelScope.launch {
			observeTasks().collectLatest { list ->
				_state.update { it.copy(isLoading = false, tasks = list, message = null) }
			}
		}
	}

	private fun onDelete(id: Int) {
		viewModelScope.launch {
			deleteTask(id)
			_effects.send(ListEffect.ShowMessage("Eliminado"))
		}
	}

	fun onNavigationHandled() {
		_state.update { it.copy(navigateToCreate = false, navigateToEditId = null) }
	}
}