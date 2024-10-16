package edu.ucne.composedemo.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SuplidoresGastos")
data class SuplidorGastoEntity (
    @PrimaryKey
    val idSuplidor: Int,
    val nombres: String,
    val direccion: String,
    val telefono: String,
    val celular: String,
    val fax: String,
    val rnc: String,
    val tipoNCF: Int,
    val condicion: Int,
    val email: String
)