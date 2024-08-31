package edu.ucne.composedemo.presentation.Ticket

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import edu.ucne.composedemo.data.local.entities.TicketEntity

@Composable
fun TicketListScreen(ticketList: List<TicketEntity>) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Text("Lista de tickets")

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(ticketList) {
                TicketRow(it)
            }
        }
    }
}
@Composable
private fun TicketRow(it: TicketEntity) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(modifier = Modifier.weight(1f), text = it.ticketId.toString())
        Text(
            modifier = Modifier.weight(2f),
            text = it.cliente,
            style = MaterialTheme.typography.headlineLarge
        )
        Text(modifier = Modifier.weight(2f), text = it.asunto)
    }
    HorizontalDivider()
}
