package edu.ucne.composedemo.data.tareas.repository

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import edu.ucne.composedemo.data.tareas.local.TaskDao
import edu.ucne.composedemo.data.tareas.local.TaskEntity
import edu.ucne.composedemo.domain.tareas.model.Task
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TaskRepositoryImplTest {

    private lateinit var dao: TaskDao
    private lateinit var repository: TaskRepositoryImpl

    @Before
    fun setUp() {
        dao = mockk(relaxed = true)
        repository = TaskRepositoryImpl(dao)
    }

    @Test
    fun observeTasks_mapsEntitiesToDomain() = runTest {
        val shared = MutableSharedFlow<List<TaskEntity>>()
        every { dao.observeAll() } returns shared

        val job = launch {
            repository.observeTasks().test {
                // First emission
                shared.emit(listOf(TaskEntity(tareaId = 1, descripcion = "A", tiempo = 5)))
                val first = awaitItem()
                assertThat(first).containsExactly(Task(tareaId = 1, descripcion = "A", tiempo = 5))

                // Second emission (multiple items)
                shared.emit(
                    listOf(
                        TaskEntity(tareaId = 2, descripcion = "B", tiempo = 3),
                        TaskEntity(tareaId = 3, descripcion = "C", tiempo = 4)
                    )
                )
                val second = awaitItem()
                assertThat(second).containsExactly(
                    Task(tareaId = 2, descripcion = "B", tiempo = 3),
                    Task(tareaId = 3, descripcion = "C", tiempo = 4)
                )

                cancelAndIgnoreRemainingEvents()
            }
        }
        job.join()
    }

    @Test
    fun getTask_returnsMappedDomainModel_whenEntityExists() = runTest {
        coEvery { dao.getById(5) } returns TaskEntity(tareaId = 5, descripcion = "X", tiempo = 7)

        val result = repository.getTask(5)

        assertThat(result).isEqualTo(Task(tareaId = 5, descripcion = "X", tiempo = 7))
    }

    @Test
    fun getTask_returnsNull_whenEntityMissing() = runTest {
        coEvery { dao.getById(42) } returns null

        val result = repository.getTask(42)

        assertThat(result).isNull()
    }

    @Test
    fun upsert_callsDaoWithMappedEntity_andReturnsTaskId() = runTest {
        coEvery { dao.upsert(any()) } just runs
        val task = Task(tareaId = 10, descripcion = "Nueva", tiempo = 1)

        val returnedId = repository.upsert(task)

        assertThat(returnedId).isEqualTo(10)
        coVerify { dao.upsert(TaskEntity(tareaId = 10, descripcion = "Nueva", tiempo = 1)) }
    }

    @Test
    fun delete_callsDaoDeleteById() = runTest {
        coEvery { dao.deleteById(12) } just runs

        repository.delete(12)

        coVerify { dao.deleteById(12) }
    }
}
