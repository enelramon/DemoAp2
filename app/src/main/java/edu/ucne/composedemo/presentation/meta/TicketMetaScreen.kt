package edu.ucne.composedemo.presentation.meta

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.composedemo.data.remote.dto.TicketDto
import edu.ucne.composedemo.data.remote.dto.TicketMetaResponseDto
import edu.ucne.composedemo.presentation.Ticket.TicketCard
import edu.ucne.composedemo.presentation.components.TopBarComponent
import edu.ucne.composedemo.ui.theme.DemoAp2Theme

@Composable
fun TicketMetaScreen(
    idTicket: Int,
    onDrawer: () -> Unit,
    viewModel: TicketMetaViewModel = hiltViewModel()
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    TicketMetaBodyScreen(
        idTicket = idTicket,
        uiState = uiState,
        onEvent = viewModel::onEvent,
        onDrawer = onDrawer
    )
}

@Composable
private fun TicketMetaBodyScreen(
    idTicket: Int,
    uiState: TicketMetaUiState,
    onEvent: (TicketMetaUiEvent) -> Unit,
    onDrawer: () -> Unit
) {
    LaunchedEffect(Unit){
        onEvent(TicketMetaUiEvent.SelectedTicketMeta(idTicket))
    }

    val ticketCompletados = uiState.ticketMetas.count(
        predicate = { it.estatus == 1 }
    )

    val progress = ticketCompletados.toFloat() / uiState.ticketMetas.size.toFloat()
    
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
                value = "${progress * 100}",
                onValueChange = {},
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Tickets Seleccionados",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 8.dp)
            )
            LazyColumn {
                items(uiState.ticketMetas) { ticket ->
                    TicketCard(
                        ticket = TicketDto(
                            idTicket = ticket.idTicket,
                            fecha = "",
                            vence = "",
                            idCliente = 1001.0,
                            empresa = ticket.empresa,
                            solicitadoPor = "",
                            asunto = ticket.asunto,
                            prioridad = 1,
                            idEncargado = 2001,
                            estatus = ticket.estatus,
                            especificaciones = ticket.estatusDescription,
                            archivo = null
                        ),
                        date = "12/08/2023"
                    )
                }
            }
        }
    }
}

private val sampleTicketMetaUiSate = TicketMetaUiState(
    ticketMetas = listOf(
        TicketMetaResponseDto(
            id = 1,
            idTicket = 1,
            asunto = "Asunto 1",
            empresa = "Empresa 1",
            estatus = 1,
            estatusDescription = "Estatus 1"
        ),
        TicketMetaResponseDto(
            id = 2,
            idTicket = 2,
            asunto = "Asunto 2",
            empresa = "Empresa 2",
            estatus = 0,
            estatusDescription = "Estatus 2"
        ),
        TicketMetaResponseDto(
            id = 3,
            idTicket = 3,
            asunto = "Asunto 3",
            empresa = "Empresa 3",
            estatus = 1,
            estatusDescription = "Estatus 3"
        )
    )
)

@Preview
@Composable
private fun TicketMetaScreenPreview() {
    DemoAp2Theme {
        TicketMetaBodyScreen(
            uiState = sampleTicketMetaUiSate,
            idTicket = 1,
            onEvent = {},
            onDrawer = {}
        )
    }
}