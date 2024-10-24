package edu.ucne.composedemo.data.repository_tests

import app.cash.turbine.test
import com.google.common.truth.Truth
import edu.ucne.composedemo.data.remote.RemoteDataSource
import edu.ucne.composedemo.data.remote.Resource
import edu.ucne.composedemo.data.remote.dto.SuplidorGastoDto
import edu.ucne.composedemo.data.repository.SuplidorGastoRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class SuplidorGastoRepositoryTest {
    @Test
    fun `Should return a flow of suplidores gastos`() = runTest {
        // Given
        val suplidoresGastos = listOf(
            SuplidorGastoDto(1, "Braylin ", "Calle mencia","8297505905", "8099089090","ajbsba17727","bsjajb18282", 1, 1, "n@Gmail.com" ),
            SuplidorGastoDto(1, "Papo ", "Calle marte","8297501234", "809908123","ajbsba17727","a12a12", 1, 1, "p@Gmail.com" ),
        )
        val remoteDataSource = mockk<RemoteDataSource>()
        val repository = SuplidorGastoRepository(remoteDataSource)

        coEvery { remoteDataSource.getSuplidoresGastos() } returns suplidoresGastos

        // When
        repository.getSuplidoresGastos().test {
            // Then
            Truth.assertThat(awaitItem() is Resource.Loading).isTrue()

            Truth.assertThat(awaitItem().data).isEqualTo(suplidoresGastos)

            cancelAndIgnoreRemainingEvents()
        }
    }
}