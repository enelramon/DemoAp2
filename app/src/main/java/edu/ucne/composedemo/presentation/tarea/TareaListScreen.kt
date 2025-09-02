package edu.ucne.composedemo.presentation.tarea

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.composedemo.R
import edu.ucne.composedemo.data.local.entities.TareaEntity
import edu.ucne.composedemo.presentation.components.TopBarComponent

@Composable
fun TareaListScreen(
    viewModel: TareaViewModel = hiltViewModel(),
    goToTarea: (Int?) -> Unit,
    onDrawer: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    DesplegarListado(
        uiState = uiState,
        goToTarea = { goToTarea(it) },
        onDeleteClick = { viewModel.onEvent(TareaEvent.Delete(it)) },
        onDrawer = onDrawer,
    )
}

@Composable
fun DesplegarListado(
    uiState: TareaUiState,
    goToTarea: (Int?) -> Unit,
    onDeleteClick: (TareaEntity) -> Unit,
    onDrawer: () -> Unit
) {
    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    goToTarea(0)
                },
                icon = {
                    Icon(Icons.Filled.Add, stringResource(R.string.agregar_tarea))
                },
                text = { Text(stringResource(R.string.btn_agregar_tarea)) }
            )
        },
        topBar = {
            TopBarComponent(
                title = "Tareas",
                onActionClick = onDrawer
            )
        }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            contentPadding = innerPadding
        ) {
            items(uiState.listaTareas) { tarea ->
                TareaRow(
                    tarea = tarea,
                    onEditClick = { goToTarea(tarea.tareaId) },
                    onDeleteClick = { onDeleteClick(tarea) },
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewListado() {
    DesplegarListado(
        uiState = TareaUiState(
            listaTareas = listOf(
                TareaEntity(
                    tareaId = 1,
                    descripcion = "Descripcion",
                    tiempo = 1
                ),
                TareaEntity(
                    tareaId = 2,
                    descripcion = "Descripcion",
                    tiempo = 2
                ),
            )
        ),
        onDeleteClick = {},
        goToTarea = {},
        onDrawer = {},
    )
}
