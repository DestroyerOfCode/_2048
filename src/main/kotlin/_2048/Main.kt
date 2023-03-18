package _2048

import _2048.gameboard.GameBoard
import _2048.gameboard.GameBoardService
import _2048.gameboard.GameBoardServiceImpl
import _2048.movement.MovementService
import _2048.movement.MovementServiceImpl
import _2048.player.PlayerService
import _2048.player.PlayerServiceImpl
import _2048.ui.GameBoardView
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.window.singleWindowApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun main() = singleWindowApplication(title = "2048", icon = ColorPainter(Color.Cyan)) {

    val gameBoard = GameBoard()
    val movementService: MovementService = MovementServiceImpl(gameBoard)
    val playerService: PlayerService = PlayerServiceImpl(gameBoard)
    val gameBoardView = GameBoardView(gameBoard)
    val gameBoardService: GameBoardService =
        GameBoardServiceImpl(
            gameBoard = gameBoard,
            playerService = playerService,
            movementService = movementService
        )
    CoroutineScope(Dispatchers.Default).launch {
        gameBoardService.playGame()
    }

    MaterialTheme {
        gameBoardView.playingBoard()
    }
}