package edu.ucne.composedemo.presentation.gastorecurrencia

sealed interface GastoRecurrenciaEvent {
    data class GetGastosRecurrencia(val idSuplidor: Int): GastoRecurrenciaEvent
    data class DiaChange(val dia: String): GastoRecurrenciaEvent
    data class PeriodicidadChange(val periodicidad: String): GastoRecurrenciaEvent
    data class MontoChange(val monto: String): GastoRecurrenciaEvent
    data class EsRecurenteChange(val esRecurente: Boolean): GastoRecurrenciaEvent
    data class SaveGastoRecurrencia(val goSuplidoresGastos: () -> Unit): GastoRecurrenciaEvent
}