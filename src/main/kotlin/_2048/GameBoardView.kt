package _2048

import _2048.gameboard.Direction
import _2048.gameboard.GameBoard
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
import kotlinx.coroutines.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class GameBoardView(
    private val gameBoard: GameBoard,
) {

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(GameBoardView::class.java)
    }

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

        Row(
            Modifier.focusable()
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

            when (it.key) {
                Key.DirectionUp -> shiftAndRender(gameBoardBox, Direction.UP)
                Key.DirectionDown -> shiftAndRender(gameBoardBox, Direction.DOWN)
                Key.DirectionLeft -> shiftAndRender(gameBoardBox, Direction.LEFT)
                Key.DirectionRight -> shiftAndRender(gameBoardBox, Direction.RIGHT)
                else -> {
                    true
                }
            }

        }

    private fun shiftAndRender(gameBoardBox: MutableState<Array<IntArray>>, direction: Direction): Boolean {
        var isMoved: Boolean = false
        runBlocking {
            try {
                gameBoard.movementChannel.send(direction)
                isMoved = gameBoard.isMovedChannel.receive()
                if (isMoved) {
                    gameBoardBox.value = gameBoard.playingArea
                }
            } catch (ex: CancellationException) {
                LOGGER.error("Exception thrown when receiving channel ${ex.message}")
            }
        }
        return isMoved
    }
}