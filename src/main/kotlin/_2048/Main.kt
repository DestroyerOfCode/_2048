package _2048

import _2048.gameboard.GameBoard
import _2048.gameboard.GameBoardService
import _2048.gameboard.GameBoardServiceImpl
import _2048.movement.MovementService
import _2048.movement.MovementServiceImpl
import _2048.player.PlayerService
import _2048.player.PlayerServiceImpl
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.window.singleWindowApplication
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
fun main() = singleWindowApplication(title = "2048", icon = ColorPainter(Color.Cyan)) {

    val gameBoard = GameBoard()
    val movementService: MovementService = MovementServiceImpl(gameBoard)
    val playerService: PlayerService = PlayerServiceImpl(gameBoard)
    val gameBoardView = GameBoardView(gameBoard, movementService, playerService)
    val gameBoardService: GameBoardService =
        GameBoardServiceImpl(
            gameBoard = gameBoard,
            playerService = playerService,
            movementService = movementService,
            gameBoardView
        )
    val job = GlobalScope.launch {
        gameBoardService.playGame()
    }

    MaterialTheme {
        gameBoardView.playingBoard()
    }
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun tile(color: Color, value: Int) {
    val requester = remember { FocusRequester() }
    Box(
        modifier = Modifier.size(Dp(100F), Dp(100F)).border(Dp(2f), Color.Black)

            .focusable()
            .focusRequester(focusRequester = requester)
            .background(color)
    ) {
        Text(
            value.toString(),
            modifier = Modifier.align(Alignment.Center)
        )
    }
    LaunchedEffect(Unit) {
        requester.requestFocus()
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun playingBoard(gameBoard: MutableState<GameBoard>) = Row(
    modifier = Modifier.fillMaxSize()
        .background(Color(100, 100, 100))
        .focusable()

        .onKeyEvent {

            if (it.key == Key.K) {

                true
            }
            println("hi")
            false

        },
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.Center
) {

//    val playingArea by mutableStateOf(gameBoard.playingArea)
    repeat(4) { column ->
        Column {
            repeat(4) { row ->
                Row {
                    tile(Color.Gray, gameBoard.value.playingArea[row][column])
                }
            }
        }
    }

}