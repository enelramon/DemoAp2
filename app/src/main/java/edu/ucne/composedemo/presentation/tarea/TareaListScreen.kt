package edu.ucne.composedemo.presentation.tarea

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.composedemo.data.local.entities.TareaEntity

@Composable
fun TareaListScreen(
    viewModel: TareasViewModel = hiltViewModel(),
    goToTarea: (Int) -> Unit,
    createTarea: () -> Unit,
    deleteTarea: ((TareaEntity) -> Unit)? = null
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    TareaListBodyScreen(
        uiState = uiState,
        goToTarea = goToTarea,
        createTarea = createTarea,
        deleteTarea = { tarea ->
            viewModel.onEvent(TareaEvent.TareaChange(tarea.tareaId ?: 0))
            viewModel.onEvent(TareaEvent.Delete)
        }
    )
}

@Composable
private fun TareaRow(
    tarea: TareaEntity,
    goToTarea: () -> Unit,
    deleteTarea: (TareaEntity) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Descripción:", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = tarea.descripcion, style = MaterialTheme.typography.titleMedium)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Tiempo:", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "${tarea.tiempo} hrs", style = MaterialTheme.typography.titleMedium)
                }
            }

            Row {
                IconButton(onClick = goToTarea) {
                    Icon(Icons.Default.Edit, contentDescription = "Editar", tint = MaterialTheme.colorScheme.primary)
                }
                IconButton(onClick = { deleteTarea(tarea) }) {
                    Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TareaListBodyScreen(
    uiState: TareaUiState,
    goToTarea: (Int) -> Unit,
    createTarea: () -> Unit,
    deleteTarea: (TareaEntity) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF6650a4))
                    .padding(vertical = 12.dp)
            ) {
                Text(
                    text = "Lista de Tareas",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontSize = 23.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = createTarea,
                containerColor = Color(0xFF43A047),
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFFE9E7EF), Color(0xFF6650a4))
                    )
                )
                .padding(paddingValues)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(uiState.tareas) { tarea ->
                    TareaRow(
                        tarea = tarea,
                        goToTarea = { goToTarea(tarea.tareaId ?: 0) },
                        deleteTarea = deleteTarea
                    )
                }
            }
        }
    }
}