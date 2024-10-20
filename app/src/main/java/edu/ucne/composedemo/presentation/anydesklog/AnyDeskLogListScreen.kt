package edu.ucne.composedemo.presentation.anydesklog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import edu.ucne.composedemo.data.remote.dto.AnyDeskLogDto
import edu.ucne.composedemo.presentation.components.TopBarComponent
import edu.ucne.composedemo.ui.theme.DemoAp2Theme

@Composable
fun AnyDeskLogListScreen(
    viewModel: AnyDeskLogViewModel = hiltViewModel(),
    onDrawer: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    AnyDeskLogBodyListScreen(
        uiState = uiState,
        isLoading = uiState.isLoading,
        onDrawer = onDrawer
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun AnyDeskLogBodyListScreen(
    uiState: AnyDeskLogUiState,
    isLoading: Boolean,
    onDrawer: () -> Unit
) {
    Scaffold(
        topBar = {
            TopBarComponent(
                title = "AnyDeskLogs",
                onDrawer
            )
        },
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(align = Alignment.Center)
                )
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(horizontal = 16.dp)
                ) {
                    Row(
                        modifier = Modifier
                    ) {
                        Text(
                            text = "IdCliente",
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Cliente",
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Cantidad",
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Minutos",
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(uiState.anyDeskLogs) { anyDeskLog ->
                            AnyDeskLogRow(
                                anyDeskLog = anyDeskLog
                            )
                        }
                        uiState.errorMessage.let {
                            item {
                                Text(text = it)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AnyDeskLogRow(
    anyDeskLog: AnyDeskLogDto
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                text = anyDeskLog.idCliente.toString()
            )
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                text = anyDeskLog.cliente
            )
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                text = anyDeskLog.cantidad.toString()
            )
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                text = anyDeskLog.minutos.toString()
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun AnyDeskLogListPreview() {
    DemoAp2Theme {
        AnyDeskLogBodyListScreen(
            uiState = AnyDeskLogUiState(
                isLoading = false,
                errorMessage = "",
                anyDeskLogs = listOf(
                    AnyDeskLogDto(1, "Ferreteria Gama", 1.0, 10),
                    AnyDeskLogDto(2, "Ferreteria mireserba", 2.0, 15),
                    AnyDeskLogDto(3, "Supermercado Yoma", 3.0, 20)
                )
            ),
            isLoading = false,
            onDrawer = {}
        )
    }
}
