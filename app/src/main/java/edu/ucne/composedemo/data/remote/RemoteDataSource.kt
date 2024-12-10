package edu.ucne.composedemo.data.remote

import edu.ucne.composedemo.data.remote.dto.SistemaDto
import edu.ucne.composedemo.data.remote.dto.TicketDto
import edu.ucne.composedemo.data.remote.dto.TicketMetaRequestDto
import edu.ucne.composedemo.data.remote.dto.TicketPostMen
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val ticketingApi: TicketingApi

) {
    suspend fun getSistemas() = ticketingApi.getSistemas()

    suspend fun updateSistemas(sistemaDto: SistemaDto) = ticketingApi.updateSistema(sistemaDto)

    suspend fun getCliente(id: Double?) = ticketingApi.getClientes()
    suspend fun getClientes() = ticketingApi.getClientes()

    suspend fun getEquiposAnyDesks() = ticketingApi.getEquiposAnyDesks()

    suspend fun getAnydeskLog(id: Int) = ticketingApi.getAnyDeskLogs(id)

    suspend fun getGastos() = ticketingApi.getGastos()

    suspend fun getCobros() = ticketingApi.getCobro()

    suspend fun getTiposSoportes() = ticketingApi.getTiposSoportes()

    suspend fun getSuplidoresGastos() = ticketingApi.getSuplidoresGastos()

    suspend fun getCxc(idCliente: Int) = ticketingApi.getCxc(idCliente)

    suspend fun getTickets() = ticketingApi.getTickets()
    suspend fun saveTicket(ticketDto: TicketDto) = ticketingApi.saveTicket(ticketDto)
    suspend fun getTicket(idTicket: Double) = ticketingApi.getTicket(idTicket)

    suspend fun getMensaje(idTicket: Double) =ticketingApi.getMensaje(idTicket)
    suspend fun postMensaje(ticketPostMen: TicketPostMen) = ticketingApi.postMensaje(ticketPostMen)

    suspend fun getMetasUsuario(idUsuario: Int) = ticketingApi.getMetasUsuario(idUsuario)

    suspend fun addTicketMeta(ticketMetaRequestDto: TicketMetaRequestDto) = ticketingApi.addTicketMeta(ticketMetaRequestDto)

    suspend fun deleteTicketMeta(idDetalle: Int) = ticketingApi.deleteTicketMeta(idDetalle)
}