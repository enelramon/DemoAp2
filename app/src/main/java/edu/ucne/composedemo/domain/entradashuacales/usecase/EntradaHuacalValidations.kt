package edu.ucne.composedemo.domain.entradashuacales.usecase

import edu.ucne.composedemo.domain.entradashuacales.model.EntradaHuacal

data class ValidationResult(
    val isValid: Boolean,
    val error: String? = null
)

fun validateNombreCliente(nombreCliente: String): ValidationResult {
    if (nombreCliente.isBlank()) {
        return ValidationResult(false, "El nombre del cliente no puede estar vacío")
    }
    if (nombreCliente.length < 3) {
        return ValidationResult(false, "El nombre del cliente debe tener al menos 3 caracteres")
    }
    return ValidationResult(true)
}

fun validateCantidad(cantidadStr: String): ValidationResult {
    if (cantidadStr.isBlank()) {
        return ValidationResult(false, "La cantidad no puede estar vacía")
    }
    val cantidad = cantidadStr.toIntOrNull()
    if (cantidad == null) {
        return ValidationResult(false, "La cantidad debe ser un número válido")
    }
    if (cantidad <= 0) {
        return ValidationResult(false, "La cantidad debe ser mayor a 0")
    }
    return ValidationResult(true)
}

fun validatePrecio(precioStr: String): ValidationResult {
    if (precioStr.isBlank()) {
        return ValidationResult(false, "El precio no puede estar vacío")
    }
    val precio = precioStr.toDoubleOrNull()
    if (precio == null) {
        return ValidationResult(false, "El precio debe ser un número válido")
    }
    if (precio < 0) {
        return ValidationResult(false, "El precio debe ser mayor o igual a 0")
    }
    return ValidationResult(true)
}

fun validateFecha(fecha: String): ValidationResult {
    if (fecha.isBlank()) {
        return ValidationResult(false, "La fecha no puede estar vacía")
    }
    return ValidationResult(true)
}

fun validateEntradaHuacal(entradaHuacal: EntradaHuacal): ValidationResult {
    val nombreValidation = validateNombreCliente(entradaHuacal.nombreCliente)
    if (!nombreValidation.isValid) return nombreValidation
    
    val fechaValidation = validateFecha(entradaHuacal.fecha)
    if (!fechaValidation.isValid) return fechaValidation
    
    if (entradaHuacal.cantidad <= 0) {
        return ValidationResult(false, "La cantidad debe ser mayor a 0")
    }
    
    if (entradaHuacal.precio < 0) {
        return ValidationResult(false, "El precio debe ser mayor o igual a 0")
    }
    
    return ValidationResult(true)
}
