package edu.ucne.composedemo.presentation.cobro

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api

import androidx.compose.material3.HorizontalDivider

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.composedemo.data.remote.dto.CobroDto
import edu.ucne.composedemo.presentation.components.TopBarComponent

import java.text.SimpleDateFormat
import java.util.Locale


@Composable
fun CobroListScreen(
    viewModel: CobroViewModel = hiltViewModel(),
    onDrawer: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    CobroListBodyScreen(uiState, onDrawer)

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CobroListBodyScreen(uiState: CobroUiState, onDrawer: () -> Unit) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBarComponent (
                title = "Cobros" ,onDrawer)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.CenterHorizontally)

                    )
                }

                uiState.errorMessage != null -> {
                    Text(text = uiState.errorMessage)
                }

                else -> {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(uiState.cobros) { cobro ->
                            CobroRow(cobro)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CobroRow(cobro: CobroDto) {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable { }
        ) {
            Text(
                text = "Fecha: ${dateFormat.format(cobro.fecha)}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(modifier = Modifier.weight(1f), text = cobro.idCobro.toString())
            Text(modifier = Modifier.weight(2f), text = cobro.monto.toString())
            Text(modifier = Modifier.weight(2f), text = cobro.codigoCliente.toString())
            Text(modifier = Modifier.weight(2f), text = cobro.observaciones)
        }
    }
    HorizontalDivider()
}