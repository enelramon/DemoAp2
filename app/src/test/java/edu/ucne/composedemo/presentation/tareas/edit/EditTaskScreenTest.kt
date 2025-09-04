/*
package edu.ucne.composedemo.presentation.tareas.edit

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import org.junit.Rule
import org.junit.Test

class EditTaskScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun userCanTypeAndSave() {
        var lastEvent: EditTaskUiEvent? = null
        composeRule.setContent {
            EditTaskBody(
                state = EditTaskUiState(isNew = true),
                onEvent = { lastEvent = it }
            )
        }

        composeRule.onNodeWithTag("input_descripcion").assertIsDisplayed().performTextInput("Tarea X")
        composeRule.onNodeWithTag("input_tiempo").assertIsDisplayed().performTextInput("15")
        composeRule.onNodeWithTag("btn_guardar").performClick()

        assert(lastEvent is EditTaskUiEvent.Save)
    }

    @Test
    fun showDeleteWhenEditing_andClickEmitsDelete() {
        var lastEvent: EditTaskUiEvent? = null
        composeRule.setContent {
            EditTaskBody(
                state = EditTaskUiState(isNew = false),
                onEvent = { lastEvent = it }
            )
        }
        composeRule.onNodeWithTag("btn_eliminar").assertIsDisplayed().performClick()
        assert(lastEvent is EditTaskUiEvent.Delete)
    }
}
*/
