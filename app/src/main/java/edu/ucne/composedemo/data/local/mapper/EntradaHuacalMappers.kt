package edu.ucne.composedemo.data.local.mapper

import edu.ucne.composedemo.data.local.entities.EntradaHuacalEntity
import edu.ucne.composedemo.domain.entradashuacales.model.EntradaHuacal

fun EntradaHuacalEntity.toDomain(): EntradaHuacal = EntradaHuacal(
    idEntrada = idEntrada,
    fecha = fecha,
    nombreCliente = nombreCliente,
    cantidad = cantidad,
    precio = precio
)

fun EntradaHuacal.toEntity(): EntradaHuacalEntity = EntradaHuacalEntity(
    idEntrada = idEntrada,
    fecha = fecha,
    nombreCliente = nombreCliente,
    cantidad = cantidad,
    precio = precio
)
