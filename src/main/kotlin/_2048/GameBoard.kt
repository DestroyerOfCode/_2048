package _2048

import kotlin.random.Random


class GameBoard {
    companion object {
        const val BOX_HEIGHT = 4
        const val BOX_WIDTH = 4
    }

    private var boardGame: Array<IntArray>


    constructor(boardGame: Array<IntArray>) {
        this.boardGame = boardGame
    }

    constructor() {
        boardGame = Array(BOX_HEIGHT) { IntArray(BOX_WIDTH) }
    }

    fun start() {
        initBoard()
        printBoard()
    }

    fun shift(direction: Char): Array<IntArray>? {
        return when (direction) {
            'w' -> shiftUp();
            's' -> shiftDown();
            'a' -> shiftLeft();
            'd' -> shiftRight();
            else -> null;
        }
    }

    private fun shiftRight(): Array<IntArray> {
        TODO("Not yet implemented")
    }

    private fun shiftLeft(): Array<IntArray> {
        TODO("Not yet implemented")
    }

    private fun shiftDown(): Array<IntArray> {
        TODO("Not yet implemented")
    }

    private fun shiftUp(): Array<IntArray> {
        mergeTiles()
        compactTiles()
        return boardGame
    }

    private fun compactTiles() {
        for (row in BOX_HEIGHT-1 downTo 1) {
            for (column in BOX_WIDTH-1 downTo 0) {
                if (0 != boardGame[row][column] &&
                    0 == boardGame[row-1][column]) {
                    boardGame[row-1][column] = boardGame[row][column]
                    boardGame[row][column] = 0
                }
            }
        }
    }
    private fun mergeTiles() {
        for (row in (0 until BOX_WIDTH - 1)) {
            for (column in (0 until BOX_HEIGHT)) {
                if (boardGame[row][column] != 0 &&
                    boardGame[row][column] == boardGame[row + 1][column]
                ) {
                    boardGame[row][column] *= 2
                    boardGame[row + 1][column] = 0

                }
            }
        }
    }

    fun printBoard() {
        for (x in 0 until BOX_HEIGHT) {
            for (y in 0 until BOX_WIDTH) {
                if (y != BOX_WIDTH - 1) {
                    print("${boardGame[x][y]} ")
                } else {
                    print(boardGame[x][y])
                }
            }
            println()
        }
    }

    private fun initBoard() {
        boardGame[(0..3).random()][(0..3).random()] = 2
        if (Random.nextBoolean()) {
            val heightIndex: Int = (0..3).random()
            val widthIndex: Int = (0..3).random()
            boardGame[heightIndex][widthIndex] = if (Random.nextBoolean()) 2 else 4;
        }
    }


}