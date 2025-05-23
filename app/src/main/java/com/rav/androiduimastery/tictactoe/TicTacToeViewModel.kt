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

    }
}