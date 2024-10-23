package edu.ucne.composedemo.data.repository_tests

import androidx.compose.ui.text.intl.Locale
import app.cash.turbine.test
import com.google.common.truth.Truth
import edu.ucne.composedemo.data.remote.RemoteDataSource
import edu.ucne.composedemo.data.remote.Resource
import edu.ucne.composedemo.data.remote.dto.CobroDto
import edu.ucne.composedemo.data.repository.CobroRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date

class CobroRepositoryTest {

    @Test
    fun getCobro() = runTest {

        val fecha = Date()
        //Given
        val cobro = listOf(
            CobroDto(1,fecha, 56,567,"OJo"),
            CobroDto(2,fecha, 56,567,"OJo"),
            CobroDto(3,fecha, 56,567,"OJo"),

        )
        val remoteDataSource = mockk<RemoteDataSource>()
        val repository = CobroRepository(remoteDataSource)

        coEvery { remoteDataSource.getCobros() } returns cobro

        //When
        repository.getCobro().test {
            //Then
            Truth.assertThat(awaitItem() is  Resource.Loading).isTrue()
            Truth.assertThat(awaitItem().data).isEqualTo(cobro)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun update() {
    }
}