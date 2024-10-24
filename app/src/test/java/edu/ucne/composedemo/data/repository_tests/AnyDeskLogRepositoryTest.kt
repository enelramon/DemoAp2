package edu.ucne.composedemo.data.repository_tests

import app.cash.turbine.test
import com.google.common.truth.Truth
import edu.ucne.composedemo.data.remote.RemoteDataSource
import edu.ucne.composedemo.data.remote.Resource
import edu.ucne.composedemo.data.remote.dto.AnyDeskLogDto
import edu.ucne.composedemo.data.repository.AnyDeskLogRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class AnyDeskLogRepositoryTest {
    @Test
    fun `Should return a flow of anydesklogs`() = runTest {
        val anydesklogs = listOf(
            AnyDeskLogDto(1, "Ferreteria Gama", 1.0, 10),
            AnyDeskLogDto(2, "Ferreteria mireserba", 2.0, 15),
            AnyDeskLogDto(3, "Supermercado Yoma", 3.0, 20)
        )
        val remoteDataSource = mockk<RemoteDataSource>()
        val repository = AnyDeskLogRepository(remoteDataSource)

        coEvery { remoteDataSource.getAnydeskLog(1) } returns anydesklogs
        repository.getAnydeskLog(1).test {
            Truth.assertThat(awaitItem() is Resource.Loading).isTrue()
            Truth.assertThat(awaitItem().data).isEqualTo(anydesklogs)
            cancelAndIgnoreRemainingEvents()

        }
    }
}