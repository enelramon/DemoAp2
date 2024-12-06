package edu.ucne.composedemo.data.repository

import edu.ucne.composedemo.data.remote.RemoteDataSource
import edu.ucne.composedemo.data.remote.Resource
import edu.ucne.composedemo.data.remote.dto.GastoRecurrenciaDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class GastoRecurrenciaRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
){
    fun getGastostoRecurrencias(): Flow<Resource<List<GastoRecurrenciaDto>>> = flow {
        try{
            emit(Resource.Loading())
            val gastosRecurenciaApi = remoteDataSource.getGastosRecurencias()
            emit (Resource.Success(gastosRecurenciaApi))
        }catch (e: HttpException){
            val errorMessage = e.response()?.errorBody()?.string() ?: e.message()
            emit(Resource.Error("Error de Internet:$errorMessage "))
        }
        catch (e: Exception){
            emit(Resource.Error("Error ${e.message}"))
        }
    }

    fun createGastoRecurencia(gastosRecurenciaDto: GastoRecurrenciaDto): Flow<Resource<GastoRecurrenciaDto>> = flow {
        try{
            emit(Resource.Loading())
            val gastosRecurenciaApi = remoteDataSource.createGastoRecurencia(gastosRecurenciaDto)
            emit (Resource.Success(gastosRecurenciaApi))
        }catch (e: HttpException){
            val errorMessage = e.response()?.errorBody()?.string() ?: e.message()
            emit(Resource.Error("Error de Internet:$errorMessage "))
        }
        catch (e: Exception){
            emit(Resource.Error("Error ${e.message}"))
        }
    }

    fun deleteGastoRecurencia(id: Int): Flow<Resource<Boolean>> = flow {
        try{
            emit(Resource.Loading())
            remoteDataSource.deleteGastoRecurencia(id)
            emit (Resource.Success(true))
        }catch (e: HttpException){
            val errorMessage = e.response()?.errorBody()?.string() ?: e.message()
            emit(Resource.Error("Error de Internet:$errorMessage "))
        }
        catch (e: Exception){
            emit(Resource.Error("Error ${e.message}"))
        }
    }
}