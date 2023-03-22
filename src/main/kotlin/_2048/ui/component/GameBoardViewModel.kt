package _2048.ui.component

import _2048.gameboard.GameBoard
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

class GameBoardViewModel(gameBoard: GameBoard) {

    var gameBoardBox: MutableState<Array<IntArray>> = mutableStateOf(gameBoard.playingArea)
        private set

}