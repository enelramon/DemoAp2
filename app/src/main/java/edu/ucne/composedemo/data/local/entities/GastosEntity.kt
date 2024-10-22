package edu.ucne.composedemo.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Gastos")
data class GastoEntity(
    @PrimaryKey
    val idGasto: Int? = null,
    val fecha: String = "",
    val idSuplidor: Int = 0,
    val suplidor: String = "",
    val ncf: String = "",
    val concepto: String = "",
    val descuento: Double = 0.0,
    val itbis: Double = 0.0,
    val monto: Double = 0.0,
    val numero: String = ""
)