package com.rav.androiduimastery.tictactoe

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TicTacToeViewModel : ViewModel() {
    private val _gameState = MutableStateFlow<GameState>(GameState(currentPlayer = Player.P_X))
    val gameState = _gameState.asStateFlow()

    fun playMove(position: Pair<Int, Int>) {
        
    }
}