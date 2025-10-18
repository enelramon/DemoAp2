package edu.ucne.composedemo.presentation.tictactoe


import androidx.lifecycle.ViewModel
import edu.ucne.composedemo.presentation.tareas.edit.EditTaskUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class GameUiState(
    val board: List<Player?> = List(9) { null },
    val currentPlayer: Player = Player.X,
    val winner: Player? = null,
    val isDraw: Boolean = false,
    val playerSelection: Player? = null,
    val gameStarted: Boolean = false
)

class GameViewModel : ViewModel() {
    private val _state = MutableStateFlow(GameUiState())
    val state: StateFlow<GameUiState> = _state.asStateFlow()


    fun selectPlayer(player: Player) {
        _state.update { it.copy(playerSelection = player) }
    }

    fun startGame() {
        if (_state.value.playerSelection != null) {
            _state.update { it.copy(gameStarted = true) }
        }
    }

    fun onCellClick(index: Int) {
        if (_state.value.board[index] != null || _state.value.winner != null) {
            return
        }

        val newBoard = _state.value.board.toMutableList()
        newBoard[index] = _state.value.currentPlayer

        val newWinner = checkWinner(newBoard)
        val isDraw = newBoard.all { it != null } && newWinner == null

        _state.update {
            it.copy(
                board = newBoard.toList(),
                currentPlayer = it.currentPlayer.opponent,
                winner = newWinner,
                isDraw = isDraw
            )
        }
    }

    fun restartGame() {
        _state.value = GameUiState()
    }

    private fun checkWinner(board: List<Player?>): Player? {
        val winningLines = listOf(
            // Horizontales
            listOf(0, 1, 2), listOf(3, 4, 5), listOf(6, 7, 8),
            // Verticales
            listOf(0, 3, 6), listOf(1, 4, 7), listOf(2, 5, 8),
            // Diagonales
            listOf(0, 4, 8), listOf(2, 4, 6)
        )

        for (line in winningLines) {
            val (a, b, c) = line
            if (board[a] != null && board[a] == board[b] && board[a] == board[c]) {
                return board[a] // Devuelve el jugador ganador (X o O)
            }
        }
        return null // No hay ganador
    }
}

enum class Player(val symbol: String) {
    X("X"),
    O("O");

    val opponent: Player
        get() = when (this) {
            X -> O
            O -> X
        }
}