package edu.ucne.composedemo.presentation.entradashuacales.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.composedemo.domain.entradashuacales.model.EntradaHuacal
import edu.ucne.composedemo.presentation.components.TopBarComponent

@Composable
fun EntradaHuacalListScreen(
    onDrawer: () -> Unit = {},
    goToEdit: (Int) -> Unit = {},
    createNew: () -> Unit = {},
    viewModel: EntradaHuacalListViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    
    LaunchedEffect(state.navigateToCreate) {
        if (state.navigateToCreate) {
            createNew()
            viewModel.onNavigationHandled()
        }
    }

    LaunchedEffect(state.navigateToEditId) {
        state.navigateToEditId?.let {
            goToEdit(it)
            viewModel.onNavigationHandled()
        }
    }

    EntradaHuacalListBody(
        state = state,
        onEvent = viewModel::onEvent,
        onDrawer = onDrawer
    )
}

@Composable
fun EntradaHuacalListBody(
    state: EntradaHuacalListUiState,
    onEvent: (EntradaHuacalListUiEvent) -> Unit,
    onDrawer: () -> Unit
) {
    var showFilters by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopBarComponent(
                title = "Entradas de Huacales",
                onDrawer = onDrawer
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEvent(EntradaHuacalListUiEvent.CreateNew) }
            ) {
                Icon(Icons.Filled.Add, "Crear entrada")
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // Filter Section
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "Filtros",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Row {
                                IconButton(onClick = { showFilters = !showFilters }) {
                                    Icon(Icons.Filled.FilterList, "Mostrar filtros")
                                }
                                if (state.filterNombreCliente.isNotBlank() || 
                                    state.filterFechaInicio.isNotBlank() || 
                                    state.filterFechaFin.isNotBlank()) {
                                    IconButton(onClick = { onEvent(EntradaHuacalListUiEvent.ClearFilters) }) {
                                        Icon(Icons.Filled.Clear, "Limpiar filtros")
                                    }
                                }
                            }
                        }

                        if (showFilters) {
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = state.filterNombreCliente,
                                onValueChange = { onEvent(EntradaHuacalListUiEvent.FilterByCliente(it)) },
                                label = { Text("Nombre Cliente") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = state.filterFechaInicio,
                                onValueChange = { onEvent(EntradaHuacalListUiEvent.FilterByFechaInicio(it)) },
                                label = { Text("Fecha Inicio (YYYY-MM-DD)") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = state.filterFechaFin,
                                onValueChange = { onEvent(EntradaHuacalListUiEvent.FilterByFechaFin(it)) },
                                label = { Text("Fecha Fin (YYYY-MM-DD)") },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }

                if (state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(16.dp)
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                    ) {
                        items(state.entradas) { entrada ->
                            EntradaHuacalCard(
                                entrada = entrada,
                                onClick = { onEvent(EntradaHuacalListUiEvent.Edit(entrada.idEntrada)) },
                                onDelete = { onEvent(EntradaHuacalListUiEvent.Delete(entrada.idEntrada)) }
                            )
                        }
                    }

                    // Summary Card
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                "Resumen",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Total de Huacales:")
                                Text(
                                    "${state.totalEntradas}",
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Total en Precio:")
                                Text(
                                    "${'$'}${String.format("%.2f", state.totalPrecio)}",
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EntradaHuacalCard(
    entrada: EntradaHuacal,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    entrada.nombreCliente,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text("Fecha: ${entrada.fecha}")
                Text("Cantidad: ${entrada.cantidad}")
                Text("Precio: ${'$'}${String.format("%.2f", entrada.precio)}")
                Text(
                    "Total: ${'$'}${String.format("%.2f", entrada.precio * entrada.cantidad)}",
                    fontWeight = FontWeight.Bold
                )
            }
            TextButton(onClick = onDelete) {
                Text("Eliminar")
            }
        }
    }
}
