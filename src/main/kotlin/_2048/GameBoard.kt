package _2048

import kotlin.random.Random


class GameBoard(val playingArea: Array<IntArray> = Array(BOX_HEIGHT) { IntArray(BOX_WIDTH) }) {
    companion object {
        const val BOX_HEIGHT = 4
        const val BOX_WIDTH = 4
    }

    init {
        if (isPlayingAreaEmpty()) {
            initBoard()
        }
    }

    private fun isPlayingAreaEmpty(): Boolean {
        var isEmpty = true
        playingArea.forEach { row ->
            row.forEach { tile ->
                if (tile != 0) {
                    isEmpty = false
                }
            }
        }
        return isEmpty
    }

    private fun initBoard() {
        val firstTileHeightIndex = playingArea.lastIndex
        playingArea[(0..firstTileHeightIndex).random()][(0..playingArea[firstTileHeightIndex].lastIndex).random()] =
            if (Random.nextBoolean()) 2 else 4
        if (Random.nextBoolean()) {
            val secondTileHeightIndex: Int = (0..playingArea.lastIndex).random()
            val widthIndex: Int = (0..playingArea[secondTileHeightIndex].lastIndex).random()
            playingArea[secondTileHeightIndex][widthIndex] = if (Random.nextBoolean()) 2 else 4
        }
    }

}