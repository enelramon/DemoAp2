package edu.ucne.composedemo.domain.usecase_tests

import app.cash.turbine.test
import com.google.common.truth.Truth
import edu.ucne.composedemo.data.remote.Resource
import edu.ucne.composedemo.data.remote.dto.CxcDto
import edu.ucne.composedemo.data.repository.CxcRepository
import edu.ucne.composedemo.domain.usecase.CxcUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test

class CxcUseCaseTest {

    @Test
    fun `Should return a flow of cxc for given clienteId`() = runTest {
        // Given
        val clienteId = 4
        val cxc = listOf(
            CxcDto(1, "2023-01-01", 1000f, 500f),
            CxcDto(2, "2023-01-02", 2000f, 1500f)
        )
        val cxcRepository = mockk<CxcRepository>()
        val cxcUseCase = CxcUseCase(cxcRepository)

        coEvery { cxcRepository.getCxc(clienteId) } returns flowOf(Resource.Success(cxc))

        // When
        cxcUseCase.getCxc(clienteId).test {
            // Then
            val result = awaitItem()
            Truth.assertThat(result.data).isEqualTo(cxc)
            awaitComplete()
        }
    }
}