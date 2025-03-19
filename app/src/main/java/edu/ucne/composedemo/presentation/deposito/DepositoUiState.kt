package edu.ucne.composedemo.presentation.deposito

import edu.ucne.composedemo.data.local.entities.DepositoEntity
import edu.ucne.composedemo.data.remote.dto.DepositoDto

data class DepositoUiState(
    val depositoId: Int? = null,
    val fecha: String = "",
    val idCuenta: String = "",
    val concepto: String = "",
    val monto: String = "",
    val errorMessage: String? = null,
    val depositos: List<DepositoEntity> = emptyList(),
    val isLoading: Boolean = false,
)