package com.tareasapp.feature.list

import com.tareasapp.domain.model.Task

sealed interface ListIntent {
	data object Load : ListIntent
	data class Delete(val id: Int) : ListIntent
	data object CreateNew : ListIntent
	data class Edit(val id: Int) : ListIntent
}

data class ListState(
	val isLoading: Boolean = false,
	val tasks: List<Task> = emptyList(),
	val message: String? = null,
	val navigateToCreate: Boolean = false,
	val navigateToEditId: Int? = null
)

sealed interface ListEffect {
	data class ShowMessage(val message: String) : ListEffect
}