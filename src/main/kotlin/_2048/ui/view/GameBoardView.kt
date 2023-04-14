package _2048.ui.view

import _2048.gameboard.Direction
import _2048.ui.component.GameBoardViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.unit.Dp
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class GameBoardView(private val gameBoardViewModel: GameBoardViewModel) {

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(GameBoardViewModel::class.java)
        private const val TILE_WIDTH: Float = 100f
        private const val TILE_HEIGHT: Float = 100f
    }

    @Composable
    fun playingBoard() = Row(
        modifier = Modifier.fillMaxSize().background(Color(100, 100, 100)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        val coroutineScope = rememberCoroutineScope()

        Column(
            modifier = Modifier.focusable().onKeyEvent(onKeyPressed(coroutineScope))
        ) {
            for (row in 0 until gameBoardViewModel.gameBoardBox.value.size) {
                Column {
                    Row {
                        createTiles(row)
                    }
                    Spacer(modifier = Modifier.height(Dp(1f)))
                }
            }
        }
    }

    @Composable
    private fun createTiles(row: Int) {
        for (column in 0 until gameBoardViewModel.gameBoardBox.value[0].size) {
            val tileValue = gameBoardViewModel.gameBoardBox.value[row][column]
            tile(
                color = gameBoardViewModel.tileColors[tileValue]!!,
                tileValue = tileValue.toString()
            )
            Spacer(modifier = Modifier.width(Dp(1f)))

        }
    }

    @Composable
    private fun tile(color: Color, tileValue: String) = Box(
        modifier = Modifier.size(Dp(TILE_WIDTH), Dp(TILE_HEIGHT)).border(Dp(2f), Color.Black).background(color)
    ) {
        Text(tileValue, modifier = Modifier.align(Alignment.Center))
    }

    @OptIn(ExperimentalComposeUiApi::class)
    fun onKeyPressed(coroutineScope: CoroutineScope): (KeyEvent) -> Boolean = { input: KeyEvent ->
        when (input.key) {
            Key.DirectionUp -> handleKeyPressed(input.type, coroutineScope, Direction.UP)
            Key.DirectionDown -> handleKeyPressed(input.type, coroutineScope, Direction.DOWN)
            Key.DirectionLeft -> handleKeyPressed(input.type, coroutineScope, Direction.LEFT)
            Key.DirectionRight -> handleKeyPressed(input.type, coroutineScope, Direction.RIGHT)
            else -> false
        }
    }

    private fun handleKeyPressed(
        type: KeyEventType,
        coroutineScope: CoroutineScope,
        direction: Direction
    ) = if (type != KeyEventType.KeyUp) {
        false
    } else {
        coroutineScope.launch { shiftAndRender(gameBoardViewModel.gameBoardBox, direction) }
        true
    }

    private suspend fun shiftAndRender(
        gameBoardBox: MutableState<Array<IntArray>>, direction: Direction
    ) = try {
        gameBoardViewModel.gameBoard.movementChannel.send(direction)
        gameBoardBox.value = gameBoardViewModel.gameBoard.playingAreaChannel.receive()
    } catch (ex: CancellationException) {
        LOGGER.error("Exception thrown when receiving channel ${ex.message}")
    }

}