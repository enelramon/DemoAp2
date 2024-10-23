package edu.ucne.composedemo.data.remote

import edu.ucne.composedemo.data.remote.dto.AnyDeskLogDto
import edu.ucne.composedemo.data.remote.dto.ClienteDto
import edu.ucne.composedemo.data.remote.dto.EquiposAnyDeskDto
import edu.ucne.composedemo.data.remote.dto.CobroDto
import edu.ucne.composedemo.data.remote.dto.SistemaDto
import edu.ucne.composedemo.data.remote.dto.SuplidorGastoDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
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
    suspend fun getClientes(): List<ClienteDto>

    @Headers("X-API-Key:test")
    @GET("api/EquiposAnydesk")
    suspend fun getEquiposAnyDesks(): List<EquiposAnyDeskDto>

    @Headers("X-API-Key:test")
    @GET("api/AnydeskLog/{id}")
    suspend fun getAnyDeskLogs(@Path("id") id: Int): List<AnyDeskLogDto>

    @Headers("X-API-Key:test")
    @GET("api/SuplidoresGastos")
    suspend fun getSuplidoresGastos(): List<SuplidorGastoDto>
    @GET("api/Cobros")
    suspend fun  getCobro(): List<CobroDto>



}