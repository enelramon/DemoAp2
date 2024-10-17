package edu.ucne.composedemo.data.remote

import edu.ucne.composedemo.data.remote.dto.SistemaDto
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val ticketingApi: TicketingApi,
) {
    suspend fun getSistemas() = ticketingApi.getSistemas()

    suspend fun updateSistemas(sistemaDto: SistemaDto) = ticketingApi.updateSistema(sistemaDto)

    suspend fun getAllAnydeskLog() = ticketingApi.getAnyDeskLogs()

    suspend fun getAnydeskLog(id: Int) = ticketingApi.getAnyDeskLogById(id)
}