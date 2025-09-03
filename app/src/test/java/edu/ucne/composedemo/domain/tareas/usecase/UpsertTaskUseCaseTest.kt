package edu.ucne.composedemo.domain.tareas.usecase

import com.google.common.truth.Truth.assertThat
import edu.ucne.composedemo.domain.tareas.model.Task
import edu.ucne.composedemo.domain.tareas.repository.TaskRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class UpsertTaskUseCaseTest {
    private lateinit var repository: TaskRepository
    private lateinit var useCase: UpsertTaskUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = UpsertTaskUseCase(repository)
    }

    @Test
    fun `fails when descripcion is blank`() = runTest {
        val result = useCase(Task(descripcion = "", tiempo = 5))

        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()).isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun `fails when descripcion is too short`() = runTest {
        val result = useCase(Task(descripcion = "ab", tiempo = 5))

        assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `fails when tiempo is blank or non numeric`() = runTest {
        // simulate non numeric via negative or zero by constructing Task with tiempo = 0
        val result = useCase(Task(descripcion = "Valida", tiempo = 0))

        assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `succeeds and returns id when repository upsert works`() = runTest {
        coEvery { repository.upsert(any()) } returns 7
        val result = useCase(Task(tareaId = 7, descripcion = "Valida", tiempo = 3))

        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()).isEqualTo(7)
    }
}
