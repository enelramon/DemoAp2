package edu.ucne.composedemo.presentation.conversacion


import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.ucne.composedemo.presentation.components.TopBarComponent

@Composable
fun ConversationScreen(

    onDrawer: () -> Unit
) {

    Scaffold(

        modifier = Modifier.fillMaxSize(),
        topBar = {

            TopBarComponent(
                title = "Tickets",
                 onDrawer,

            )
        },

    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp)
                    .border(1.dp, color = Color.Black),
                colors = CardDefaults.cardColors(containerColor = Color.White),



                ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Spacer(modifier = Modifier.height(16.dp))


                    MessageInputBlock(userName = "Enel R. Almonte P.")

                    Spacer(modifier = Modifier.height(16.dp))

                    MessagesBlock(
                        messages = listOf(
                            Message(
                                date = "12/12/2023 8:55:52 PM",
                                sender = "Enel R. Almonte P.",
                                content = "Favor indicar el número del contrato para poder identificar la razón."
                            ),
                            Message(
                                date = "12/13/2023 4:05:51 PM",
                                sender = "COMERCIAL DEL CAMPO",
                                content = "Esto era relacionado con lo contrato..."
                            )
                        )
                    )
                }
            }
    }
}
}


@Composable
fun MessagesBlock(messages: List<Message>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        messages.forEach { message ->
            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                Text(
                    text = "Fecha: ${message.date}",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Enviado por: ${message.sender}",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Mensaje:",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = message.content,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Divider()
        }
    }
}

@Composable
fun MessageInputBlock(userName: String) {
    Column(modifier = Modifier.fillMaxWidth()) {

        Text(
            text = "Nombre:",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = "$userName:",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Mensaje:",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface
        )



        var message by remember { mutableStateOf(TextFieldValue("")) }
        BasicTextField(
            value = message,
            onValueChange = { message = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(8.dp)
                .border(1.dp, MaterialTheme.colorScheme.primary)
                .padding(8.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))


        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally



        ) {

            Button(
                onClick = {  },
                colors = ButtonDefaults.buttonColors(Color.Blue)

            ) {
                Text("Enviar Mensaje")
            }
        }
    }
}

data class Message(val date: String, val sender: String, val content: String)

@Preview(showBackground = true)
@Composable
fun PreviewConversationScreen() {
    ConversationScreen(

        onDrawer = {  }
    )
}
