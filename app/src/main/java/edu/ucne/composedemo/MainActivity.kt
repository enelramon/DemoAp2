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
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Upsert
import edu.ucne.composedemo.ui.theme.DemoAp2Theme
import kotlinx.coroutines.flow.Flow
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
                        TicketScreen()
                    }
                }
            }
        }
    }

    @Composable
    fun TicketScreen(
    ) {
        var cliente by remember { mutableStateOf("") }
        var asunto by remember { mutableStateOf("") }
        var errorMessage: String? by remember { mutableStateOf(null) }

        Scaffold { innerPadding ->
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

                val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
                val ticketList by ticketDb.ticketDao().getAll()
                    .collectAsStateWithLifecycle(
                        initialValue = emptyList(),
                        lifecycleOwner = lifecycleOwner,
                        minActiveState = Lifecycle.State.STARTED
                    )
                TicketListScreen(ticketList)
            }
        }
    }

    @Composable
    fun TicketListScreen(ticketList: List<TicketEntity>) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            Text("Lista de tickets")

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(ticketList) {
                    TicketRow(it)
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
    @Entity(tableName = "Tickets")
    data class TicketEntity(
        @PrimaryKey
        val ticketId: Int? = null,
        val cliente: String = "",
        val asunto: String = ""
    )


    @Dao
    interface TicketDao {
        @Upsert()
        suspend fun save(ticket: TicketEntity)

        @Query(
            """
        SELECT * 
        FROM Tickets 
        WHERE ticketId=:id  
        LIMIT 1
        """
        )
        suspend fun find(id: Int): TicketEntity?

        @Delete
        suspend fun delete(ticket: TicketEntity)

        @Query("SELECT * FROM Tickets")
        fun getAll(): Flow<List<TicketEntity>>
    }

    @Database(
        entities = [
            TicketEntity::class
        ],
        version = 1,
        exportSchema = false
    )
    abstract class TicketDb : RoomDatabase() {
        abstract fun ticketDao(): TicketDao
    }

    @Preview(showBackground = true, showSystemUi = true)
    @Composable
    fun Preview() {
        DemoAp2Theme {
            val ticketList = listOf(
                TicketEntity(1, "Enel ewsd4444444444", "Impresora"),
                TicketEntity(2, "Juan", "Cable de red"),
            )
            TicketListScreen(ticketList)
        }
    }
}


