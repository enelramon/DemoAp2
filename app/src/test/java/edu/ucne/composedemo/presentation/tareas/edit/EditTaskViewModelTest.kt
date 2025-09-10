package edu.ucne.composedemo.presentation.tareas.edit

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import edu.ucne.composedemo.domain.tareas.model.Task
import edu.ucne.composedemo.domain.tareas.usecase.DeleteTaskUseCase
import edu.ucne.composedemo.domain.tareas.usecase.GetTaskUseCase
import edu.ucne.composedemo.domain.tareas.usecase.UpsertTaskUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class EditTaskViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    private lateinit var getTask: GetTaskUseCase
    private lateinit var upsertTask: UpsertTaskUseCase
    private lateinit var deleteTask: DeleteTaskUseCase

    @Before
    fun setUp() {
        kotlinx.coroutines.Dispatchers.setMain(dispatcher)
        getTask = mockk()
        upsertTask = mockk()
        deleteTask = mockk()
    }

    @After
    fun tearDown() {
        kotlinx.coroutines.Dispatchers.resetMain()
    }

    @Test
    fun load_withNullId_setsNewState() = runTest(dispatcher) {
        val vm = EditTaskViewModel(getTask, upsertTask, deleteTask)

        vm.onEvent(EditTaskUiEvent.Load(null))
        runCurrent()

        val s = vm.state.value
        assertThat(s.isNew).isTrue()
        assertThat(s.tareaId).isNull()
    }

    @Test
    fun load_withId_populatesFields() = runTest(dispatcher) {
        coEvery { getTask(7) } returns Task(tareaId = 7, descripcion = "Hola", tiempo = 9)
        val vm = EditTaskViewModel(getTask, upsertTask, deleteTask)

        vm.onEvent(EditTaskUiEvent.Load(7))
        runCurrent()

        val s = vm.state.value
        assertThat(s.isNew).isFalse()
        assertThat(s.tareaId).isEqualTo(7)
        assertThat(s.descripcion).isEqualTo("Hola")
        assertThat(s.tiempo).isEqualTo("9")
    }

    @Test
    fun save_withInvalidInputs_setsErrorsAndDoesNotSave() = runTest(dispatcher) {
        val vm = EditTaskViewModel(getTask, upsertTask, deleteTask)

        vm.onEvent(EditTaskUiEvent.DescripcionChanged(""))
        vm.onEvent(EditTaskUiEvent.TiempoChanged("abc"))
        vm.onEvent(EditTaskUiEvent.Save)
        runCurrent()

        val s = vm.state.value
        assertThat(s.descripcionError).isNotNull()
        assertThat(s.tiempoError).isNotNull()
        assertThat(s.saved).isFalse()
    }

    @Test
    fun save_withValidInputs_callsUpsert_andSetsSavedTrue() = runTest(dispatcher) {
        coEvery { upsertTask(any()) } returns Result.success(123)
        val vm = EditTaskViewModel(getTask, upsertTask, deleteTask)

        vm.onEvent(EditTaskUiEvent.DescripcionChanged("Valida"))
        vm.onEvent(EditTaskUiEvent.TiempoChanged("5"))

        vm.onEvent(EditTaskUiEvent.Save)
        runCurrent()

        val s = vm.state.value
        assertThat(s.isSaving).isFalse()
        assertThat(s.saved).isTrue()
        assertThat(s.tareaId).isEqualTo(123)
    }

    @Test
    fun delete_whenHasId_callsUseCase_andFlagsDeleted() = runTest(dispatcher) {
        coEvery { deleteTask(9) } returns Unit
        val vm = EditTaskViewModel(getTask, upsertTask, deleteTask)

        // seed state with an existing id
        vm.onEvent(EditTaskUiEvent.Load(null))
        vm.onEvent(EditTaskUiEvent.DescripcionChanged("X"))
        vm.onEvent(EditTaskUiEvent.TiempoChanged("1"))
        // manually set id by simulating a previous save result
        vm.onEvent(EditTaskUiEvent.Save) // will be invalid, but we set id directly next line
        runCurrent()
        // force id into state via reflection is heavy; instead, call onLoad with valid task
        coEvery { getTask(9) } returns Task(9, "Y", 2)
        vm.onEvent(EditTaskUiEvent.Load(9))
        runCurrent()

        vm.onEvent(EditTaskUiEvent.Delete)
        runCurrent()

        coVerify { deleteTask(9) }
        val s = vm.state.value
        assertThat(s.isDeleting).isFalse()
        assertThat(s.deleted).isTrue()
    }
}
