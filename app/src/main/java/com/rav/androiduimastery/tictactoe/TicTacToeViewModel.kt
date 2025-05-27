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
        val winnerCells = isWinner(position, newGrid)
        val newGameState = GameState(
            grid = newGrid,
            currentPlayer = if (currentPlayer == Player.P_X) Player.P_O else Player.P_X,
            isGameOver = totalMoves == totalCells || winnerCells.isNotEmpty(),
            winnerCells = winnerCells
        )

        _gameState.update {
            newGameState
        }
    }

    private fun isWinner(position: Pair<Int, Int>, newGrid: List<List<Cell>>): Set<Pair<Int, Int>> {
        val ansHorizontal = isWinnerHorizontally(position, newGrid)
        val ansVertical = isWinnerVertically(position, newGrid)
        val ansDiagonally1 = isWinnerDiagonally1(position, newGrid)
        val ansDiagonally2 = isWinnerDiagonally2(position, newGrid)

        return when {
            ansHorizontal.isNotEmpty() -> ansHorizontal
            ansVertical.isNotEmpty() -> ansVertical
            ansDiagonally1.isNotEmpty() -> ansDiagonally1
            ansDiagonally2.isNotEmpty() -> ansDiagonally2
            else -> emptySet<Pair<Int, Int>>()
        }
    }

    private fun isWinnerHorizontally(position: Pair<Int, Int>, grid: List<List<Cell>>): Set<Pair<Int, Int>> {
        val currentPlayer = _gameState.value.currentPlayer
        val cell = if (currentPlayer == Player.P_X) Cell.X else Cell.O

        val winnerPositions = mutableSetOf<Pair<Int, Int>>()

        val row = grid[position.first]

        row.forEachIndexed { i, mCell ->
            if (mCell != cell) return emptySet()
            else winnerPositions.add(Pair<Int, Int>(position.first, i))
        }

        return winnerPositions
    }

    private fun isWinnerVertically(position: Pair<Int, Int>, grid: List<List<Cell>>): Set<Pair<Int, Int>> {
        val column = grid.flatMap { row ->
            row.mapIndexedNotNull { j, cell ->
                if (j == position.second) cell
                else null
            }
        }

        val winnerPositions = mutableSetOf<Pair<Int, Int>>()

        val currentPlayer = _gameState.value.currentPlayer
        val cell = if (currentPlayer == Player.P_X) Cell.X else Cell.O

        column.forEachIndexed { i, mCell ->
            if (mCell != cell) return emptySet()
            else winnerPositions.add(Pair<Int, Int>(i, position.second))
        }

        return winnerPositions
    }

    fun isWinnerDiagonally1(position: Pair<Int, Int>, grid: List<List<Cell>>): Set<Pair<Int, Int>> {
        val currentPlayer = _gameState.value.currentPlayer
        val cell = if (currentPlayer == Player.P_X) Cell.X else Cell.O

        var winnerPositions = mutableSetOf<Pair<Int, Int>>()
        for (i in 0..grid.size - 1) {
            val mCell = grid[i][i]
            if (mCell != cell) return emptySet()
            else winnerPositions.add(Pair(i, i))
        }
        return winnerPositions
    }

    fun isWinnerDiagonally2(position: Pair<Int, Int>, grid: List<List<Cell>>): Set<Pair<Int, Int>> {
        val currentPlayer = _gameState.value.currentPlayer
        val cell = if (currentPlayer == Player.P_X) Cell.X else Cell.O

        var winnerPositions = mutableSetOf<Pair<Int, Int>>()
        for (i in 0..grid.size - 1) {
            val mCell = grid[grid.size - 1 - i][i]
            if (mCell != cell) return emptySet()
            else winnerPositions.add(Pair(grid.size - 1 - i, i))
        }
        return winnerPositions
    }
}