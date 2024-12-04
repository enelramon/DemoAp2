package edu.ucne.composedemo.data.repository_tests

import app.cash.turbine.test
import com.google.common.truth.Truth
import edu.ucne.composedemo.data.remote.RemoteDataSource
import edu.ucne.composedemo.data.remote.Resource
import edu.ucne.composedemo.data.remote.dto.TicketMetaRequestDto
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
            TicketMetaRequestDto(
                id = 1
            ),
            TicketMetaRequestDto(
                id = 2
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