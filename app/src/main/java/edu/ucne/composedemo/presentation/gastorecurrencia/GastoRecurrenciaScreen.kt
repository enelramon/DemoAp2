package edu.ucne.composedemo.presentation.gastorecurrencia

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import edu.ucne.composedemo.presentation.components.ShowComponent
import edu.ucne.composedemo.presentation.components.TopBarComponent
import edu.ucne.composedemo.ui.theme.Green
import edu.ucne.composedemo.ui.theme.Red

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GastoRecurrenciaScreen(
    gastoRecurrenciaViewModel: GastoRecurrenciaViewModel = hiltViewModel(),
    onDrawer: () -> Unit = {},
    idSuplidor: Int = 0,
    navigateToSuplidoresGasto: () -> Unit = {},
) {
    val uiState by gastoRecurrenciaViewModel.uiState.collectAsState()
    val onEvent = gastoRecurrenciaViewModel::onEvent
    val focusManager = LocalFocusManager.current

    LaunchedEffect(uiState.suplidoresGastos,uiState.gastosRecurrencia) {
        onEvent(GastoRecurrenciaEvent.GetGastosRecurrencia(idSuplidor))
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBarComponent(
                title = "Gastos Recurrencia",
                onMenuClick = { onDrawer() }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(8.dp)
        ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Row (
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Switch(
                                checked = uiState.esRecurrente,
                                onCheckedChange = { onEvent(GastoRecurrenciaEvent.EsRecurenteChange(it)) },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = Color.White,
                                    uncheckedThumbColor = Color.White,
                                    checkedTrackColor = Green.copy(alpha = 0.8f),
                                    uncheckedTrackColor = Red
                                ),
                                thumbContent =
                                {
                                    ShowComponent(
                                        value = uiState.esRecurrente,
                                        whenContentIsTrue = {
                                            Icon(
                                                imageVector = Icons.Filled.Check,
                                                contentDescription = null,
                                                modifier = Modifier.size(SwitchDefaults.IconSize),
                                            )
                                        },
                                        whenContentIsFalse = {
                                            Icon(
                                                imageVector = Icons.Filled.Cancel,
                                                contentDescription = null,
                                                modifier = Modifier.size(SwitchDefaults.IconSize),
                                                tint = Color.White
                                            )
                                        }
                                    )
                                }
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text ="Recurrente",
                                modifier = Modifier.padding(end = 8.dp),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                    }
                    ShowComponent(
                        value = uiState.esRecurrente,
                        whenContentIsTrue = {
                            InputSelectRecurrence(uiState.periodicidad){
                                onEvent(GastoRecurrenciaEvent.PeriodicidadChange(it.toString()))
                            }
                            ShowComponent(
                                value = uiState.errorPeriodicidad.isNotBlank(),
                                whenContentIsTrue = {
                                    Text(
                                        text = uiState.errorPeriodicidad,
                                        color = Red,
                                        style = MaterialTheme.typography.bodySmall,
                                        textAlign = TextAlign.Start
                                    )
                                }
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            InputSelectDia(
                                range = 1..28,
                                value = uiState.dia
                            ){
                                onEvent(GastoRecurrenciaEvent.DiaChange(it.toString()))
                            }
                            ShowComponent(
                                value = uiState.errorDia.isNotBlank(),
                                whenContentIsTrue = {
                                    Text(
                                        text = uiState.errorDia,
                                        color = Red,
                                        style = MaterialTheme.typography.bodySmall,
                                        textAlign = TextAlign.Start
                                    )
                                }
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    keyboardType = KeyboardType.Number,
                                    imeAction = ImeAction.Done
                                ),
                                keyboardActions = KeyboardActions(
                                    onDone = { focusManager.clearFocus() }
                                ),
                                value = uiState.monto.toString(),
                                onValueChange = {onEvent(GastoRecurrenciaEvent.MontoChange(it))},
                                label = { Text("Monto") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            ShowComponent(
                                value = uiState.errorMonto.isNotBlank(),
                                whenContentIsTrue = {
                                    Text(
                                        text = uiState.errorMonto,
                                        color = Red,
                                        style = MaterialTheme.typography.bodySmall,
                                        textAlign = TextAlign.Start
                                    )
                                }
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    colors = ButtonDefaults.buttonColors(Green),
                    onClick = {
                        focusManager.clearFocus()
                        onEvent(
                            GastoRecurrenciaEvent
                                .SaveGastoRecurrencia(navigateToSuplidoresGasto)
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Filled.Save,
                        contentDescription = "Guardar"
                    )
                    Text(text = "Guardar")
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center)
                ) {
                    ShowComponent(
                        value = uiState.errorMessage.isNotBlank(),
                        whenContentIsTrue = {
                            Toast.makeText(
                                LocalContext.current,
                                uiState.errorMessage,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    )
                }
            }
        }

        ShowComponent(
            value = uiState.isLoading,
            whenContentIsTrue = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.5f))
                        .clickable(enabled = false) {}
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(150.dp),
                        color = Color.White,
                        strokeWidth = 10.dp,
                    )
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputSelectRecurrence(
    value: Int = 1,
    onChange: (Int) -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }
    val options = listOf("Mensual" to 1, "Anual" to 2)
    val selectedOption = options.find { it.second == value }?.first ?: ""

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            readOnly = true,
            value = selectedOption,
            onValueChange = {},
            label = { Text("Recurrencia") },
            modifier = Modifier
                .menuAnchor(MenuAnchorType.SecondaryEditable, true)
                .fillMaxWidth(),
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            }
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { (label, intValue) ->
                DropdownMenuItem(
                    text = { Text(label) },
                    onClick = {
                        onChange(intValue)
                        expanded = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputSelectDia(
    range: IntRange = 1..28,
    value: Int= 0,
    onChange: (Int) -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }
    val selectedDay = if (value in range) value.toString() else ""

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            readOnly = true,
            value = selectedDay,
            onValueChange = {},
            label = { Text("Seleccionar Día") },
            modifier = Modifier
                .menuAnchor(MenuAnchorType.PrimaryEditable, true)
                .fillMaxWidth(),
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            }
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.height(200.dp)
        ) {
            range.forEach { day ->
                DropdownMenuItem(
                    text = { Text(day.toString()) },
                    onClick = {
                        onChange(day)
                        expanded = false
                    }
                )
            }
        }
    }
}