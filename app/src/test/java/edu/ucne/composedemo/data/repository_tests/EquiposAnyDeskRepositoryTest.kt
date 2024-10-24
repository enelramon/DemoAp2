package edu.ucne.composedemo.data.repository_tests

import app.cash.turbine.test
import com.google.common.truth.Truth
import edu.ucne.composedemo.data.remote.RemoteDataSource
import edu.ucne.composedemo.data.remote.Resource
import edu.ucne.composedemo.data.remote.dto.EquiposAnyDeskDto
import edu.ucne.composedemo.data.repository.EquiposAnyDeskRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test

class EquiposAnyDeskRepositoryTest {

    @Test
    fun `Should return a flow of equiposAnyDesk`() = runTest {
        //Given
        val equiposAnyDesk = listOf(
            EquiposAnyDeskDto(
                idEquipo = 1,
                alias = "Equipo 1",
                clave = "Clave 1",
                descripcion = "Descripción 1",
                codigoCliente = 1,
                aliasAnydesk = "Alias Anydesk 1"
            ),
            EquiposAnyDeskDto(
                idEquipo = 2,
                alias = "Equipo 2",
                clave = "Clave 2",
                descripcion = "Descripción 2",
                codigoCliente = 2,
                aliasAnydesk = "Alias Anydesk 2"
            )
        )
        val remoteDataSource = mockk<RemoteDataSource>()
        val repository = EquiposAnyDeskRepository(remoteDataSource)

        coEvery { remoteDataSource.getEquiposAnyDesks() } returns equiposAnyDesk

        //When
        repository.getEquiposAnyDesks().test {
            //Then
            Truth.assertThat(awaitItem() is Resource.Loading).isTrue()

            Truth.assertThat(awaitItem().data).isEqualTo(equiposAnyDesk)

            cancelAndIgnoreRemainingEvents()
        }
    }
}