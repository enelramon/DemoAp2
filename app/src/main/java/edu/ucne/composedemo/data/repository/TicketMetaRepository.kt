package edu.ucne.composedemo.data.repository

import edu.ucne.composedemo.data.remote.RemoteDataSource
import edu.ucne.composedemo.data.remote.Resource
import edu.ucne.composedemo.data.remote.dto.TicketMetaRequestDto
import edu.ucne.composedemo.data.remote.dto.TicketMetaResponseDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class TicketMetaRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {
    fun getMetasUsuario(idUsuario: Int): Flow<Resource<List<TicketMetaResponseDto>>> = flow {
        try{
            emit(Resource.Loading())
            val metasUsuario = remoteDataSource.getMetasUsuario(idUsuario)

            emit(Resource.Success(metasUsuario))
        } catch (e: HttpException){
            emit(Resource.Error("Error de internet: ${e.message()}"))
        } catch (e: Exception){
            emit(Resource.Error("Error desconocido: ${e.message}"))
        }
    }

    suspend fun addTicketMeta(ticketMetaRequestDto: TicketMetaRequestDto) = remoteDataSource.addTicketMeta(ticketMetaRequestDto)

    suspend fun deleteTicketMeta(idDetalle: Int) = remoteDataSource.deleteTicketMeta(idDetalle)
}