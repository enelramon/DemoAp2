package edu.ucne.composedemo.presentation.gastos

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.composedemo.data.remote.dto.GastoDto

@Composable
fun GastosListScreen(
    viewModel: GastosViewModel = hiltViewModel(),
    onDrawer: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    GastosListBodyscreen(
        uiState = uiState,
        onDrawer = onDrawer
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GastosListBodyscreen(
    uiState: GastosUiState,
    onDrawer: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    onDrawer()
                    Text(
                        text = "Lista de Gastos",
                        style = TextStyle(
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    )
                },
                modifier = Modifier.background(MaterialTheme.colorScheme.primary)
            )
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
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(align = Alignment.Center)
                )
            } else if (uiState.errorMessage.isNotEmpty()) {
                Text("Error: ${uiState.errorMessage}")
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.gastos) { gasto ->
                        GastosRow(gasto = gasto)
                    }
                }
            }
        }
    }
}

@Composable
fun GastoInfoRow(label: String, value: String) {
    Text(
        text = "$label: $value",
        style = TextStyle(
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
    )
}

@Composable
fun GastosRow(
    gasto: GastoDto
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
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
                GastoInfoRow(label = "Concepto", value = gasto.concepto)
                GastoInfoRow(label = "Fecha", value = gasto.fecha)
                GastoInfoRow(label = "Descuento", value = "${gasto.descuento}")
                GastoInfoRow(label = "Itbis", value = "${gasto.itbis}")
                GastoInfoRow(label = "Monto", value = "${gasto.monto}")
                GastoInfoRow(label = "Suplidor", value = gasto.suplidor)
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun Preview() {
    val list = listOf(
        GastoDto(
            idGasto = 1,
            concepto = "Concepto 1",
            fecha = "2021-01-01",
            descuento = 10.0,
            itbis = 15.0,
            monto = 100.0,
            idSuplidor = 2,
            suplidor = "Suplidor 1",
            ncf = "NCF 1"
        ),
        GastoDto(
            idGasto = 2,
            concepto = "Concepto 2",
            fecha = "2021-01-01",
            descuento = 20.0,
            itbis = 12.0,
            monto = 300.0,
            idSuplidor = 3,
            suplidor = "Suplidor 2",
            ncf = "NCF 2"
        )
    )

    GastosListBodyscreen(
        uiState = GastosUiState(gastos = list),
        onDrawer = {}
    )
}
