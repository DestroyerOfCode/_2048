package _2048.ui

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
import androidx.compose.ui.input.key.*
import androidx.compose.ui.unit.Dp
import kotlinx.coroutines.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class GameBoardView(private val gameBoard: GameBoard) {

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(GameBoardView::class.java)
    }

    @Composable
    fun playingBoard() = Row(
        modifier = Modifier.fillMaxSize().background(Color(100, 100, 100)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        val gameBoardBox: MutableState<Array<IntArray>> = mutableStateOf(gameBoard.playingArea)
        val coroutineScope = rememberCoroutineScope()

        Column(
            modifier = Modifier.focusable().onKeyEvent(onKeyPressed(gameBoardBox, coroutineScope))
        ) {
            for (row in 0 until gameBoardBox.value.size) {
                Row {
                    for (column in 0 until gameBoardBox.value[0].size) {
                        createNumberBox(gameBoardBox.value[row][column])
                    }
                }
            }
        }
    }

    @Composable
    private fun createNumberBox(boxNumber: Int) = Box(
        modifier = Modifier.size(
            Dp(100F), Dp(100F)
        ).border(Dp(2f), Color.Black).background(Color.White)
    ) {
        tile(Color.White, boxNumber)
    }


    @Composable
    private fun tile(color: Color, value: Int) = Box(
        modifier = Modifier.size(Dp(100F), Dp(100F)).border(Dp(2f), Color.Black).background(color)
    ) {
        Text(value.toString(), modifier = Modifier.align(Alignment.Center))
    }

    @OptIn(ExperimentalComposeUiApi::class)
    fun onKeyPressed(
        gameBoardBox: MutableState<Array<IntArray>>, coroutineScope: CoroutineScope
    ): (KeyEvent) -> Boolean = { input: KeyEvent ->
        when (input.key) {
            Key.DirectionUp -> handleKeyPressed(input.type, coroutineScope, gameBoardBox, Direction.UP)
            Key.DirectionDown -> handleKeyPressed(input.type, coroutineScope, gameBoardBox, Direction.DOWN)
            Key.DirectionLeft -> handleKeyPressed(input.type, coroutineScope, gameBoardBox, Direction.LEFT)
            Key.DirectionRight -> handleKeyPressed(input.type, coroutineScope, gameBoardBox, Direction.UP)
            else -> false
        }
    }

    private fun handleKeyPressed(
        type: KeyEventType,
        coroutineScope: CoroutineScope,
        gameBoardBox: MutableState<Array<IntArray>>,
        direction: Direction
    ) = if (type != KeyEventType.KeyUp) {
        false
    } else {
        coroutineScope.launch { shiftAndRender(gameBoardBox, direction) }
        true
    }

    private suspend fun shiftAndRender(
        gameBoardBox: MutableState<Array<IntArray>>, direction: Direction
    ) = try {
        gameBoard.movementChannel.send(direction)
        gameBoardBox.value = gameBoard.playingAreaChannel.receive()
    } catch (ex: CancellationException) {
        LOGGER.error("Exception thrown when receiving channel ${ex.message}")
    }

}