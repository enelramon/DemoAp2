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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle


@Composable
fun TicketScreen(
    viewModel: TicketViewModel = hiltViewModel(),
    ticketId: Int,
    goBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    TicketBodyScreen(
        uiState = uiState,
        onClienteChange = viewModel::onClienteChange,
        onAsuntoChange = viewModel::onAsuntoChange,
        onTicketIdChange = viewModel::onTicketIdChange,
        saveTicket = viewModel::save,
        deleteTicket = viewModel::delete,
        nuevoTicket = viewModel::nuevo,
        goBack = goBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketBodyScreen(
    uiState: UiState,
    onClienteChange: (String) -> Unit,
    onAsuntoChange: (String) -> Unit,
    onTicketIdChange: (Int) -> Unit,
    saveTicket: () -> Unit,
    deleteTicket: () -> Unit,
    nuevoTicket: () -> Unit,
    goBack: () -> Unit
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Ticket")
                },
                navigationIcon = {
                    IconButton(onClick = goBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
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
                        onValueChange = onClienteChange,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        label = { Text(text = "Asunto") },
                        value = uiState.asunto ?: "",
                        onValueChange = onAsuntoChange,
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
                                nuevoTicket()
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
                                saveTicket()
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