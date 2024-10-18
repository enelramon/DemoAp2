package edu.ucne.composedemo.presentation.supplidorGastos

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
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.composedemo.data.local.entities.SuplidorGastoEntity
import edu.ucne.composedemo.presentation.navigation.Screen

@Composable
fun SuplidorGastosListScreen(
    viewModel: SuplidorGastosViewModel = hiltViewModel(),
    onGoCreate: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    SuplidorGastosBodyListCreen(
        uiState = uiState,
        onGoCreate= onGoCreate
    )
}

@Composable
fun SuplidorGastosBodyListCreen(
    uiState: UiState,
    onGoCreate: () -> Unit
) {
    Scaffold(
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
                    .padding(horizontal = 15.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    "Nombre",
                    modifier = Modifier.weight(1f)
                )
                Text(
                    "Direccion",
                    modifier = Modifier.weight(1f)
                )
                Text(
                    "Telefono",
                    modifier = Modifier.weight(1f)
                )
                Text(
                    "Fax",
                    modifier = Modifier.weight(1f)
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
                    .padding(horizontal = 15.dp)
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
    suplidorGastoEntity: SuplidorGastoEntity
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Text(
            text = suplidorGastoEntity.nombres,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = suplidorGastoEntity.direccion,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = suplidorGastoEntity.telefono,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = suplidorGastoEntity.fax,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = suplidorGastoEntity.rnc,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = suplidorGastoEntity.email,
            modifier = Modifier.weight(1f)
        )
    }
}