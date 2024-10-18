package edu.ucne.composedemo.presentation.Ticket

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.composedemo.data.local.entities.TicketEntity
import edu.ucne.composedemo.presentation.components.TopBarComponent

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
            Spacer(modifier = Modifier.height(32.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(uiState.tickets) {
                    TicketRow(
                        it,
                        goToTicket,
                        createTicket
                    )
                }
            }
        }
    }
}
@Composable
private fun TicketRow(
    it: TicketEntity,
    goToTicket: (Int) -> Unit,
    createTicket: () -> Unit
) {

        Column(
            modifier = Modifier
                .fillMaxSize()

        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable {
                    goToTicket(it.ticketId ?: 0)
                }
            ) {
                Text(modifier = Modifier.weight(1f), text = it.ticketId.toString())
                Text(
                    modifier = Modifier.weight(2f),
                    text = it.cliente,
                    style = MaterialTheme.typography.headlineLarge
                )
                Text(modifier = Modifier.weight(2f), text = it.asunto)
            }
        }
        HorizontalDivider()

}
