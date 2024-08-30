package edu.ucne.composedemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import edu.ucne.composedemo.data.local.database.TicketDb
import edu.ucne.composedemo.data.local.entities.TicketEntity
import edu.ucne.composedemo.presentation.navigation.Screen
import edu.ucne.composedemo.ui.theme.DemoAp2Theme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var ticketDb: TicketDb

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        ticketDb = Room.databaseBuilder(
            applicationContext,
            TicketDb::class.java,
            "Ticket.db"
        ).fallbackToDestructiveMigration()
            .build()

        setContent {
            DemoAp2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        val navController = rememberNavController()
                        DemoAp2NavHost(navController)
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TicketScreen(
        goTicketList: () -> Unit
    ) {
        var cliente by remember { mutableStateOf("") }
        var asunto by remember { mutableStateOf("") }
        var errorMessage: String? by remember { mutableStateOf(null) }

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = { Text(text = "Registro Tickets") },
                    navigationIcon = {
                        IconButton(onClick = goTicketList) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Lista"
                            )
                        }
                    }
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(8.dp)
            ) {
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {

                        OutlinedTextField(
                            label = { Text(text = "Cliente") },
                            value = cliente,
                            onValueChange = { cliente = it },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            label = { Text(text = "Asunto") },
                            value = asunto,
                            onValueChange = { asunto = it },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.padding(2.dp))
                        errorMessage?.let {
                            Text(text = it, color = Color.Red)
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            OutlinedButton(
                                onClick = {

                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "new button"
                                )
                                Text(text = "Nuevo")
                            }
                            val scope = rememberCoroutineScope()
                            OutlinedButton(
                                onClick = {
                                    if (cliente.isBlank())
                                        errorMessage = "Nombre vacio"

                                    scope.launch {
                                        saveTicket(
                                            TicketEntity(
                                                cliente = cliente,
                                                asunto = asunto
                                            )
                                        )
                                        cliente = ""
                                        asunto = ""
                                    }
                                    goTicketList()
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "save button"
                                )
                                Text(text = "Guardar")
                            }
                        }
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TicketListScreen(
        ticketList: List<TicketEntity>,
        onAddTicket: () -> Unit
    ) {
        Scaffold (
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "Tickets")
                        }

                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(onClick = onAddTicket) {
                    Icon(Icons.Filled.Add, "Agregar nueva entidad")
                }
            }
        ){
            Column(
                modifier = Modifier.fillMaxSize()
                    .padding(it)
            ) {
                Spacer(modifier = Modifier.height(32.dp))

                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(ticketList) {
                        TicketRow(it)
                    }
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

    private suspend fun saveTicket(ticket: TicketEntity) {
        ticketDb.ticketDao().save(ticket)
    }

    @Composable
    fun DemoAp2NavHost(
        navHostController: NavHostController
    ){
        val lifecycleOwner = LocalLifecycleOwner.current
        val ticketList by ticketDb.ticketDao().getAll()
            .collectAsStateWithLifecycle(
                initialValue = emptyList(),
                lifecycleOwner = lifecycleOwner,
                minActiveState = Lifecycle.State.STARTED
            )
        NavHost(
            navController = navHostController,
            startDestination = Screen.TicketList
        ){
            composable<Screen.TicketList>{
                TicketListScreen(
                    ticketList = ticketList,
                    onAddTicket = { navHostController.navigate(Screen.Ticket(0)) }
                )
            }
            composable<Screen.Ticket>{
                TicketScreen(
                    goTicketList = { navHostController.navigate(Screen.TicketList) }

                )
            }
        }
    }

    @Preview(showBackground = true, showSystemUi = true)
    @Composable
    fun Preview() {
        DemoAp2Theme {
            val ticketList = listOf(
                TicketEntity(1, "Enel ewsd4444444444", "Impresora"),
                TicketEntity(2, "Juan", "Cable de red"),
            )
            TicketListScreen(
                ticketList,
                onAddTicket = {}
            )
        }
    }
}


