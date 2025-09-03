package edu.ucne.composedemo.domain.usecase_tests

import app.cash.turbine.test
import com.google.common.truth.Truth
import edu.ucne.composedemo.data.remote.Resource
import edu.ucne.composedemo.data.remote.dto.ClienteDto
import edu.ucne.composedemo.data.repository.ClienteRepository
import edu.ucne.composedemo.domain.usecase.ClienteUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test

class ClienteUseCaseTest {

    @Test
    fun `Should return a flow of clientes`() = runTest {
        // Given
        val clientes = listOf(
            ClienteDto(1.0, "Cliente 1", "Empresa 1", "Direccion 1", "123456789", "987654321", "123456789", false, 1),
            ClienteDto(2.0, "Cliente 2", "Empresa 2", "Direccion 2", "123456789", "987654321", "123456789", true, 2)
        )
        val clienteRepository = mockk<ClienteRepository>()
        val clienteUseCase = ClienteUseCase(clienteRepository)

        coEvery { clienteRepository.getClientes() } returns flowOf(Resource.Success(clientes))

        // When
        clienteUseCase.getClientes().test {
            // Then
            val result = awaitItem()
            Truth.assertThat(result.data).isEqualTo(clientes)
            awaitComplete()
        }
    }
}