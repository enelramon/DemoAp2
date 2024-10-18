package edu.ucne.composedemo.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.composedemo.data.local.entities.SuplidorGastoEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface SuplidorGastoDao {
    @Upsert
    suspend fun save(suplidorGastoEntity: SuplidorGastoEntity)
    @Query("Select * from suplidoresgastos")
    fun getSuplidoresGastos(): Flow<List<SuplidorGastoEntity>>
}