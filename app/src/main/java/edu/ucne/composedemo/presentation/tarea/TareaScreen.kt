package edu.ucne.composedemo.presentation.tarea

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Article
import androidx.compose.material.icons.filled.AccessTimeFilled
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.composedemo.R
import edu.ucne.composedemo.presentation.components.MensajeDeErrorGenerico
import edu.ucne.composedemo.presentation.components.TopBarComponent

@Composable
fun TareaScreen(
    viewModel: TareaViewModel = hiltViewModel(),
    tareaId: Int?,
    goBack: () -> Unit,
) {
    LaunchedEffect(tareaId) {
        tareaId?.let {
            viewModel.selectedTarea(tareaId)
        }
    }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    TareaFormulario(
        uiState = uiState,
        evento = viewModel::onEvent,
        goBack = goBack
    )
    LaunchedEffect(uiState.guardado) {
        if (uiState.guardado) {
            goBack()
            viewModel.onEvent(TareaEvent.GoBackAfterSave)
        }
    }
}

@Composable
fun TareaFormulario(
    uiState: TareaUiState,
    evento: (TareaEvent) -> Unit,
    goBack: () -> Unit,
) {
    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            TopBarComponent(
                title = if (uiState.tareaId != null && uiState.tareaId != 0) stringResource(R.string.editar_tarea)
                else stringResource(R.string.registrar_tarea),
                onActionClick = goBack,
                backButtonVisible = true
            )
        },
        floatingActionButton = {
            Column {
                ExtendedFloatingActionButton(
                    onClick = {
                        evento(TareaEvent.LimpiarTodo)
                        focusManager.clearFocus()
                    },
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onSecondary,
                    icon = {
                        Icon(
                            Icons.Filled.CleaningServices,
                            contentDescription = stringResource(R.string.limpiar)
                        )
                    },
                    text = { Text(stringResource(R.string.limpiar)) },
                    modifier = Modifier
                        .scale(0.8f)
                        .padding(start = 15.dp)
                )

                ExtendedFloatingActionButton(
                    onClick = {
                        evento(TareaEvent.Save)
                        focusManager.clearFocus()
                    },
                    icon = {
                        Icon(
                            Icons.Filled.Save,
                            contentDescription = stringResource(R.string.guardar)
                        )
                    },
                    text = { Text(stringResource(R.string.guardar)) }
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 15.dp)
                .padding(padding)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    focusManager.clearFocus()
                },
        ) {
            // CAMPO DESCRIPCION
            OutlinedTextField(
                value = uiState.descripcion ?: "",
                onValueChange = {
                    evento(TareaEvent.DescripcionChange(it))
                },
                label = { Text(stringResource(R.string.campo_descripcion)) },
                modifier = Modifier
                    .fillMaxWidth(),
                singleLine = true,
                isError = !uiState.errorMessageDescripcion.isNullOrBlank(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Article,
                        contentDescription = ""
                    )
                }
            )
            MensajeDeErrorGenerico(uiState.errorMessageDescripcion)

            // CAMPO TIEMPO
            OutlinedTextField(
                value = uiState.tiempo?.toString() ?: "",
                onValueChange = {
                    evento(TareaEvent.TiempoChange(it.toIntOrNull()))
                },
                label = { Text(stringResource(R.string.campo_tiempo)) },
                modifier = Modifier
                    .fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = !uiState.errorMessageTiempo.isNullOrBlank(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.AccessTimeFilled,
                        contentDescription = ""
                    )
                }
            )
            MensajeDeErrorGenerico(uiState.errorMessageTiempo)
        }
    }
}