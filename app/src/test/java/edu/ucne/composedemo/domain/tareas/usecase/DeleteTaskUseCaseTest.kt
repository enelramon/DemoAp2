package edu.ucne.composedemo.domain.tareas.usecase

import edu.ucne.composedemo.domain.tareas.repository.TaskRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class DeleteTaskUseCaseTest {
    private lateinit var repository: TaskRepository
    private lateinit var useCase: DeleteTaskUseCase

    @Before
    fun setup() {
        repository = mockk(relaxed = true)
        useCase = DeleteTaskUseCase(repository)
    }

    @Test
    fun `calls repository delete with id`() = runTest {
        coEvery { repository.delete(5) } just runs

        useCase(5)

        coVerify { repository.delete(5) }
    }
}
