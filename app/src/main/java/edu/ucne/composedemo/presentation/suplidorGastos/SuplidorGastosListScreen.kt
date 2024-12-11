package edu.ucne.composedemo.presentation.suplidorGastos

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Loop
import androidx.compose.material.icons.filled.Paid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.composedemo.R
import edu.ucne.composedemo.data.remote.dto.SuplidorGastoDto
import edu.ucne.composedemo.presentation.components.ShowComponent
import edu.ucne.composedemo.presentation.components.TopBarComponent
import edu.ucne.composedemo.ui.theme.Blue
import edu.ucne.composedemo.ui.theme.Green
import edu.ucne.composedemo.ui.theme.Red
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun SuplidorGastosListScreen(
    viewModel: SuplidorGastosViewModel = hiltViewModel(),
    onDrawer: () -> Unit,
    onAsignarRecurrencia:(Int) -> Unit,
    onAsignarGasto:(Int) -> Unit
) {
    var filter by remember { mutableStateOf("Todos") }
    val uiState  by viewModel.uiState.collectAsStateWithLifecycle()
    val onEvent = viewModel::onEvent
    val expandedIndex = remember { mutableIntStateOf(-1) }

    Scaffold(
        topBar = {
            TopBarComponent(
                title = "Suplidores Gastos",
                onDrawer
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                item{
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = filter == stringResource(R.string.todos),
                            onClick = {
                                expandedIndex.intValue = -1
                                filter = "Todos"
                                onEvent(SuplidorGastoEvent.GetAllSuplidorGastos)
                            },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Blue,
                                unselectedColor = Blue
                            ),
                            modifier = Modifier.padding(0.dp)
                        )
                        Text(stringResource(R.string.todos))
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = filter == stringResource(R.string.recurrentes),
                            onClick = {
                                expandedIndex.intValue = -1
                                filter = "Recurrentes"
                                onEvent(SuplidorGastoEvent.GetAllSuplidorGastosByRecurrencia)
                            },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Blue,
                                unselectedColor = Blue
                            ),
                            modifier = Modifier.padding(0.dp)
                        )
                        Text(stringResource(R.string.recurrentes))
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = filter == stringResource(R.string.no_recurrentes),
                            onClick = {
                                expandedIndex.intValue = -1
                                filter = "NoRecurrentes"
                                onEvent(SuplidorGastoEvent.GetAllSuplidorGastosByNoRecurrente)
                            },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Blue,
                                unselectedColor = Blue
                            ),
                            modifier = Modifier.padding(0.dp)
                        )
                        Text(stringResource(R.string.no_recurrentes))
                    }
                }
            }

            ShowComponent(
                value = uiState.isLoading,
                whenContentIsTrue = {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                },
                whenContentIsFalse = {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        itemsIndexed(items = uiState.suplidoresGastos) { index, suplidor  ->
                            SwipeableItemWithActionsRTL(
                                isRevealed = expandedIndex.intValue == index,
                                actions = {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .background(Color.Transparent)
                                            .padding(vertical = 8.dp)
                                            .clip(RoundedCornerShape(8.dp))
                                            .border(
                                                width = 1.dp,
                                                color = Color.Gray,
                                                shape = RoundedCornerShape(8.dp)
                                            )
                                    ) {
                                        Row(
                                            modifier = Modifier.fillMaxHeight(),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceEvenly
                                        ) {
                                            ActionIcon(
                                                onClick = { onAsignarGasto(suplidor.idSuplidor) },
                                                backgroundColor = Green,
                                                icon = Icons.Filled.Paid,
                                                modifier = Modifier
                                                    .fillMaxHeight(),
                                                contentDescription = "Asignar\nGasto"
                                            )
                                            ActionIcon(
                                                onClick = { onAsignarRecurrencia(suplidor.idSuplidor) },
                                                backgroundColor = Blue,
                                                icon = Icons.Filled.Loop,
                                                modifier = Modifier
                                                    .fillMaxHeight(),
                                                contentDescription = "Asignar\nRecurrencia"
                                            )
                                        }
                                    }
                                },
                                onExpanded = {
                                    expandedIndex.intValue = index
                                },
                                onCollapsed = {
                                    expandedIndex.intValue = -1
                                }
                            ) {
                                SuplidoresGastosCard(suplidor)
                            }
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun SuplidoresGastosCard(
    suplidorGasto: SuplidorGastoDto,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.elevatedCardElevation(8.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = suplidorGasto.nombres,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                ShowComponent(
                    value = suplidorGasto.esRecurrente,
                    whenContentIsTrue = {
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ){
                            Text(
                                text = stringResource(R.string.recurrente),
                                style = MaterialTheme.typography.bodySmall
                            )
                            Spacer(modifier = Modifier.width(2.dp))
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = stringResource(R.string.recurrente),
                                tint = Green,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = if(suplidorGasto.periodicidad == 1)
                                "${stringResource(R.string.mensual)}: Dia ${suplidorGasto.dia}"
                            else
                                "${stringResource(R.string.anual)}: Dia ${suplidorGasto.dia}",
                            style = MaterialTheme.typography.bodySmall
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Ultima Recurrencia: ${suplidorGasto.ultimaRecurencia?.substringBefore("T")}",
                            style = MaterialTheme.typography.bodySmall
                        )
                    },
                    whenContentIsFalse = {
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ){
                            Text(
                                text = stringResource(R.string.recurrente),
                                style = MaterialTheme.typography.bodySmall
                            )
                            Spacer(modifier = Modifier.width(2.dp))
                            Icon(
                                imageVector = Icons.Filled.Cancel,
                                contentDescription = stringResource(R.string.recurrente),
                                tint = Red,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun SwipeableItemWithActionsRTL(
    isRevealed: Boolean,
    actions: @Composable RowScope.() -> Unit,
    modifier: Modifier = Modifier,
    onExpanded: () -> Unit = {},
    onCollapsed: () -> Unit = {},
    content: @Composable () -> Unit
) {
    var contextMenuWidth by remember { mutableFloatStateOf(0f) }
    var contentHeight by remember { mutableIntStateOf(0) }
    val offset = remember { Animatable(initialValue = 0f) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(isRevealed, contextMenuWidth) {
        if (isRevealed) {
            offset.animateTo(-contextMenuWidth)
        } else {
            offset.animateTo(0f)
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        Row(
            modifier = Modifier
                .onSizeChanged { contextMenuWidth = it.width.toFloat() }
                .height(with(LocalDensity.current) { contentHeight.toDp() })
                .align(Alignment.CenterEnd),
            verticalAlignment = Alignment.Top
        ) {
            actions()
        }

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .offset { IntOffset(offset.value.roundToInt(), 0) }
                .pointerInput(contextMenuWidth) {
                    detectHorizontalDragGestures(
                        onHorizontalDrag = { _, dragAmount ->
                            scope.launch {
                                val newOffset = (offset.value + dragAmount)
                                    .coerceIn(-contextMenuWidth, 0f)
                                offset.snapTo(newOffset)
                            }
                        },
                        onDragEnd = {
                            when {
                                offset.value <= -contextMenuWidth / 2f -> {
                                    scope.launch {
                                        offset.animateTo(-contextMenuWidth)
                                        onExpanded()
                                    }
                                }
                                else -> {
                                    scope.launch {
                                        offset.animateTo(0f)
                                        onCollapsed()
                                    }
                                }
                            }
                        }
                    )
                }
                .onSizeChanged { contentHeight = it.height }
        ) {
            content()
        }
    }
}

@Composable
fun ActionIcon(
    onClick: () -> Unit,
    backgroundColor: Color,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    tint: Color = Color.White
) {
    IconButton(
        onClick = onClick,
        modifier = modifier.width(100.dp)
            .background(backgroundColor)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = tint
            )
            Text(
                text = contentDescription ?: "",
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                color = Color.White
            )
        }
    }
}