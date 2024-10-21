package edu.ucne.composedemo.presentation.cobro


sealed interface CobroEvent {
    data class CobroChange(val cobroId: Int): CobroEvent
    data class  MontoChange(val monto: Int): CobroEvent
    data class CodigoClienteChange(val codigoClienteChange: Int): CobroEvent
    data class ObservacionesChange(val observacionesChange: String): CobroEvent
    data object Save: CobroEvent
    data object Delete: CobroEvent
    data object New: CobroEvent

}