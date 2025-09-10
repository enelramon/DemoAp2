package edu.ucne.composedemo.domain.tareas.usecase

import com.google.common.truth.Truth.assertThat
import edu.ucne.composedemo.domain.tareas.model.Task
import edu.ucne.composedemo.domain.tareas.repository.TaskRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetTaskUseCaseTest {
    private lateinit var repository: TaskRepository
    private lateinit var useCase: GetTaskUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = GetTaskUseCase(repository)
    }

    @Test
    fun `returns task when repository finds it`() = runTest {
        coEvery { repository.getTask(1) } returns Task(1, "Desc", 10)

        val result = useCase(1)

        assertThat(result).isEqualTo(Task(1, "Desc", 10))
    }

    @Test
    fun `returns null when repository returns null`() = runTest {
        coEvery { repository.getTask(999) } returns null

        val result = useCase(999)

        assertThat(result).isNull()
    }
}
