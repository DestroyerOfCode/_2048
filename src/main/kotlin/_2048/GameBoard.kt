package _2048

import kotlin.random.Random


class GameBoard {
    companion object {
        const val BOX_HEIGHT = 4
        const val BOX_WIDTH = 4
    }

    val playingArea: Array<IntArray>

    constructor(playingArea: Array<IntArray>) {
        this.playingArea = playingArea
    }

    constructor() {
        playingArea = Array(BOX_HEIGHT) { IntArray(BOX_WIDTH) }
    }

    fun start() {
        initBoard()
        printBoard()
    }

    private fun printBoard() {
        for (x in playingArea.indices) {
            for (y in playingArea[0].indices) {
                if (y != playingArea[0].lastIndex) {
                    print("${playingArea[x][y]} ")
                } else {
                    print(playingArea[x][y])
                }
            }
            println()
        }
    }

    private fun initBoard() {
        playingArea[(0..3).random()][(0..3).random()] = if (Random.nextBoolean()) 2 else 4
        if (Random.nextBoolean()) {
            val heightIndex: Int = (0..3).random()
            val widthIndex: Int = (0..3).random()
            playingArea[heightIndex][widthIndex] = if (Random.nextBoolean()) 2 else 4;
        }
    }

}