package _2048

import _2048.gameboard.GameBoard
import _2048.movement.MovementService
import _2048.player.PlayerService
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.unit.Dp
import kotlinx.coroutines.Job

class GameBoardView(
    private val gameBoard: GameBoard,
    private val movementService: MovementService,
    val playerService: PlayerService,
//    val gameBoardService: GameBoardService,
) {
    @Composable
    fun tile(color: Color, value: Int) =
        Box(modifier = Modifier.size(Dp(100F), Dp(100F)).border(Dp(2f), Color.Black).background(color)) {
            Text(
                value.toString(),
                modifier = Modifier.align(Alignment.Center)
            )
        }

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    fun playingBoard() = Row(
        modifier = Modifier.fillMaxSize()
            .background(Color(100, 100, 100)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        val gameBoardBox: MutableState<Array<IntArray>> = mutableStateOf(gameBoard.playingArea)

        Row(Modifier.focusable()
            .onKeyEvent(onKeyPressed(gameBoardBox))
        ) {
            repeat(4) { column ->
                Column {
                    repeat(4) { row ->
                        Row {
                            Box(
                                modifier = Modifier.size(Dp(100F), Dp(100F)).border(Dp(2f), Color.Black).background(
                                    Color.White
                                )
                            ) {
                                Text(
                                    gameBoardBox.value[row][column].toString(),
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalComposeUiApi::class)
    fun onKeyPressed(gameBoardBox: MutableState<Array<IntArray>>): (KeyEvent) -> Boolean =
        {

            if (it.key == Key.W) {
//                gameBoardService.chooseDirection(Direction.UP)
    //                    gameBoardService.playRound(Direction.UP)
                gameBoardBox.value = gameBoard.playingArea
                true
            }
    //                if (it.key == Key.S) {
    //                    gameBoardService.playRound(Direction.DOWN)
    //                    gameBoardBox.value = gameBoard.playingArea
    //                    true
    //                }
    //                if (it.key == Key.A) {
    //                    gameBoardService.playRound(Direction.LEFT)
    //                    gameBoardBox.value = gameBoard.playingArea
    //                    true
    //                }
    //                if (it.key == Key.D) {
    //                    gameBoardService.playRound(Direction.RIGHT)
    //                    gameBoardBox.value = gameBoard.playingArea
    //                    true
    //                }
            false

        }
}