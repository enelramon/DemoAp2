package edu.ucne.composedemo.presentation.gastos

import android.app.DatePickerDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import edu.ucne.composedemo.presentation.components.TopBarComponent
import edu.ucne.composedemo.ui.theme.Green
import java.util.Calendar
import java.util.Locale

@Composable
fun GastosScreen(
    viewModel: GastosViewModel = hiltViewModel(),
    onDrawer: () -> Unit = {},
    idSuplidor: Int,
    navigateToSuplidorGasto: () -> Unit = {}
) {
    val uiState = viewModel.uiState.collectAsState().value
    val onEvent = viewModel::onEvent
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    val showDatePicker = remember { mutableStateOf(false) }

    LaunchedEffect(idSuplidor) {
        onEvent(GastosEvent.GetGasto(idSuplidor))
    }

    if (showDatePicker.value) {
        ShowDatePickerDialog(
            context = context,
            onDateSelected = { selectedDate ->
                onEvent(GastosEvent.FechaChange(selectedDate))
                showDatePicker.value = false
            }
        )
    }

    Scaffold(
        topBar = { TopBarComponent(title = "Registrar Gasto", onMenuClick = onDrawer) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GastoTextField(
                value = uiState.fecha,
                onValueChange = {},
                label = "Fecha",
                trailingIcon = {
                    IconButton(onClick = { showDatePicker.value = true }) {
                        Icon(
                            imageVector = Icons.Default.CalendarToday,
                            contentDescription = "Seleccionar Fecha"
                        )
                    }
                },
                error = uiState.errorFecha,
                readOnly = true
            )

            GastoTextField(
                value = uiState.ncf,
                onValueChange = { onEvent(GastosEvent.NcfChange(it)) },
                label = "NCF",
                error = uiState.errorNcf
            )

            GastoTextField(
                value = uiState.concepto,
                onValueChange = { onEvent(GastosEvent.ConceptoChange(it)) },
                label = "Concepto",
                error = uiState.errorConcepto
            )

            GastoTextField(
                value = uiState.descuento.toString(),
                onValueChange = { onEvent(GastosEvent.DescuentoChange(it)) },
                label = "Descuento",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                )
            )

            GastoTextField(
                value = uiState.itbis.toString(),
                onValueChange = { onEvent(GastosEvent.ItbisChange(it)) },
                label = "ITBIS",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                )
            )

            if (!uiState.esRecurrente) {
                GastoTextField(
                    value = uiState.monto.toString(),
                    onValueChange = { onEvent(GastosEvent.MontoChange(it)) },
                    label = "Monto",
                    error = uiState.errorMonto,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                colors = ButtonDefaults.buttonColors(Green),
                onClick = {
                    focusManager.clearFocus()
                    onEvent(GastosEvent.SaveGasto(navigateToSuplidorGasto))
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.Save,
                    contentDescription = "Guardar"
                )
                Text("Guardar")
            }
        }
    }
}

@Composable
fun ShowDatePickerDialog(
    context: android.content.Context,
    onDateSelected: (String) -> Unit
) {
    val calendar = Calendar.getInstance()
    val year = calendar[Calendar.YEAR]
    val month = calendar[Calendar.MONTH]
    val day = calendar[Calendar.DAY_OF_MONTH]

    DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, selectedDay ->
            val formattedDate = String.format(
                Locale.getDefault(),
                "%04d-%02d-%02d",
                selectedYear,
                selectedMonth + 1,
                selectedDay
            )
            onDateSelected(formattedDate)
        },
        year,
        month,
        day
    ).show()
}

@Composable
fun GastoTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    trailingIcon: @Composable (() -> Unit)? = null,
    error: String? = null,
    readOnly: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            trailingIcon = trailingIcon,
            readOnly = readOnly,
            keyboardOptions = keyboardOptions,
            modifier = Modifier.fillMaxWidth()
        )
        error?.let {
            Text(
                text = it,
                color = Color.Red,
                modifier = Modifier.align(Alignment.Start)
            )
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}
