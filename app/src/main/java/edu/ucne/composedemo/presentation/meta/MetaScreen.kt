package edu.ucne.composedemo.presentation.meta

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import edu.ucne.composedemo.presentation.components.TopBarComponent

@Composable
fun MetaScreen(
    onDrawer: () -> Unit
){
    MetaBodyScreen(
        onDrawer = onDrawer
    )
}

@Composable
private fun MetaBodyScreen(
    onDrawer: () -> Unit
) {
    val progress = 0.75f

    Scaffold(
        topBar = {
            TopBarComponent(
                title = "Registro de Metas",
                onMenuClick = onDrawer
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    progress = { progress },
                    modifier = Modifier.size(80.dp),
                    color = MaterialTheme.colorScheme.primary,
                    strokeWidth = 6.dp,
                    trackColor = ProgressIndicatorDefaults.circularIndeterminateTrackColor,
                )
                Text(
                    text = "${(progress * 100).toInt()}%",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(
                onClick = {},
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Cerrar Meta")
            }

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                label = { Text("Encargado") },
                value = "Enel R. Almonte P.",
                onValueChange = {},
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                label = { Text("% Logrado") },
                value = "100%",
                onValueChange = {},
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Tickets Seleccionados",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally) // Centralizado
                            .padding(bottom = 8.dp)
                    )
                    TicketList(
                        tickets = listOf(
                            TicketPrueba(1, "Solicitado", "Empresa 1", "Asunto 1", "Finalizado"),
                            TicketPrueba(2, "En Proceso", "Empresa 2", "Asunto 2", "Pendiente"),
                            TicketPrueba(3, "Completado", "Empresa 3", "Asunto 3", "Finalizado")
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun TicketList(tickets: List<TicketPrueba>) {
    LazyColumn {
        items(tickets) { ticket ->
            TicketRow(ticket)
            HorizontalDivider()
        }
    }
}

@Composable
private fun TicketRow(ticket: TicketPrueba) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "ID: ${ticket.id}",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Estatus: ${ticket.estatus}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Empresa: ${ticket.empresa}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Asunto: ${ticket.asunto}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Estado Final: ${ticket.estatusFinal}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

data class TicketPrueba(
    val id: Int,
    val estatus: String,
    val empresa: String,
    val asunto: String,
    val estatusFinal: String
)

@Preview
@Composable
private fun MetaScreenPreview() {
    MetaScreen(
        onDrawer = {}
    )
}