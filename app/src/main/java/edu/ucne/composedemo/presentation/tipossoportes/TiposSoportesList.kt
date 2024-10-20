package edu.ucne.composedemo.presentation.tipossoportes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.composedemo.data.remote.dto.TiposSoportesDto
import edu.ucne.composedemo.presentation.components.TopBarComponent

@Composable
fun TiposSoportesListScreen(
    viewModel: TiposSoportesViewModel = hiltViewModel(),
    onDrawer: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    TiposSoportesListBodyScreen(
        uiState = uiState,
        onDrawer = onDrawer
    )
}

@Composable
fun TiposSoportesListBodyScreen(
    uiState: TiposSoportesUiState,
    onDrawer: () -> Unit
) {
    Scaffold(
        Modifier.fillMaxSize(),
        topBar = {
            TopBarComponent (
                title = "Tipos Soportes",
                onDrawer
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Spacer(
                modifier = Modifier
                    .height(32.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "idTipo", modifier = Modifier.weight(0.10f))
                Text(text = "Descripción", modifier = Modifier.weight(0.10f))
                Text(text = "PrecioBase", modifier = Modifier.weight(0.10f))
            }

            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                    )
                }

                uiState.errorMessage != null -> {
                    Text(
                        text = uiState.errorMessage,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally),
                        color = MaterialTheme.colorScheme.error
                    )
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        items(uiState.tiposSoportes) {
                            TiposSoportesRow(it)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TiposSoportesRow(
    it: TiposSoportesDto
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(modifier = Modifier.weight(0.10f), text = it.idTipo.toString())
            Text(modifier = Modifier.weight(0.10f), text = it.descripcion)
            Text(modifier = Modifier.weight(0.10f), text = it.precioBase.toString())
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun TiposSoportesListPreview() {
    TiposSoportesListBodyScreen(
        uiState = TiposSoportesUiState(
            tiposSoportes = listOf(
                TiposSoportesDto(
                    idTipo = 1,
                    descripcion = "Soporte 1",
                    precioBase = 100
                ),
                TiposSoportesDto(
                    idTipo = 2,
                    descripcion = "Soporte 2",
                    precioBase = 200
                ),
                TiposSoportesDto(
                    idTipo = 3,
                    descripcion = "Soporte 3",
                    precioBase = 300
                )
            )
        ),
        onDrawer = {}
    )
}