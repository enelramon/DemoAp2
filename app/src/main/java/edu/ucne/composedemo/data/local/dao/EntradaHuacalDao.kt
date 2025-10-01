package edu.ucne.composedemo.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.composedemo.data.local.entities.EntradaHuacalEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EntradaHuacalDao {
    @Query("SELECT * FROM entradasHuacales ORDER BY idEntrada DESC")
    fun observeAll(): Flow<List<EntradaHuacalEntity>>

    @Query("SELECT * FROM entradasHuacales WHERE idEntrada = :id")
    suspend fun getById(id: Int): EntradaHuacalEntity?

    @Query("""
        SELECT * FROM entradasHuacales 
        WHERE (:nombreCliente IS NULL OR nombreCliente LIKE '%' || :nombreCliente || '%')
        AND (:fechaInicio IS NULL OR fecha >= :fechaInicio)
        AND (:fechaFin IS NULL OR fecha <= :fechaFin)
        ORDER BY idEntrada DESC
    """)
    fun observeFiltered(
        nombreCliente: String?,
        fechaInicio: String?,
        fechaFin: String?
    ): Flow<List<EntradaHuacalEntity>>

    @Upsert
    suspend fun upsert(entity: EntradaHuacalEntity)

    @Query("DELETE FROM entradasHuacales WHERE idEntrada = :id")
    suspend fun deleteById(id: Int)
}
