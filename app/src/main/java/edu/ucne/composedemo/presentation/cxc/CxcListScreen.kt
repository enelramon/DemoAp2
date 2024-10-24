package edu.ucne.composedemo.presentation.cxc

import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.composedemo.data.remote.dto.CxcDto
import edu.ucne.composedemo.presentation.components.TopBarComponent
import kotlinx.coroutines.launch

@Composable
fun CxcListScreen(
    viewModel: CxcViewModel = hiltViewModel(),
    onDrawer: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    CxcListBody(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        onDrawer = onDrawer
    )
}

@Composable
fun CxcListBody(
    uiState: CxcUiState,
    onEvent: (CxcEvent) -> Unit,
    onDrawer: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBarComponent(
                title = "CXC",
                onDrawer
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onEvent(CxcEvent.GetCxc)
                }
            ) {
                Icon(Icons.Filled.Refresh, "Actualizar")
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Fecha", modifier = Modifier.weight(0.30f))
                Text(text = "Monto", modifier = Modifier.weight(0.30f))
                Text(text = "Balance", modifier = Modifier.weight(0.30f))
            }
            HorizontalDivider()

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                if (uiState.isLoading) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .wrapContentSize(Alignment.Center)
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                } else {
                    items(uiState.cxc, key = { it.idVenta }) { cxc ->
                        val coroutineScope = rememberCoroutineScope()
                        val dismissState = rememberSwipeToDismissBoxState(
                            confirmValueChange = { state ->
                                if (state == SwipeToDismissBoxValue.EndToStart) {
                                    coroutineScope.launch {

                                    }
                                    true
                                } else {
                                    false
                                }
                            }
                        )

                        SwipeToDismissBox(
                            state = dismissState,
                            enableDismissFromStartToEnd = false,
                            enableDismissFromEndToStart = false,
                            backgroundContent = {
                                val color by animateColorAsState(
                                    when (dismissState.targetValue) {
                                        SwipeToDismissBoxValue.Settled -> Color.Transparent
                                        SwipeToDismissBoxValue.EndToStart -> Color.Red
                                        SwipeToDismissBoxValue.StartToEnd -> TODO()
                                    },
                                    label = "Changing color"
                                )
                            },
                            modifier = Modifier
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { /* Manejar clic en el CXC */ }
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = cxc.fecha, modifier = Modifier.weight(0.30f))
                                Text(text = cxc.monto.toString(), modifier = Modifier.weight(0.30f))
                                Text(text = cxc.balance.toString(), modifier = Modifier.weight(0.30f))
                            }
                            HorizontalDivider(
                                modifier = Modifier.padding(horizontal = 20.dp)
                            )
                        }
                    }
                }
                if (uiState.errorMessage != "") {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .wrapContentSize(Alignment.Center)
                        ) {
                            Toast.makeText(
                                LocalContext.current,
                                uiState.errorMessage,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CxcListPreview() {
    val list = listOf(
        CxcDto(
            idVenta = 1,
            fecha = "2024-10-01",
            monto = 1000f,
            balance = 500f
        ),
        CxcDto(
            idVenta = 2,
            fecha = "2024-10-02",
            monto = 1500f,
            balance = 700f
        ),
        CxcDto(
            idVenta = 3,
            fecha = "2024-10-03",
            monto = 2000f,
            balance = 1000f
        )
    )

    CxcListBody(
        uiState = CxcUiState(cxc = list),
        onEvent = {},
        onDrawer = {}
    )
}
