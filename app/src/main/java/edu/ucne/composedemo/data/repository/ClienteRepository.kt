package edu.ucne.composedemo.data.repository

import edu.ucne.composedemo.data.remote.RemoteDataSource
import edu.ucne.composedemo.data.remote.Resource
import edu.ucne.composedemo.data.remote.dto.ClienteDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class ClienteRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {
    suspend fun getClienteById(id: Double) = remoteDataSource.getCliente(id)

    fun getClientes(): Flow<Resource<List<ClienteDto>>> = flow {
        try {
            emit(Resource.Loading())
            val clientes = remoteDataSource.getClientes()
            emit(Resource.Success(clientes))
        } catch (e: HttpException) {
            val errorMessage = e.response()?.errorBody()?.string() ?: e.message()
            emit(Resource.Error("Error de conexion $errorMessage"))
        } catch (e: Exception) {
            emit(Resource.Error("Error ${e.message}"))
        }
    }
}