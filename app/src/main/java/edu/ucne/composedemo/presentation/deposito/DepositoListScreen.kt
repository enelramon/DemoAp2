package edu.ucne.composedemo.presentation.deposito

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.composedemo.data.local.entities.DepositoEntity
import edu.ucne.composedemo.presentation.components.TopBarComponent

@Composable
fun DepositoListScreen(
    viewModel: DepositoViewModel = hiltViewModel(),
    onDrawer: () -> Unit,
    createDeposito: () -> Unit,
    goToDeposito: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    DepositoListBodyscreen(
        uiState = uiState,
        onDrawer = onDrawer,
        createDeposito = createDeposito,
        goToDeposito = goToDeposito,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DepositoListBodyscreen(
    uiState: DepositoUiState,
    onDrawer: () -> Unit,
    createDeposito: () -> Unit,
    goToDeposito: (Int) -> Unit,
) {
    Scaffold(
        topBar = {
            TopBarComponent(
                title = "Lista de Depósitos",
                onMenuClick = onDrawer
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = createDeposito,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Crear Depósito")
            }
        }
    ) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center)
                )
            } else if (uiState.errorMessage?.isNotEmpty() == true) {
                Text("Error: ${uiState.errorMessage}")
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.depositos) { deposito ->
                        DepositoRow(
                            deposito = deposito,
                            goToDeposito = goToDeposito
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DepositoRow(
    deposito: DepositoEntity,
    goToDeposito: (Int) -> Unit
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .clickable { deposito.depositoId?.let { goToDeposito(it) } },
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(5f),
                verticalArrangement = Arrangement.Center
            ) {
                DepositoInfoRow(label = "Id", value = deposito.depositoId.toString())
                DepositoInfoRow(label = "Concepto", value = deposito.concepto)
                DepositoInfoRow(label = "Fecha", value = deposito.fecha)
                DepositoInfoRow(label = "Cuenta", value = "${deposito.idCuenta}")
                DepositoInfoRow(label = "Monto", value = "${deposito.monto}")
            }
        }
    }
}

@Composable
fun DepositoInfoRow(label: String, value: String) {
    Text(
        text = "$label: $value",
        style = TextStyle(
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
    )
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun Preview() {
    val list = listOf(
        DepositoEntity(
            depositoId = 1,
            concepto = "Concepto 1",
            fecha = "2021-01-01",
            monto = 1500.0,
            idCuenta = 2,
        ),
        DepositoEntity(
            depositoId = 2,
            concepto = "Concepto 2",
            fecha = "2021-02-01",
            monto = 5000.0,
            idCuenta = 3,
        ),
    )

    DepositoListBodyscreen(
        uiState = DepositoUiState(depositos = list),
        onDrawer = {},
        goToDeposito = {},
        createDeposito = {}
    )
}