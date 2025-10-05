package edu.ucne.composedemo.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "entradasHuacales")
data class EntradaHuacalEntity(
    @PrimaryKey(autoGenerate = true)
    val idEntrada: Int = 0,
    val fecha: String,
    val nombreCliente: String,
    val cantidad: Int,
    val precio: Double
)
