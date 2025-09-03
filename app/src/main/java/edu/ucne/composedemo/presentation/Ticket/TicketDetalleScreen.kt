package edu.ucne.composedemo.presentation.Ticket

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.composedemo.presentation.components.SimpleTopBarComponent
import edu.ucne.composedemo.ui.theme.DemoAp2Theme

@Composable
fun TicketDetalleScreen(
    viewModel: TicketViewModel = hiltViewModel(),
    goBack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    TicketDetalleBodyScreen(
        uiState = uiState,
        viewModel::onEvent,
        goBack = goBack,
    )
}

@Composable
fun TicketDetalleBodyScreen(
    uiState: TicketUiState,
    onEvent: (TicketEvent) -> Unit,
    goBack: () -> Unit,
) {

    var isEncargadoModalOpen by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            SimpleTopBarComponent(
                title = "",
                onBackClick = goBack
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding).padding(8.dp)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .border(width = 1.dp, color = Color(0xFF8F8E8E)),
                shape = RoundedCornerShape(10.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                colors = CardDefaults.cardColors(Color.White)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally, // Centrar elementos horizontalmente
                    verticalArrangement = Arrangement.Top
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .background(color = Color(0xFF5C90FF)),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            modifier = Modifier.padding(start = 16.dp),
                            text = "Detalles Tickets",
                            style = TextStyle(
                                fontSize = 30.sp,
                                fontWeight = FontWeight(400),
                                color = Color(0xFFFFFFFF),
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Button(
                                onClick = {},
                                colors = buttonColors(Color.Red)
                            ) {
                                Text(text = "Cerrar Ticket", color = Color.White)
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(
                                onClick = {},
                                colors = buttonColors(Color.Green)
                            ) {
                                Text(text = "Agregar Meta", color = Color.White)
                            }
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(text = "Ticket ID")
                            Text(text = "${uiState.idTicket}")
                            Text(
                                text = "Ver datos del cliente",
                                color = Color.Blue,
                                modifier = Modifier.clickable { }
                            )
                        }
                    }
                    Divider(
                        modifier = Modifier.fillMaxWidth(),
                        thickness = 1.dp,
                        color = Color.Black
                    )

                    DetalleRow(
                        labels = listOf("Fecha:", "Estatus:", "Prioridad:", "Clients:", "Sistema:", "Solicit por:"),
                        values = listOf(
                            uiState.fecha ?: "Pendiente",
                            uiState.solicitadoPor ?: "Pendiente",
                            "${uiState.prioridad ?: "Pendiente"}",
                            uiState.nombreCliente,
                            "${uiState.sistema}",
                            uiState.solicitadoPor ?: "Pendiente"
                        )
                    )
                    Divider(
                        modifier = Modifier.fillMaxWidth(),
                        thickness = 1.dp,
                        color = Color.Black
                    )

                    DetalleRow(
                        labels = listOf("Tipo:", "Asunto:", "Especificaciones:", "Encargado:"),
                        values = listOf(
                            uiState.fecha ?: "Pendiente",
                            uiState.asunto ?: "Pendiente",
                            uiState.especificaciones ?: "Pendiente",
                            uiState.idEncargado?.toString() ?: "No asignado"
                        )
                    )

                    if (isEncargadoModalOpen) {
                        EncargadoModal(
                            encargadosMap = mapOf(1 to "Juan Pérez", 2 to "Ana López", 3 to "Carlos Gómez"),
                            onEncargadoChange = {
                                onEvent(TicketEvent.EncargadoChange(it))
                                isEncargadoModalOpen = false
                            },
                            onDismiss = { isEncargadoModalOpen = false }
                        )
                    }

                    Divider(
                        modifier = Modifier.fillMaxWidth(),
                        thickness = 1.dp,
                        color = Color.Black
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(text = "Minutos Invertidos:", fontWeight = FontWeight.Bold)

                        OutlinedTextField(
                            value = uiState.minutosInvertidos?.toString() ?: "",
                            onValueChange = { nuevoValor ->
                                val minutos = nuevoValor.toIntOrNull() ?: 0
                                onEvent(TicketEvent.MinutosInvertidosChange(minutos))
                            },
                            label = { Text(text = "0") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            shape = RoundedCornerShape(15.dp),
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = {  },
                            colors = buttonColors(Color(0xFFFFA500)),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(text = "Esperar por cliente", color = Color.White)
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = { },
                            colors = buttonColors(Color(0xFF00BFFF)),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(text = "Finalizar", color = Color.White)
                        }
                    }


                }

            }
        }
    }
}

@Composable
fun EncargadoModal(
    encargadosMap: Map<Int, String>,
    onEncargadoChange: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = onDismiss
            ) {
                Text("Cerrar")
            }
        },
        text = {
            Column {
                Text("Selecciona un encargado:")
                encargadosMap.forEach { (id, nombre) ->
                    DropdownMenuItem(
                        onClick = {
                            onEncargadoChange(id)
                            onDismiss()
                        },
                        text = { Text(text = nombre) }
                    )
                }
            }
        }
    )
}

@Composable
fun DetalleRow(
    labels: List<String>,
    values: List<String>,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
    ) {
        Column(modifier = Modifier.weight(1f)) {
            labels.forEach { label ->
                Text(text = label, fontWeight = FontWeight.Bold)
            }
        }
        Column(modifier = Modifier.weight(1f)) {
            values.forEach { value ->
                Text(text = value)
            }
        }
    }
}

@Composable
fun SectionDivider() {
    Divider(
        modifier = Modifier.fillMaxWidth(),
        thickness = 1.dp,
        color = Color.Black
    )
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun Preview() {
    DemoAp2Theme {
        TicketDetalleBodyScreen(
            uiState = TicketUiState(
                idTicket = 101,
                fecha = "2024-12-03",
                solicitadoPor = "Juan Pérez",
                prioridad = 1,
                idCliente = 123.2,
                empresa = "Empresa A",
                asunto = "Problema con la conexión",
                especificaciones = "Fallas de red en toda la oficina",
                idEncargado = 1
            ),
            onEvent = {},
            goBack = {}
        )
    }
}
