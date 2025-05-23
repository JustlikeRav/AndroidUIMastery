package com.rav.androiduimastery.tictactoe

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TicTacToeViewModel : ViewModel() {
    private val _gameState = MutableStateFlow<GameState>(GameState(currentPlayer = Player.P_X))
    val gameState = _gameState.asStateFlow()

    private var totalCells: Int = 0
    private var totalMoves: Int = 0

    init {
        val newGrid = listOf(
            listOf<Cell>(Cell.NULL, Cell.NULL, Cell.NULL),
            listOf<Cell>(Cell.NULL, Cell.NULL, Cell.NULL),
            listOf<Cell>(Cell.NULL, Cell.NULL, Cell.NULL),
        )

        if (newGrid.isNotEmpty()) {
            totalCells = newGrid.size * newGrid[0].size
        }

        _gameState.update {
            GameState(
                grid = newGrid, currentPlayer = Player.P_X
            )
        }
    }

    fun playMove(position: Pair<Int, Int>) {
        totalMoves++

        val currentPlayer = _gameState.value.currentPlayer
        val currentGrid = _gameState.value.grid
        val newGrid = currentGrid.mapIndexed { i, row ->
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
        }
        val newGameState = GameState(
            grid = newGrid, currentPlayer = if (currentPlayer == Player.P_X) Player.P_O else Player.P_X,
            isGameOver = totalMoves == totalCells || isWinner(position, newGrid)
        )

        _gameState.update {
            newGameState
        }
    }

    private fun isWinner(position: Pair<Int, Int>, newGrid: List<List<Cell>>): Boolean {
        return isWinnerHorizontally(position, newGrid) || isWinnerVertically(position, newGrid) || isWinnerDiagonally(position, newGrid)
    }

    private fun isWinnerHorizontally(position: Pair<Int, Int>, grid: List<List<Cell>>): Boolean {
        val currentPlayer = _gameState.value.currentPlayer
        val cell = if (currentPlayer == Player.P_X) Cell.X else Cell.O

        val row = grid[position.first]

        row.forEach {
            if (it != cell) return false
        }

        return true
    }

    private fun isWinnerVertically(position: Pair<Int, Int>, grid: List<List<Cell>>): Boolean {
        val column = grid.flatMap { row ->
            row.mapIndexedNotNull { j, cell ->
                if (j == position.second) cell
                else null
            }
        }

        val currentPlayer = _gameState.value.currentPlayer
        val cell = if (currentPlayer == Player.P_X) Cell.X else Cell.O

        column.forEach {
            if (it != cell) return false
        }

        return true
    }

    private fun isWinnerDiagonally(position: Pair<Int, Int>, grid: List<List<Cell>>): Boolean {
        val currentPlayer = _gameState.value.currentPlayer
        val cell = if (currentPlayer == Player.P_X) Cell.X else Cell.O

        fun isWinnerDiagonally1(): Boolean {
            for (i in 0..grid.size - 1) {
                val mCell = grid[i][i]
                if (mCell != cell) return false
            }
            return true
        }

        fun isWinnerDiagonally2(): Boolean {
            for (i in 0..grid.size - 1) {
                val mCell = grid[grid.size - 1 - i][i]
                if (mCell != cell) return false
            }
            return true
        }

        return isWinnerDiagonally1() || isWinnerDiagonally2()
    }
}