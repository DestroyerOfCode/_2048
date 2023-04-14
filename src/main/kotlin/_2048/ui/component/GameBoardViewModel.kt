package _2048.ui.component

import _2048.gameboard.GameBoard
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color

class GameBoardViewModel(var gameBoard: GameBoard) {

    var gameBoardBox: MutableState<Array<IntArray>> = mutableStateOf(gameBoard.playingArea)
        private set

    val tileColors: Map<Int, Color> = mapOf(
        0 to Color.White,
        2 to Color.DarkGray,
        4 to Color.Blue,
        8 to Color.Green,
        16 to Color.Magenta,
        32 to Color.Red,
        64 to Color.Yellow,
        128 to Color.DarkGray,
    )
    
}