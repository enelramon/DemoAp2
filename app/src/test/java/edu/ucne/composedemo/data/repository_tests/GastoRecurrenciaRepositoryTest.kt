package edu.ucne.composedemo.data.repository_tests

import app.cash.turbine.test
import com.google.common.truth.Truth
import edu.ucne.composedemo.data.remote.RemoteDataSource
import edu.ucne.composedemo.data.remote.Resource
import edu.ucne.composedemo.data.remote.dto.GastoRecurrenciaDto
import edu.ucne.composedemo.data.repository.GastoRecurrenciaRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

class GastoRecurrenciaRepositoryTest {

    @Test
    fun `Should return a flow of gastos recurrencias`() = runTest {
        // Given
        val gastosRecurrencias = listOf(
            GastoRecurrenciaDto(1, 1, 2, 1, 2000.0, "2024-06-02"),
            GastoRecurrenciaDto(2, 2, 2, 2,2000.0, "2024-06-02")
        )
        val remoteDataSource = mockk<RemoteDataSource>()
        val repository = GastoRecurrenciaRepository(remoteDataSource)

        coEvery { remoteDataSource.getGastosRecurencias() } returns gastosRecurrencias

        // When
        repository.getGastostoRecurrencias().test {
            // Then
            Truth.assertThat(awaitItem() is Resource.Loading).isTrue()
            Truth.assertThat(awaitItem().data).isEqualTo(gastosRecurrencias)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Should return HttpException error when fetching gastos recurrencias`() = runTest {
        // Given
        val remoteDataSource = mockk<RemoteDataSource>()
        val repository = GastoRecurrenciaRepository(remoteDataSource)

        val httpException = HttpException(Response.error<Any>(
            500, "Internal Server Error".toResponseBody(null)
        ))

        coEvery { remoteDataSource.getGastosRecurencias() } throws httpException

        // When
        repository.getGastostoRecurrencias().test {
            // Then
            Truth.assertThat(awaitItem() is Resource.Loading).isTrue()

            val error = awaitItem() as Resource.Error
            Truth.assertThat(error.message).isEqualTo("Error de Internet:Internal Server Error ")

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Should return Exception error when fetching gastos recurrencias`() = runTest {
        // Given
        val remoteDataSource = mockk<RemoteDataSource>()
        val repository = GastoRecurrenciaRepository(remoteDataSource)

        val exception = Exception("Connection Lost")

        coEvery { remoteDataSource.getGastosRecurencias() } throws exception

        // When
        repository.getGastostoRecurrencias().test {
            // Then
            Truth.assertThat(awaitItem() is Resource.Loading).isTrue()

            val error = awaitItem() as Resource.Error
            Truth.assertThat(error.message).isEqualTo("Error Connection Lost")

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Should successfully create a gasto recurrencia`() = runTest {
        // Given
        val gastoRecurrencia = GastoRecurrenciaDto(1, 1, 2, 1, 2000.0, "2024-06-02")
        val remoteDataSource = mockk<RemoteDataSource>()
        val repository = GastoRecurrenciaRepository(remoteDataSource)

        coEvery { remoteDataSource.createGastoRecurencia(gastoRecurrencia) } returns gastoRecurrencia

        // When
        repository.createGastoRecurencia(gastoRecurrencia).test {
            // Then
            Truth.assertThat(awaitItem() is Resource.Loading).isTrue()
            Truth.assertThat(awaitItem().data).isEqualTo(gastoRecurrencia)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Should successfully delete a gasto recurrencia`() = runTest {
        // Arrange
        val gastoId = 1
        val remoteDataSource = mockk<RemoteDataSource>()
        val repository = GastoRecurrenciaRepository(remoteDataSource)

        coEvery { remoteDataSource.deleteGastoRecurencia(gastoId) } returns Response.success(Unit)

        // Act
        repository.deleteGastoRecurencia(gastoId).test {
            // Assert
            Truth.assertThat(awaitItem() is Resource.Loading).isTrue()
            val result = awaitItem() as Resource.Success
            Truth.assertThat(result.data).isTrue()

            cancelAndIgnoreRemainingEvents()
        }
    }
}
