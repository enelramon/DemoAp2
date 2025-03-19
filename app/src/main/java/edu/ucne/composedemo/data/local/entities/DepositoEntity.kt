package edu.ucne.composedemo.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Depositos")
data class DepositoEntity(
    @PrimaryKey
    val depositoId: Int?,
    val fecha: String,
    val idCuenta: Int,
    val concepto: String,
    val monto: Double
)