package _2048

import _2048.gameboard.GameBoard
import _2048.gameboard.GameBoardService
import _2048.gameboard.GameBoardServiceImpl
import _2048.movement.MovementService
import _2048.movement.MovementServiceImpl
import _2048.player.PlayerService
import _2048.player.PlayerServiceImpl
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.window.singleWindowApplication

fun main() = singleWindowApplication(title = "2048", icon = ColorPainter(Color.Cyan)) {

    val gameBoard = GameBoard()
    val movementService: MovementService = MovementServiceImpl(gameBoard)
    val playerService: PlayerService = PlayerServiceImpl(gameBoard)
    val gameBoardService: GameBoardService =
        GameBoardServiceImpl(gameBoard = gameBoard, playerService = playerService, movementService = movementService)

    MaterialTheme {
        playingBoard()
    }
}

@Composable
fun tile(color: Color, value: Int) =
    Box(modifier = Modifier.size(Dp(100F), Dp(100F)).border(Dp(2f), Color.Black).background(color)) {
        Text(
            value.toString(),
            modifier = Modifier.align(Alignment.Center)
        )
    }

@Composable
fun playingBoard() = Row(
    modifier = Modifier.fillMaxSize(),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.Center
) {

    repeat(4) {
        Column {
            repeat(4) {
                Row {
                    tile(Color.Gray, 0)
                }
            }
        }
    }

}