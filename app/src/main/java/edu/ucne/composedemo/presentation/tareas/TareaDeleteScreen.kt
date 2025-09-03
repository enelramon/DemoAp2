package edu.ucne.joserivera_ap2_p1.presentation.tareas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.ucne.composedemo.presentation.tareas.TareaviewModel

@Composable
fun DeleteTareaScreen(
    viewModel: TareaviewModel,
    tareaId: Int,
    goBack: () -> Unit
) {
    val tarea = viewModel.getTareaById(tareaId)

    if (tarea == null) {
        Text(
            text = "Tarea no encontrada",
            modifier = Modifier.fillMaxSize(),
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Red
        )
        return
    }

    Scaffold(
        topBar = {
            Text(
                text = "¿Está seguro de eliminar esta tarea?",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                color = Color.Red,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFF6650a4), Color(0xFF9370DB))
                    )
                )
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Descripción: ${tarea.descripcion}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Text(
                        text = "Tiempo: ${tarea.tiempo} minutos",
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = {
                        viewModel.deleteTarea(tarea)
                        goBack()
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Eliminar",
                        tint = Color.Red
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Eliminar", color = Color.Red)
                }

                OutlinedButton(
                    onClick = goBack,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Cancelar",
                        tint = Color.Blue
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Cancelar", color = Color.Gray)
                }
            }
        }
    }
}