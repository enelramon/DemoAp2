package edu.ucne.composedemo.presentation.suplidorGastos

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
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
import edu.ucne.composedemo.data.remote.dto.SuplidorGastoDto
import edu.ucne.composedemo.presentation.components.TopBarComponent

@Composable
fun SuplidorGastosListScreen(
    viewModel: SuplidorGastosViewModel = hiltViewModel(),
    onGoCreate: () -> Unit,
    onDrawer: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    SuplidorGastosBodyListCreen(
        uiState = uiState,
        onGoCreate= onGoCreate,
        onDrawer = onDrawer
    )
}

@Composable
fun SuplidorGastosBodyListCreen(
    uiState: UiState,
    onGoCreate: () -> Unit,
    onDrawer: () -> Unit
) {
    Scaffold(
        topBar = {
            TopBarComponent(
                title = "Sistemas",
                onDrawer
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onGoCreate
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = null)
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Text(
                "Lista de SuplidoresGastos",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    "Nombre",
                    modifier = Modifier.weight(1f)
                )
                Text(
                    "Direccion",
                    modifier = Modifier.weight(1.2f)
                )
                Text(
                    "Telefono",
                    modifier = Modifier.weight(1.2f)
                )
                Text(
                    "Fax",
                    modifier = Modifier.weight(1.1f)
                )
                Text(
                    "Rnc",
                    modifier = Modifier.weight(1f)
                )
                Text(
                    "Email",
                    modifier = Modifier.weight(1f)
                )
            }
            if(uiState.isLoading){
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(uiState.suplidoresGastos){
                    SuplidoresGastosRowList(it)
                }
            }
        }
    }
}


@Composable
fun SuplidoresGastosRowList(
    suplidorGasto: SuplidorGastoDto
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Text(
            text = suplidorGasto.nombres,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = suplidorGasto.direccion,
            modifier = Modifier.weight(1.2f)
        )
        Text(
            text = suplidorGasto.telefono,
            modifier = Modifier.weight(1.1f)
        )
        Text(
            text = suplidorGasto.fax,
            modifier = Modifier.weight(1.1f)
        )
        Text(
            text = suplidorGasto.rnc,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = suplidorGasto.email,
            modifier = Modifier.weight(1f)
        )
    }
}