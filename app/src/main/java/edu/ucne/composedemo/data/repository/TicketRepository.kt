package edu.ucne.composedemo.data.repository

import edu.ucne.composedemo.data.local.dao.TicketDao
import edu.ucne.composedemo.data.local.entities.TicketEntity
import edu.ucne.composedemo.data.remote.RemoteDataSource
import edu.ucne.composedemo.data.remote.Resource
import edu.ucne.composedemo.data.remote.dto.TicketDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class TicketRepository @Inject constructor(
    private val ticketDao: TicketDao,
    private val remoteDataSource: RemoteDataSource
) {
    suspend fun save(ticket: TicketEntity) = ticketDao.save(ticket)

    suspend fun saveApi(ticket: TicketDto) = remoteDataSource.saveTicket(ticket)

    suspend fun getTicket(ticketId: Double): List<TicketDto> {
        return remoteDataSource.getTicket(ticketId)
    }


    suspend fun delete(ticket: TicketEntity) = ticketDao.delete(ticket)

    fun getTickets(): Flow<Resource<List<TicketDto>>> = flow {
        try {
            emit(Resource.Loading())
            val tickets = remoteDataSource.getTickets()

            emit(Resource.Success(tickets))

        } catch (e: HttpException) {
            emit(Resource.Error("Error de internet: ${e.message()}"))
        }catch (e: Exception){
            emit(Resource.Error("Error desconocido: ${e.message}"))
        }
    }
}