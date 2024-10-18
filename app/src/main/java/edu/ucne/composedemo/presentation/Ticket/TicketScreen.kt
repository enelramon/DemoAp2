package edu.ucne.composedemo.presentation.Ticket

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.composedemo.presentation.components.TopBarComponent
import edu.ucne.composedemo.ui.theme.DemoAp2Theme


@Composable
fun TicketScreen(
    viewModel: TicketViewModel = hiltViewModel(),
    ticketId: Int,
    goBack: () -> Unit,
    onDrawer: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    TicketBodyScreen(
        uiState = uiState,
        viewModel::onEvent,
        goBack = goBack,
        onDrawer = onDrawer
    )
}

@Composable
fun TicketBodyScreen(
    uiState: TicketUiState,
    onEvent: (TicketEvent) -> Unit,
    goBack: () -> Unit,
    onDrawer: () -> Unit
) {

    Scaffold(
        topBar = {
            TopBarComponent(
                title = "Registro de Tickets",
                onDrawer
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(8.dp)
        ) {
            ElevatedCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {

                    OutlinedTextField(
                        label = { Text(text = "Cliente") },
                        value = uiState.cliente ?: "",
                        onValueChange = { onEvent(TicketEvent.ClienteChange(it)) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        label = { Text(text = "Asunto") },
                        value = uiState.asunto ?: "",
                        onValueChange = { onEvent(TicketEvent.AsuntoChange(it)) } ,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.padding(2.dp))
                    uiState.errorMessage?.let {
                        Text(text = it, color = Color.Red)
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        OutlinedButton(
                            onClick = {
                                onEvent(TicketEvent.New)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "new button"
                            )
                            Text(text = "Nuevo")
                        }
                        val scope = rememberCoroutineScope()
                        OutlinedButton(

                            onClick = {
                                onEvent(TicketEvent.Save)
                                goBack()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "save button"
                            )
                            Text(text = "Guardar")
                        }
                    }
                }
            }
//            TicketListScreen(uiState.tickets,)
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    DemoAp2Theme {

    }
}