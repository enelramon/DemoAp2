package edu.ucne.composedemo.domain.usecase_tests

import app.cash.turbine.test
import com.google.common.truth.Truth
import edu.ucne.composedemo.data.remote.Resource
import edu.ucne.composedemo.data.remote.dto.SistemaDto
import edu.ucne.composedemo.data.repository.SistemaRepository
import edu.ucne.composedemo.domain.usecase.SistemaUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test

class SistemaUseCaseTest {

    @Test
    fun `Should return a flow of sistemas`() = runTest {
        // Given
        val sistemas = listOf(
            SistemaDto(1, "Sistema 1"),
            SistemaDto(2, "Sistema 2"),
            SistemaDto(3, "Sistema 3")
        )
        val sistemaRepository = mockk<SistemaRepository>()
        val sistemaUseCase = SistemaUseCase(sistemaRepository)

        coEvery { sistemaRepository.getSistemas() } returns flowOf(Resource.Success(sistemas))

        // When
        sistemaUseCase.getSistemas().test {
            // Then
            val result = awaitItem()
            Truth.assertThat(result.data).isEqualTo(sistemas)
            awaitComplete()
        }
    }

    @Test
    fun `Should call repository update when updateSistema is called`() = runTest {
        // Given
        val sistemaDto = SistemaDto(1, "Sistema Test")
        val sistemaRepository = mockk<SistemaRepository>(relaxed = true)
        val sistemaUseCase = SistemaUseCase(sistemaRepository)

        // When
        sistemaUseCase.updateSistema(sistemaDto)

        // Then
        coVerify { sistemaRepository.update(sistemaDto) }
    }

    @Test
    fun `Should validate campos correctly`() {
        // Given
        val sistemaRepository = mockk<SistemaRepository>()
        val sistemaUseCase = SistemaUseCase(sistemaRepository)

        // When & Then
        Truth.assertThat(sistemaUseCase.validateCampos("Sistema valido")).isTrue()
        Truth.assertThat(sistemaUseCase.validateCampos("")).isFalse()
        Truth.assertThat(sistemaUseCase.validateCampos(null)).isFalse()
        Truth.assertThat(sistemaUseCase.validateCampos("   ")).isFalse()
    }
}