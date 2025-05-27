package com.rav.androiduimastery.tictactoe

data class GameState(
    val grid: List<List<Cell>> = listOf(),
    val isGameOver: Boolean = false,
    val currentPlayer: Player,
    val winnerCells: Set<Pair<Int, Int>> = setOf()
)

enum class Cell {
    X, O, NULL
}

enum class Player {
    P_X, P_O
}