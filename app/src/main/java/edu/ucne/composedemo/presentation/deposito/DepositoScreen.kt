package edu.ucne.composedemo.presentation.deposito

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.composedemo.data.local.entities.DepositoEntity
import edu.ucne.composedemo.presentation.components.CustomDatePicker

@Composable
fun DepositoScreen(
    viewModel: DepositoViewModel = hiltViewModel(),
    depositoId: Int
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    DepositoBodyScreen(
        depositoId = depositoId,
        viewModel = viewModel,
        uiState = uiState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DepositoBodyScreen(
    depositoId: Int,
    viewModel: DepositoViewModel,
    uiState: DepositoUiState
) {
    LaunchedEffect(depositoId) {
        if (depositoId > 0) viewModel.find(depositoId)
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = if (depositoId > 0) "Editar Depósito" else "Registrar Depósito",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                label = { Text("Concepto") },
                value = uiState.concepto,
                onValueChange = viewModel::onConceptoChange,
                modifier = Modifier.fillMaxWidth()
            )
            CustomDatePicker(
                label = "Fecha",
                selectedDate = uiState.fecha,
                onDateSelected = viewModel::onFechaChange
            )
            OutlinedTextField(
                label = { Text("Monto") },
                value = uiState.monto,
                onValueChange = viewModel::onMontoChange,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                label = { Text("Cuenta") },
                value = uiState.idCuenta.toString(),
                onValueChange = viewModel::onCuentaChange,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                OutlinedButton(
                    onClick = {
                        viewModel.new()
                    }
                ) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Limpiar")
                    Text(text = "Limpiar")
                }
                if (depositoId > 0) {
                    OutlinedButton(
                        onClick = {
                            viewModel.delete(depositoId)
                        }
                    ) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Eliminar")
                        Text(text = "Eliminar")
                    }
                }
                OutlinedButton(
                    onClick = {
                        if (viewModel.isValid()) {
                            if (depositoId > 0) viewModel.update() else viewModel.save()
                        }
                    }
                ) {
                    Icon(imageVector = Icons.Default.Check, contentDescription = "Guardar")
                    Text(text = if (depositoId > 0) "Modificar" else "Guardar")
                }
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun Preview() {
    DepositoBodyScreen(
        depositoId = 0,
        viewModel = hiltViewModel(),
        uiState = DepositoUiState()
    )
}