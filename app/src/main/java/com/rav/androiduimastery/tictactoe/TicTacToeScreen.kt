package com.rav.androiduimastery.tictactoe

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TicTacToeScreen(modifier: Modifier = Modifier, vm: TicTacToeViewModel = viewModel()) {
    val gameState by vm.gameState.collectAsStateWithLifecycle()

    val grid = gameState.grid
    val isGameOver = gameState.isGameOver

    Column(modifier = modifier.fillMaxSize()) {
        grid.forEachIndexed { i, row ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                row.forEachIndexed { j, cell ->
                    Box(
                        Modifier
                            .weight(1f)
                            .fillMaxSize()
                            .background(Color.White)
                            .border(1.dp, color = Color.DarkGray)
                            .clickable(enabled = true, onClick = {
                                vm.playMove(Pair(i, j))
                            })
                    ) {
                        if (cell != Cell.NULL) {
                            Text(
                                modifier = Modifier.align(Alignment.Center), text = cell.name
                            )
                        }
                    }
                }
            }
        }
    }
}