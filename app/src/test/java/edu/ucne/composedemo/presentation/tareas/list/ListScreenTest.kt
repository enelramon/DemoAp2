package edu.ucne.composedemo.presentation.tareas.list

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import edu.ucne.composedemo.domain.tareas.model.Task
import org.junit.Rule
import org.junit.Test

class ListScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun showsLoadingIndicator_whenLoading() {
        val state = ListTaskUiState(isLoading = true)
        composeRule.setContent {
            ListTaskBody(state = state, onEvent = { })
        }
        composeRule.onNodeWithTag("loading").assertIsDisplayed()
    }

    @Test
    fun rendersTasks_andInvokesDelete() {
        val tasks = listOf(
            Task(tareaId = 1, descripcion = "A", tiempo = 1),
            Task(tareaId = 2, descripcion = "B", tiempo = 2)
        )
        var deletedId: Int? = null

        composeRule.setContent {
            ListTaskBody(
                state = ListTaskUiState(tasks = tasks),
                onEvent = {
                    if (it is ListTaskUiEvent.Delete) deletedId = it.id
                }
            )
        }

        composeRule.onNodeWithTag("task_list").assertIsDisplayed()
        composeRule.onAllNodesWithTag("task_card_1").assertCountEquals(1)
        composeRule.onAllNodesWithTag("task_card_2").assertCountEquals(1)

        composeRule.onNodeWithTag("delete_button_2").performClick()
        assert(deletedId == 2)
    }

    @Test
    fun fabClick_emitsCreateEvent() {
        var created = false
        composeRule.setContent {
            ListTaskBody(state = ListTaskUiState(), onEvent = { if (it is ListTaskUiEvent.CreateNew) created = true })
        }
        composeRule.onNodeWithTag("fab_add").performClick()
        assert(created)
    }
}
