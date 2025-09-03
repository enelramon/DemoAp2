package edu.ucne.composedemo.domain.tareas.usecase

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import edu.ucne.composedemo.domain.tareas.model.Task
import edu.ucne.composedemo.domain.tareas.repository.TaskRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class ObserveTasksUseCaseTest {
    private lateinit var repository: TaskRepository
    private lateinit var useCase: ObserveTasksUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = ObserveTasksUseCase(repository)
    }

    @Test
    fun `emits lists from repository`() = runTest {
        val shared = MutableSharedFlow<List<Task>>()
        every { repository.observeTasks() } returns shared

        val job = launch {
            useCase().test {
                shared.emit(listOf(Task(1, "A", 1)))
                assertThat(awaitItem()).containsExactly(Task(1, "A", 1))

                shared.emit(listOf(Task(2, "B", 2), Task(3, "C", 3)))
                assertThat(awaitItem()).containsExactly(Task(2, "B", 2), Task(3, "C", 3))

                cancelAndIgnoreRemainingEvents()
            }
        }
        job.join()
    }
}
