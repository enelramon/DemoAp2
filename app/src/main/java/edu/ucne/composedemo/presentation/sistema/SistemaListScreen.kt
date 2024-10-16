package edu.ucne.composedemo.presentation.sistema

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
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import edu.ucne.composedemo.data.remote.dto.SistemaDto
import edu.ucne.composedemo.ui.theme.DemoAp2Theme

@Composable
fun SistemaListScreen(
    viewModel: SistemaViewModel = hiltViewModel(),
    goToSistema: (Int) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    SistemaListBodyScreen(
        uiState,
        goToSistema,
    )
}
@Composable
fun SistemaListBodyScreen(
    uiState: SistemaUiState,
    goToSistema: (Int) -> Unit
) {
    Scaffold(modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {}
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Refresh"
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            Text("Lista de Sistemas")

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(uiState.sistemas) {
                    SistemaRow(
                        it,
                        goToSistema
                    )
                }
            }
        }
    }
}
@Composable
private fun SistemaRow(
    it: SistemaDto,
    goToSistema: (Int) -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                goToSistema(it.sistemaId)
            }
        ) {
            Text(modifier = Modifier.weight(1f), text = it.sistemaId.toString())
            Text(
                modifier = Modifier.weight(2f),
                text = it.nombre,
            )
        }
    }
    HorizontalDivider()
}

@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    DemoAp2Theme {
        SistemaListBodyScreen(
            uiState = SistemaUiState(
                sistemas = listOf(
                    SistemaDto(1, "Sistema 1"),
                    SistemaDto(2, "Sistema 2"),
                    SistemaDto(3, "Sistema 3"),
                    SistemaDto(4, "Sistema 4"),
                    SistemaDto(5, "Sistema 5"),
                    SistemaDto(6, "Sistema 6"),
                    SistemaDto(7, "Sistema 7"),
                    SistemaDto(8, "Sistema 8"),
                    SistemaDto(9, "Sistema 9"),
                    SistemaDto(10, "Sistema 10"),
                )
            ),
            goToSistema = {}
        )
    }
}