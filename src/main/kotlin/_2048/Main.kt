package _2048

import _2048.gameboard.GameBoard
import _2048.gameboard.GameBoardService
import _2048.gameboard.GameBoardServiceImpl
import _2048.ui.component.GameBoardViewModel
import _2048.ui.view.GameBoardView
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.window.singleWindowApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun main() = singleWindowApplication(title = "2048", icon = ColorPainter(Color.Cyan)) {

    val gameBoard = GameBoard()
    val gameBoardService: GameBoardService = GameBoardServiceImpl(gameBoard)
    val gameBoardViewModel = GameBoardViewModel(gameBoard)
    val gameBoardView = GameBoardView(gameBoardViewModel)

    CoroutineScope(Dispatchers.Default).launch {
        gameBoardService.playGame()
    }

    MaterialTheme {
        gameBoardView.playingBoard()
    }
}