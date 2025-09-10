package edu.ucne.composedemo.presentation.tictactoe


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.composedemo.ui.theme.DemoAp2Theme


@Composable
fun TicTacToeScreen(
    onDrawer: () -> Unit = {},
    viewModel: GameViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    TicTacToeBody(
        state = state,
        selectPlayer = viewModel::selectPlayer,
        startGame = viewModel::startGame,
        onCellClick = viewModel::onCellClick,
        restartGame = viewModel::restartGame,
    )
}

@Composable
private fun TicTacToeBody(
    state: GameUiState,
    selectPlayer: (Player) -> Unit,
    startGame: () -> Unit,
    onCellClick: (Int) -> Unit,
    restartGame: () -> Unit,
) {
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (!state.gameStarted) {
                PlayerSelectionScreen(
                    selectedPlayer = state.playerSelection,
                    onPlayerSelected = { selectPlayer(it) },
                    onStartGame = { startGame() }
                )
            } else {
                GameBoard(
                    uiState = state,
                    onCellClick = { onCellClick(it) },
                    onRestartGame = { restartGame() }
                )
            }
        }
    }
}

@Composable
fun PlayerSelectionScreen(
    selectedPlayer: Player?,
    onPlayerSelected: (Player) -> Unit,
    onStartGame: () -> Unit
) {
    Text("Elige tu jugador", fontSize = 28.sp, fontWeight = FontWeight.Bold)
    Spacer(modifier = Modifier.height(24.dp))
    Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
        Button(onClick = { onPlayerSelected(Player.X) }) { Text("Jugador X", fontSize = 18.sp) }
        Button(onClick = { onPlayerSelected(Player.O) }) { Text("Jugador O", fontSize = 18.sp) }
    }
    Spacer(modifier = Modifier.height(24.dp))
    Button(
        onClick = onStartGame,
        enabled = selectedPlayer != null
    ) {
        Text("Iniciar Partida", fontSize = 18.sp)
    }
}

@Composable
fun GameBoard(
    uiState: GameUiState,
    onCellClick: (Int) -> Unit,
    onRestartGame: () -> Unit
) {
    val gameStatus = when {
        uiState.winner != null -> "🏆 ¡Ganador: ${uiState.winner.symbol}!"
        uiState.isDraw -> "🤝 ¡Es un empate!"
        else -> "Turno de: ${uiState.currentPlayer.symbol}"
    }
    Text(text = gameStatus, fontSize = 24.sp, fontWeight = FontWeight.Bold)
    Spacer(modifier = Modifier.height(20.dp))
    GameBoardBody(board = uiState.board, onCellClick = onCellClick)
    Spacer(modifier = Modifier.height(20.dp))
    Button(onClick = onRestartGame) {
        Text("Reiniciar Juego", fontSize = 18.sp)
    }
}

@Composable
fun GameBoardBody(board: List<Player?>, onCellClick: (Int) -> Unit) {
    Column {
        (0..2).forEach { row ->
            Row {
                (0..2).forEach { col ->
                    val index = row * 3 + col
                    BoardCell(board[index]) {
                        onCellClick(index)
                    }
                }
            }
        }
    }
}

@Composable
private fun BoardCell(
    player: Player?,
    onCellClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(100.dp)
            .padding(4.dp)
            .background(Color.LightGray)
            .clickable { onCellClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = player?.symbol ?: "",
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            color = if (player == Player.X) Color.Blue else Color.Red
        )
    }
}

@Preview
@Composable
private fun BoardPreview() {
    DemoAp2Theme {
        TicTacToeBody(
            state = GameUiState(),
            selectPlayer = {},
            startGame = {},
            onCellClick = {},
            restartGame = {}
        )
    }
}

