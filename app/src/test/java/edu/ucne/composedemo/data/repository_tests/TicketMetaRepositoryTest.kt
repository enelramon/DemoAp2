package edu.ucne.composedemo.data.repository_tests

import app.cash.turbine.test
import com.google.common.truth.Truth
import edu.ucne.composedemo.data.remote.RemoteDataSource
import edu.ucne.composedemo.data.remote.Resource
import edu.ucne.composedemo.data.remote.dto.TicketMetaResponseDto
import edu.ucne.composedemo.data.repository.TicketMetaRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class TicketMetaRepositoryTest {

    @Test
    fun `should return a flow of ticketmetas`() = runTest {
        //Given
        val idUsuario = 1

        val ticketmetas = listOf(
            TicketMetaResponseDto(
                id = 1,
                idTicket = 1.0,
                asunto = "Ticket 1",
                empresa = "Empresa 1",
                estatus = 1,
                estatusDescription = "Pendiente"
            ),
            TicketMetaResponseDto(
                id = 2,
                idTicket = 2.0,
                asunto = "Ticket 2",
                empresa = "Empresa 2",
                estatus = 2,
                estatusDescription = "En Proceso"
            )
        )

        val remoteDataSource = mockk<RemoteDataSource>()
        val repository = TicketMetaRepository(remoteDataSource)

        coEvery { remoteDataSource.getMetasUsuario(idUsuario) } returns ticketmetas

        //When
        repository.getMetasUsuario(idUsuario).test {
            //Then
            Truth.assertThat(awaitItem() is Resource.Loading).isTrue()

            Truth.assertThat(awaitItem().data).isEqualTo(ticketmetas)

            cancelAndIgnoreRemainingEvents()
        }
    }
}