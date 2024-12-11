package edu.ucne.composedemo.presentation.Ticket

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.composedemo.data.remote.dto.TicketDto
import edu.ucne.composedemo.presentation.components.TopBarComponent
import edu.ucne.composedemo.presentation.meta.TicketMetaUiEvent
import edu.ucne.composedemo.presentation.meta.TicketMetaViewModel
import edu.ucne.composedemo.ui.theme.DemoAp2Theme

@Composable
fun TicketListScreen(
    ticketViewModel: TicketViewModel = hiltViewModel(),
    ticketMetaViewModel: TicketMetaViewModel = hiltViewModel(),
    goToTicket: (Int) -> Unit,
    createTicket: () -> Unit,
    onDrawer: () -> Unit,
    goToMeta: (Int) -> Unit
) {
    val uiState by ticketViewModel.uiState.collectAsStateWithLifecycle()
    TicketListBodyScreen(
        uiState = uiState,
        onTicketMetaEvent = ticketMetaViewModel::onEvent,
        goToTicket = goToTicket,
        createTicket = createTicket,
        onDrawer = onDrawer,
        goToMeta = goToMeta
    )
}

@Composable
fun TicketListBodyScreen(
    uiState: TicketUiState,
    onTicketMetaEvent: (TicketMetaUiEvent) -> Unit,
    goToTicket: (Int) -> Unit,
    createTicket: () -> Unit,
    onDrawer: () -> Unit,
    goToMeta: (Int) -> Unit
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
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(10.dp)
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

                Spacer(modifier = Modifier.height(15.dp))
            }
            Button(
                onClick = { goToMeta(uiState.idCliente?.toInt() ?: 0) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF388E3C),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .height(32.dp)
                    .padding(horizontal = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Flag,
                    contentDescription = "Ver Meta",
                    modifier = Modifier.size(16.dp),
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Ver Meta",
                    fontSize = 12.sp
                )
            }
            Spacer(modifier = Modifier.height(15.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(uiState.tickets) {
                    TicketCard(
                        ticket = it,
                        date = "12/08/2023",
                        goToTicket = goToTicket,
                        onTicketMetaEvent = onTicketMetaEvent,
                        showAddMetaButton = true
                    )
                }
            }
        }
    }
}

@Composable
fun TicketCard(
    ticket: TicketDto,
    date: String,
    goToTicket: (Int) -> Unit = {},
    onTicketMetaEvent: (TicketMetaUiEvent) -> Unit = {},
    showAddMetaButton: Boolean = false
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable {
                goToTicket(ticket.idTicket ?: 0)
            },
        elevation = CardDefaults.cardElevation()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(text = "Ticket #: ${ticket.idTicket}")
                    Text(text = "Date: $date")
                }
                if(showAddMetaButton){
                    Button(
                        onClick = {
                            onTicketMetaEvent(TicketMetaUiEvent.Save(ticket.idTicket ?: 0))
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF1565C0),
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .height(32.dp)
                            .padding(horizontal = 8.dp),
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Agregar a la meta",
                            modifier = Modifier.size(16.dp),
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Agregar a la meta",
                            fontSize = 12.sp
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Empresa: ${ticket.idTicket}")
            Text(text = "Asunto: ${ticket.asunto}")
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun TicketPreview() {
    val sampleTickets = listOf(
        TicketDto(
            idTicket = 1,
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
            idTicket = 2,
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
            idTicket = 3,
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
            onDrawer = {},
            goToMeta = {},
            onTicketMetaEvent = {}
        )
    }
}