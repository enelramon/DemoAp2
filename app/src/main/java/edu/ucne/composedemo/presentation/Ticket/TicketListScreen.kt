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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import edu.ucne.composedemo.data.local.entities.TicketEntity
import edu.ucne.composedemo.presentation.components.TopBarComponent
import edu.ucne.composedemo.ui.theme.DemoAp2Theme

@Composable
fun TicketListScreen(
    viewModel: TicketViewModel = hiltViewModel(),
    goToTicket: (Int) -> Unit,
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
    goToTicket: (Int) -> Unit,
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
                    TicketCart(
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
private fun TicketCart (
    ticket: TicketEntity,
    date: String,
    goToTicket: (Int) -> Unit
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable {
                goToTicket(ticket.ticketId ?: 0)
            },
        elevation = CardDefaults.cardElevation()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row() {
                Text("Ticket #: ${ticket.ticketId}")
                Text("Date: ${date}")
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text("Empresa: ${ticket.cliente}")
            Text("Asunto: ${ticket.asunto}")
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun TicketPreview() {
    val sampleTickets = listOf(
        TicketEntity(ticketId = 1, cliente = "Juan Pérez", asunto = "Revisión técnica"),
        TicketEntity(ticketId = 2, cliente = "Ana Gómez", asunto = "Solicitud de garantía"),
        TicketEntity(ticketId = 3, cliente = "Carlos Sánchez", asunto = "Consulta general")
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