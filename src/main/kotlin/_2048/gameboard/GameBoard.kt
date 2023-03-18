package _2048.gameboard

import kotlinx.coroutines.channels.Channel
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.random.Random

class GameBoard(
    var playingArea: Array<IntArray> = Array(BOX_HEIGHT) { IntArray(BOX_WIDTH) },
    var score: Int = 0,
    val movementChannel: Channel<Direction> = Channel(
        capacity = Channel.UNLIMITED,
        onUndeliveredElement = { LOGGER.error("Undelivered message $this") }),
    val playingAreaChannel: Channel<Array<IntArray>> = Channel(capacity = Channel.UNLIMITED)
) {

    companion object {
        const val BOX_HEIGHT = 4
        const val BOX_WIDTH = 4
        private val LOGGER: Logger = LoggerFactory.getLogger(GameBoard::class.java)
    }

    init {
        if (playingArea.isEmpty() || playingArea.any { it.isEmpty() }) {
            LOGGER.error("Invalid Size of Playing Board selected!")
            throw IllegalPlayingBoardSizeException("You cannot pick an empty Row or Column!")
        }

        if (playingArea.size != playingArea[0].size) {
            LOGGER.error("Invalid Size of Playing Board selected!")
            throw IllegalPlayingBoardSizeException("Height and Width of Playing Board must be indifferent!")
        }

        if (isPlayingAreaEmpty()) {
            initBoard()
        }
    }

    private fun isPlayingAreaEmpty(): Boolean {
        var isEmpty = true
        playingArea.forEach rowCycle@{ row ->
            row.forEach { tile ->
                if (tile != 0) {
                    isEmpty = false
                    return@rowCycle
                }
            }
        }
        return isEmpty
    }

    private fun initBoard() {
        initTileAtRandomPosition()
        if (Random.nextBoolean()) {
            initTileAtRandomPosition()
        }
    }

    private fun initTileAtRandomPosition() {
        val rowIndex = (0..playingArea.lastIndex).random()
        val columnIndex = (0..playingArea[rowIndex].lastIndex).random()
        playingArea[(0..rowIndex).random()][columnIndex] = if (Random.nextBoolean()) 2 else 4
    }
}