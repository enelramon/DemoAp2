package edu.ucne.composedemo.presentation.Ticket

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
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
import edu.ucne.composedemo.data.remote.dto.TicketDto
import edu.ucne.composedemo.presentation.components.TopBarComponent
import edu.ucne.composedemo.ui.theme.DemoAp2Theme

@Composable
fun TicketListScreen(
    viewModel: TicketViewModel = hiltViewModel(),
    goToTicket: (Double) -> Unit,
    createTicket: () -> Unit,
    onDrawer: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    TicketListBodyScreen(
        uiState,
        goToTicket,
        createTicket,
        onDrawer
    )
}

@Composable
fun TicketListBodyScreen(
    uiState: TicketUiState,
    goToTicket: (Double) -> Unit,
    createTicket: () -> Unit,
    onDrawer: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBarComponent(
                title = "Tickets",
                onDrawer
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = createTicket
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Create a new Ticket"
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding)
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ){Spacer(modifier = Modifier.height(32.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ){
                    RadioButton(
                        selected = false,
                        onClick = {}
                    )
                    Text("By user")

                    Spacer(modifier = Modifier.width(10.dp))

                    RadioButton(
                        selected = true,
                        onClick = {}
                    )
                    Text("By customer")

                    Spacer(modifier = Modifier.width(50.dp))

                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = null
                        )
                    }
                }

                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = {
                        IconButton(
                            onClick = {}
                        ) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowDown,
                                contentDescription = null
                            )
                        }
                    }
                )

                Spacer(modifier = Modifier.height(4.dp))
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(uiState.tickets) {
                    TicketCard(
                        it,
                        date = "12/08/2023",
                        goToTicket
                    )
                }
            }
        }
    }
}

@Composable
fun TicketCard (
    ticket: TicketDto,
    date: String,
    goToTicket: (Double) -> Unit
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable {
                goToTicket(ticket.idTicket ?: 0.0)
            },
        elevation = CardDefaults.cardElevation()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row() {
                Text("Ticket #: ${ticket.idTicket}")
                Text("Date: ${date}")
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text("Empresa: ${ticket.idTicket}")
            Text("Asunto: ${ticket.asunto}")
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun TicketPreview() {
    val sampleTickets = listOf(
        TicketDto(
            idTicket = 1.1,
            fecha = "2024-12-01",
            vence = "2024-12-10",
            idCliente = 1001.0,
            empresa = "Empresa A",
            solicitadoPor = "Juan Pérez",
            asunto = "Revisión técnica",
            prioridad = 1,
            idEncargado = 2001,
            estatus = 0,
            especificaciones = "Revisión completa del sistema",
            archivo = null
        ),
        TicketDto(
            idTicket = 2.1,
            fecha = "2024-12-02",
            vence = "2024-12-12",
            idCliente = 1002.0,
            empresa = "Empresa B",
            solicitadoPor = "Ana Gómez",
            asunto = "Solicitud de garantía",
            prioridad = 2,
            idEncargado = 2002,
            estatus = 1,
            especificaciones = "Reemplazo de piezas defectuosas",
            archivo = "garantia.pdf"
        ),
        TicketDto(
            idTicket = 3.1,
            fecha = "2024-12-03",
            vence = "2024-12-13",
            idCliente = 1003.0,
            empresa = "Empresa C",
            solicitadoPor = "Carlos Sánchez",
            asunto = "Consulta general",
            prioridad = 3,
            idEncargado = 2003,
            estatus = 2,
            especificaciones = "Consulta sobre el estado del proyecto",
            archivo = null
        )
    )

    val mockUiState = TicketUiState(tickets = sampleTickets)

    DemoAp2Theme {
        TicketListBodyScreen(
            uiState = mockUiState,
            goToTicket = {},
            createTicket = {},
            onDrawer = {}
        )
    }
}