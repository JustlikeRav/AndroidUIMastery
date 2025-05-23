package com.rav.androiduimastery.tictactoe

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TicTacToeViewModel : ViewModel() {
    private val _gameState = MutableStateFlow<GameState>(GameState(currentPlayer = Player.P_X))
    val gameState = _gameState.asStateFlow()

    init {
        val newGrid = listOf(
            listOf<Cell>(Cell.NULL, Cell.NULL, Cell.NULL),
            listOf<Cell>(Cell.NULL, Cell.NULL, Cell.NULL),
            listOf<Cell>(Cell.NULL, Cell.NULL, Cell.NULL),
        )
        _gameState.update {
            GameState(
                grid = newGrid,
                currentPlayer = Player.P_X
            )
        }
    }

    fun playMove(position: Pair<Int, Int>) {
        val currentPlayer = _gameState.value.currentPlayer
        val currentGrid = _gameState.value.grid
        val newGameState = GameState(
            grid = currentGrid.mapIndexed { i, row ->
                if (i == position.first) {
                    row.mapIndexed { j, cell ->
                        if (j == position.second) {
                            if (currentPlayer == Player.P_X) Cell.X
                            else Cell.O
                        } else {
                            cell
                        }
                    }
                } else row
            },
            currentPlayer = if (currentPlayer == Player.P_X) Player.P_O else Player.P_X
        )

        _gameState.update {
            newGameState
        }
    }
}