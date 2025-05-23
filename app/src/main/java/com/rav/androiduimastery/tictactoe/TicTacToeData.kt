package com.rav.androiduimastery.tictactoe

data class GameState(
    val grid: List<List<Cell>> = listOf(),
    val isGameOver: Boolean = false,
    val currentPlayer: Player
)

enum class Cell {
    X, O, NULL
}

enum class Player {
    P_X, P_O
}