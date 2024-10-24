package edu.ucne.composedemo.data.repository_tests

import app.cash.turbine.test
import com.google.common.truth.Truth
import edu.ucne.composedemo.data.remote.RemoteDataSource
import edu.ucne.composedemo.data.remote.Resource
import edu.ucne.composedemo.data.remote.dto.GastoDto
import edu.ucne.composedemo.data.repository.Gastosrepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

class GastosrepositoryTest {
    private lateinit var repository: Gastosrepository
    private lateinit var remoteDataSource: RemoteDataSource

    @Before
    fun setup() {
        remoteDataSource = mockk()
        repository = Gastosrepository(remoteDataSource)
    }

    @Test
    fun `getGastos returns success when data is fetched successfully`() = runTest {
        // Arrange
        val expectedGastos = listOf(
            GastoDto(1, "2021-01-01", 1, "Suplidor 1", "NCF 1", "Concepto 1", 30.3, 15.0, 300.0),
            GastoDto(2, "2021-01-02", 2, "Suplidor 2", "NCF 2", "Concepto 2", 20.0, 12.0, 300.0)
        )
        coEvery { remoteDataSource.getGastos() } returns expectedGastos

        // Act
        val result = repository.getGastos().toList()

        // Assert
        assert(result[0].data == null) // Loading state
        assert(result[1].data == expectedGastos) // Success state
    }
}