package edu.ucne.composedemo.data.repository_tests

import app.cash.turbine.test
import com.google.common.truth.Truth
import edu.ucne.composedemo.data.remote.RemoteDataSource
import edu.ucne.composedemo.data.remote.Resource
import edu.ucne.composedemo.data.remote.dto.ClienteDto
import edu.ucne.composedemo.data.repository.ClienteRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

class ClienteRepositoryTest {

    @Test
    fun `Should return a flow of clientes`() = runTest {
        // Given
        val clientes = listOf(
            ClienteDto(1, "Cliente 1", "Empresa 1",
                "Direccion 1", "123-456", "987-654",
                "RNC1", true, 1),
            ClienteDto(2, "Cliente 2", "Empresa 2",
                "Direccion 2", "456-789", "321-654",
                "RNC2", false, 2)
        )
        val remoteDataSource = mockk<RemoteDataSource>()
        val repository = ClienteRepository(remoteDataSource)

        coEvery { remoteDataSource.getClientes() } returns clientes

        // When
        repository.getClientes().test {
            // Then
            Truth.assertThat(awaitItem() is Resource.Loading).isTrue()

            Truth.assertThat(awaitItem().data).isEqualTo(clientes)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Should return HttpException error`() = runTest {
        // Given
        val remoteDataSource = mockk<RemoteDataSource>()
        val repository = ClienteRepository(remoteDataSource)

        val httpException = HttpException(Response.error<Any>
            (500, "Internal Server Error".toResponseBody(null)))

        coEvery { remoteDataSource.getClientes() } throws httpException

        // When
        repository.getClientes().test {
            // Then
            Truth.assertThat(awaitItem() is Resource.Loading).isTrue()

            val error = awaitItem() as Resource.Error
            Truth.assertThat(error.message).isEqualTo("Error de conexion Internal Server Error")

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Should return Exception error`() = runTest {
        // Given
        val remoteDataSource = mockk<RemoteDataSource>()
        val repository = ClienteRepository(remoteDataSource)

        val Exception = Exception("The Conexion Has Been Lost")

        coEvery { remoteDataSource.getClientes() } throws Exception

        // When
        repository.getClientes().test {
            // Then
            Truth.assertThat(awaitItem() is Resource.Loading).isTrue()

            val error = awaitItem() as Resource.Error
            Truth.assertThat(error.message).isEqualTo("Error The Conexion Has Been Lost")

            cancelAndIgnoreRemainingEvents()
        }
    }
}