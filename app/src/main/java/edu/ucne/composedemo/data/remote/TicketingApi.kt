package edu.ucne.composedemo.data.remote

import edu.ucne.composedemo.data.remote.dto.AnyDeskLogDto
import edu.ucne.composedemo.data.remote.dto.ClienteDto
import edu.ucne.composedemo.data.remote.dto.CobroDto
import edu.ucne.composedemo.data.remote.dto.CxcDto
import edu.ucne.composedemo.data.remote.dto.EquiposAnyDeskDto
import edu.ucne.composedemo.data.remote.dto.GastoDto
import edu.ucne.composedemo.data.remote.dto.GastoRecurrenciaDto
import edu.ucne.composedemo.data.remote.dto.SistemaDto
import edu.ucne.composedemo.data.remote.dto.SuplidorGastoDto
import edu.ucne.composedemo.data.remote.dto.TicketDto
import edu.ucne.composedemo.data.remote.dto.TicketGetMen
import edu.ucne.composedemo.data.remote.dto.TicketMetaRequestDto
import edu.ucne.composedemo.data.remote.dto.TicketMetaResponseDto
import edu.ucne.composedemo.data.remote.dto.TicketPostMen
import edu.ucne.composedemo.data.remote.dto.TiposSoportesDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TicketingApi {
    @Headers("X-API-Key:test")
    @GET("api/Sistemas")
    suspend fun getSistemas(): List<SistemaDto>

    @Headers("X-API-Key", "test")
    @PUT("api/Sistemas")
    suspend fun updateSistema(@Body sistemaDto: SistemaDto): SistemaDto

    @Headers("X-API-Key:test")
    @GET("api/Clientes/GetClientes")
    suspend fun getClientee(): List<ClienteDto>

    @Headers("X-API-Key:test")
    @GET("api/Clientes/GetClientes")
    suspend fun getClientes(): List<ClienteDto>

    @Headers("X-API-Key:test")
    @GET("api/EquiposAnydesk")
    suspend fun getEquiposAnyDesks(): List<EquiposAnyDeskDto>

    @Headers("X-API-Key:test")
    @GET("api/AnydeskLog/{id}")
    suspend fun getAnyDeskLogs(@Path("id") id: Int): List<AnyDeskLogDto>

    @Headers("X-API-Key:test")
    @GET("api/Gastos")
    suspend fun getGastos(): List<GastoDto>

    @Headers("X-API-Key:test")
    @GET("api/Cobros")
    suspend fun  getCobro(): List<CobroDto>

    @Headers("X-API-Key:test")
    @GET("api/TiposSoportes")
    suspend fun getTiposSoportes(): List<TiposSoportesDto>

    @Headers("X-API-Key:test")
    @GET("api/SuplidoresGastos")
    suspend fun getSuplidoresGastos(): List<SuplidorGastoDto>

    @Headers("X-API-Key:test")
    @GET("api/SuplidoresGastos/{id}")
    suspend fun getSuplidorGasto(@Path("id") id: Int): SuplidorGastoDto

    @Headers("X-API-Key:test")
    @GET("api/Cxc/{idCliente}")
    suspend fun getCxc(@Path("idCliente") idCliente: Int): List<CxcDto>

    @Headers("X-API-Key:test")
    @GET("api/Tickets")
    suspend fun getTickets() : List<TicketDto>
    @Headers("X-API-Key:test")
    @GET("api/Tickets/{idTicket}")
    suspend fun getTicket(@Path("idTicket") idTicket: Double): List<TicketDto>
    @Headers("X-API-Key:test")
    @POST("api/Tickets")
    suspend fun saveTicket(@Body ticketDto: TicketDto): TicketDto
    @PUT("api/Tickets/{idTickets}")
    suspend fun putTickets(@Path("idTickets") idTicket: Double, @Body ticket: TicketDto):TicketDto

    @Headers("X-API-Key:test")
    @GET("api/GastosRecurencias/{id}")
    suspend fun getGastosRecurencia(@Path("id") id: Int): GastoRecurrenciaDto

    @Headers("X-API-Key:test")
    @POST("api/Gastos")
    suspend fun createGasto(@Body gastoDto: GastoDto): GastoDto

    @Headers("X-API-Key:test")
    @GET("api/Tickets/GetMensajes/{idTicket}")
    suspend fun getMensaje(@Path("idTicket") idTicket: Double): List<TicketGetMen>

    @Headers("X-API-Key:test")
    @POST("api/Tickets/PostMensaje")
    suspend fun postMensaje(@Body ticketPostMen: TicketPostMen): TicketPostMen

    @Headers("X-API-Key:test")
    @GET("api/GastosRecurencias")
    suspend fun getGastosRecurencias(): List<GastoRecurrenciaDto>

    @Headers("X-API-Key:test")
    @POST("api/GastosRecurencias")
    suspend fun createGastoRecurencia(@Body gastosRecurenciaDto: GastoRecurrenciaDto): GastoRecurrenciaDto

    @Headers("X-API-Key:test")
    @DELETE("api/GastosRecurencias/{id}")
    suspend fun deleteGastosRecurencia(@Path("id") id: Int): Response<Unit>
  
    @Headers("X-API-Key:test")
    @GET("api/TicketMetas/GetMetasUsuario/{idUsuario}")
    suspend fun getMetasUsuario(@Path("idUsuario") idUsuario: Int): List<TicketMetaResponseDto>

    @Headers("X-API-Key:test")
    @POST("api/TicketMetas/AddTicketMeta")
    suspend fun addTicketMeta(@Body ticketMetaRequestDto: TicketMetaRequestDto): TicketMetaRequestDto

    @Headers("X-API-Key:test")
    @DELETE("api/TicketMetas/DeleteTicketMeta/{idDetalle")
    suspend fun deleteTicketMeta(@Path("idDetalle") idDetalle: Int): Response<Unit>
}