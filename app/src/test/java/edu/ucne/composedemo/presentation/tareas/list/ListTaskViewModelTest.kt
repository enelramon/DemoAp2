package edu.ucne.composedemo.presentation.tareas.list

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import edu.ucne.composedemo.domain.tareas.model.Task
import edu.ucne.composedemo.domain.tareas.usecase.DeleteTaskUseCase
import edu.ucne.composedemo.domain.tareas.usecase.ObserveTasksUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ListTaskViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    private lateinit var observeTasks: ObserveTasksUseCase
    private lateinit var deleteTask: DeleteTaskUseCase

    @Before
    fun setUp() {
        kotlinx.coroutines.Dispatchers.setMain(dispatcher)
        observeTasks = mockk()
        deleteTask = mockk()
    }

    @Test
    fun delete_callsUseCase_andShowsMessage() = runTest(dispatcher) {
        val shared = MutableSharedFlow<List<Task>>(replay = 1)
        shared.emit(emptyList())
        every { observeTasks() } returns shared
        coEvery { deleteTask(5) } returns Unit

        val vm = ListTaskViewModel(observeTasks, deleteTask)
        runCurrent()

        vm.onEvent(ListTaskUiEvent.Delete(5))
        runCurrent()

        coVerify { deleteTask(5) }
        assertThat(vm.state.value.message).isEqualTo("Eliminado")
    }

    @Test
    fun navigationFlags_toggleAsExpected() = runTest(dispatcher) {
        val shared = MutableSharedFlow<List<Task>>(replay = 1)
        shared.emit(emptyList())
        every { observeTasks() } returns shared
        val vm = ListTaskViewModel(observeTasks, deleteTask)
        runCurrent()

        vm.onEvent(ListTaskUiEvent.CreateNew)
        assertThat(vm.state.value.navigateToCreate).isTrue()

        vm.onEvent(ListTaskUiEvent.Edit(10))
        assertThat(vm.state.value.navigateToEditId).isEqualTo(10)

        vm.onNavigationHandled()
        assertThat(vm.state.value.navigateToCreate).isFalse()
        assertThat(vm.state.value.navigateToEditId).isNull()
    }
}
