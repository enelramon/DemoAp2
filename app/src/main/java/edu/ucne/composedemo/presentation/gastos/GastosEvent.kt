package edu.ucne.composedemo.presentation.gastos

sealed interface GastosEvent {
    data class GetGasto(val idSuplidor: Int): GastosEvent
    data class FechaChange(val fecha: String): GastosEvent
    data class NcfChange(val ncf: String): GastosEvent
    data class ConceptoChange(val concepto: String): GastosEvent
    data class DescuentoChange(val descuento: String): GastosEvent
    data class ItbisChange(val itbis: String): GastosEvent
    data class MontoChange(val monto: String): GastosEvent
    data class SaveGasto(val navigateBack: () -> Unit): GastosEvent
}