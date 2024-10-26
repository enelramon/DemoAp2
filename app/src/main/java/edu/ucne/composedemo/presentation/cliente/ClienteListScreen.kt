package edu.ucne.composedemo.presentation.cliente

import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.composedemo.data.remote.dto.ClienteDto
import edu.ucne.composedemo.presentation.components.TopBarComponent
import kotlinx.coroutines.launch

@Composable
fun ClienteListScreen(
    viewModel: ClienteViewModel = hiltViewModel(),
    onDrawer: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    ClienteListBody(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        onDrawer = onDrawer
    )
}

@Composable
fun ClienteListBody(
    uiState: ClienteUiState,
    onEvent: (ClienteEvent) -> Unit,
    onDrawer: () -> Unit
) {
    Scaffold (
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBarComponent(
                title = "Clientes",
                onDrawer
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onEvent(ClienteEvent.GetClientes)
                }
            ) {
                Icon(Icons.Filled.Refresh, "Agregar")
            }
        }
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Cliente", modifier = Modifier.weight(0.10f))
                Text(text = "Empresa", modifier = Modifier.weight(0.10f))
                Text(text = "Celular", modifier = Modifier.weight(0.10f))
            }
            HorizontalDivider()
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    if (uiState.isLoading) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .wrapContentSize(Alignment.Center)
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }

                items(uiState.clientes, key = { it.codigoCliente }) { cliente ->
                    val coroutineScope = rememberCoroutineScope()
                    val dismissState = rememberSwipeToDismissBoxState(
                        confirmValueChange = { state ->
                            if (state == SwipeToDismissBoxValue.EndToStart) {
                                coroutineScope.launch {}
                                true
                            } else false
                        }
                    )

                    SwipeToDismissBox(
                        state = dismissState,
                        enableDismissFromStartToEnd = false,
                        enableDismissFromEndToStart = false,
                        backgroundContent = {
                            val color by animateColorAsState(
                                when (dismissState.targetValue) {
                                    SwipeToDismissBoxValue.Settled -> Color.Transparent
                                    SwipeToDismissBoxValue.EndToStart -> Color.Red
                                    SwipeToDismissBoxValue.StartToEnd -> TODO()
                                },
                                label = "Changing color"
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(color, shape = RoundedCornerShape(8.dp))
                                    .padding(16.dp),
                                contentAlignment = Alignment.CenterEnd
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = null,
                                    tint = Color.White
                                )
                            }
                        }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { }
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = cliente.nombres, modifier = Modifier.weight(0.12f))
                            Text(text = cliente.empresa, modifier = Modifier.weight(0.12f))
                            Text(text = cliente.celular, modifier = Modifier.weight(0.12f))
                        }
                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 20.dp)
                        )
                    }
                }

                if (uiState.errorMessage.isNotEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .wrapContentSize(Alignment.Center)
                        ) {
                            Toast.makeText(LocalContext.current,
                                uiState.errorMessage,
                                Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ClienteListPreview() {
    val list = listOf(
        ClienteDto(
            codigoCliente = 1,
            nombres = "Juan Perez",
            empresa = "Empresa 1",
            direccion = "Direccion 1",
            telefono = "809-123-4567",
            celular = "829-456-9435",
            rnc = "111111111",
            tieneIguala = true,
            tipoComprobante = 1
        ),
        ClienteDto(
            codigoCliente = 2,
            nombres = "Maria Perez",
            empresa = "Empresa 2",
            direccion = "Direccion 2",
            telefono = "809-300-9212",
            celular = "829-452-9870",
            rnc = "222222222",
            tieneIguala = true,
            tipoComprobante = 2
        )
    )

    ClienteListBody(
        uiState = ClienteUiState(clientes = list),
        onEvent = {},
        onDrawer = {}
    )
}