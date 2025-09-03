package edu.ucne.composedemo.data.repository_tests

import app.cash.turbine.test
import com.google.common.truth.Truth
import edu.ucne.composedemo.data.remote.RemoteDataSource
import edu.ucne.composedemo.data.remote.Resource
import edu.ucne.composedemo.data.remote.dto.SistemaDto
import edu.ucne.composedemo.data.repository.SistemaRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class SistemaRepositoryTest {

    @Test
    fun `Should return a flow of sistemas`() = runTest {
        // Given
        val sistemas = listOf(
            SistemaDto(1, "Sistema 1"),
            SistemaDto(2, "Sistema 2"),
            SistemaDto(3, "Sistema 3")
        )
        val remoteDataSource = mockk<RemoteDataSource>()
        val repository = SistemaRepository(remoteDataSource)

        coEvery { remoteDataSource.getSistemas() } returns sistemas

        // When
        repository.getSistemas().test {
            // Then
            Truth.assertThat(awaitItem() is Resource.Loading).isTrue()

            Truth.assertThat(awaitItem().data).isEqualTo(sistemas)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun update() {
    }
}