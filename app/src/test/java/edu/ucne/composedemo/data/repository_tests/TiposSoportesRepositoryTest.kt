package edu.ucne.composedemo.data.repository_tests

import app.cash.turbine.test
import com.google.common.truth.Truth
import edu.ucne.composedemo.data.remote.RemoteDataSource
import edu.ucne.composedemo.data.remote.Resource
import edu.ucne.composedemo.data.remote.dto.TiposSoportesDto
import edu.ucne.composedemo.data.repository.TiposSoportesRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class TiposSoportesRepositoryTest {
    @Test
    fun `Should return a flow of tiposSoportes`() = runTest {
        // Given
        val tiposSoportes = listOf(
            TiposSoportesDto(1, "TipoSoporte 1", 100),
            TiposSoportesDto(2, "TipoSoporte 2", 200),
            TiposSoportesDto(3, "TipoSoporte 3", 300)
        )
        val remoteDatasource = mockk<RemoteDataSource>()
        val repository = TiposSoportesRepository(remoteDatasource)

        coEvery { remoteDatasource.getTiposSoportes() } returns tiposSoportes

        // When
        repository.getTiposSoportes().test {
            //Then
            Truth.assertThat(awaitItem() is Resource.Loading).isTrue()

            Truth.assertThat(awaitItem().data).isEqualTo(tiposSoportes)

            cancelAndIgnoreRemainingEvents()
        }
    }
}