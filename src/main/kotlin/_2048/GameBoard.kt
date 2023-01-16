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

    fun shift(direction: Direction): Array<IntArray> {
        return when (direction) {
            Direction.UP -> shiftUp()
            Direction.DOWN -> shiftDown()
            Direction.LEFT -> shiftLeft()
            Direction.RIGHT -> shiftRight()
        }
    }

    private fun shiftRight(): Array<IntArray> {
        compactTilesRight()
        mergeTilesRight()
        compactTilesRight()
        return boardGame
    }

    private fun mergeTilesRight() {
        val hasCollided: MutableMap<Int, Boolean> = mutableMapOf(0 to false, 1 to false, 2 to false, 3 to false)
        for (row in boardGame.indices.reversed()) {
            for (column in boardGame[0].indices.reversed()) {
                if (boardGame[row][column] != 0) {
                    for (column2 in (column - 1 downTo  0)) {
                        if (boardGame[row][column2] == boardGame[row][column] &&
                            hasCollided[column] == false
                        ) {
                            boardGame[row][column] = boardGame[row][column] shl 1
                            boardGame[row][column2] = 0
                            hasCollided[column] = true
                        } else if (boardGame[row][column2] != 0 &&
                            boardGame[row][column2] != boardGame[row][column]
                        ) {
                            break
                        }
                    }
                }

            }
        }


    }

    private fun compactTilesRight() {
        for (row in boardGame.indices.reversed()) {
            for (column in boardGame[0].indices.reversed()) {
                if (0 == boardGame[row][column]) {
                    for (column2 in column - 1 downTo  0) {
                        if (0 != boardGame[row][column2]) {
                            boardGame[row][column] = boardGame[row][column2]
                            boardGame[row][column2] = 0

                            break
                        }
                    }
                }
            }
        }


    }

    private fun shiftLeft(): Array<IntArray> {
        compactTilesLeft()
        mergeTilesLeft()
        compactTilesLeft()
        return boardGame
    }

    private fun mergeTilesLeft() {
        val hasCollided: MutableMap<Int, Boolean> = mutableMapOf(0 to false, 1 to false, 2 to false, 3 to false)
        for (row in boardGame.indices) {
            for (column in boardGame[0].indices) {
                if (boardGame[row][column] != 0) {
                    for (column2 in (column + 1 until BOX_HEIGHT)) {
                        if (boardGame[row][column2] == boardGame[row][column] &&
                            hasCollided[column] == false
                        ) {
                            boardGame[row][column] = boardGame[row][column] shl 1
                            boardGame[row][column2] = 0
                            hasCollided[column] = true
                        } else if (boardGame[row][column2] != 0 &&
                            boardGame[row][column2] != boardGame[row][column]
                        ) {
                            break
                        }
                    }
                }

            }
        }


    }

    private fun compactTilesLeft() {
        for (row in boardGame.indices) {
            for (column in boardGame[0].indices) {
                if (0 == boardGame[row][column]) {
                    for (column2 in column + 1 until BOX_HEIGHT) {
                        if (0 != boardGame[row][column2]) {
                            boardGame[row][column] = boardGame[row][column2]
                            boardGame[row][column2] = 0

                            break
                        }
                    }
                }
            }
        }


    }

    private fun shiftDown(): Array<IntArray> {
        compactTilesDown()
        mergeTilesDown()
        compactTilesDown()
        return boardGame
    }

    private fun compactTilesDown() {
        for (row in boardGame.indices.reversed()) {
            for (column in boardGame[0].indices.reversed()) {
                if (0 == boardGame[row][column]) {
                    for (row2 in row - 1 downTo 0) {
                        if (0 != boardGame[row2][column]) {
                            boardGame[row][column] = boardGame[row2][column]
                            boardGame[row2][column] = 0

                            break
                        }
                    }
                }
            }
        }
    }

    private fun mergeTilesDown() {
        val hasCollided: MutableMap<Int, Boolean> = mutableMapOf(0 to false, 1 to false, 2 to false, 3 to false)
        for (row in boardGame.indices.reversed()) {
            for (column in boardGame[0].indices.reversed()) {
                if (boardGame[row][column] != 0) {
                    for (row2 in (row - 1 downTo 0)) {
                        if (boardGame[row2][column] == boardGame[row][column] &&
                            hasCollided[column] == false
                        ) {
                            boardGame[row][column] = boardGame[row][column] shl 1
                            boardGame[row2][column] = 0
                            hasCollided[column] = true
                        } else if (boardGame[row2][column] != 0 &&
                            boardGame[row2][column] != boardGame[row][column]
                        ) {
                            break
                        }
                    }
                }
            }
        }
    }

    private fun shiftUp(): Array<IntArray> {
        compactTilesUp()
        mergeTilesUp()
        compactTilesUp()
        return boardGame
    }

    private fun mergeTilesUp() {
        val hasCollided: MutableMap<Int, Boolean> = mutableMapOf(0 to false, 1 to false, 2 to false, 3 to false)
        for (row in boardGame.indices) {
            for (column in boardGame[0].indices) {
                if (boardGame[row][column] != 0) {
                    for (row2 in (row + 1 until BOX_HEIGHT)) {
                        if (boardGame[row2][column] == boardGame[row][column] &&
                            hasCollided[column] == false
                        ) {
                            boardGame[row][column] = boardGame[row][column] shl 1
                            boardGame[row2][column] = 0
                            hasCollided[column] = true
                        } else if (boardGame[row2][column] != 0 &&
                            boardGame[row2][column] != boardGame[row][column]
                        ) {
                            break
                        }
                    }
                }

            }
        }
    }

    private fun compactTilesUp() {
        for (row in boardGame.indices) {
            for (column in boardGame[0].indices) {
                if (0 == boardGame[row][column]) {
                    for (row2 in row + 1 until BOX_WIDTH) {
                        if (0 != boardGame[row2][column]) {
                            boardGame[row][column] = boardGame[row2][column]
                            boardGame[row2][column] = 0

                            break
                        }
                    }
                }
            }
        }
    }

    fun printBoard() {
        for (x in boardGame.indices) {
            for (y in boardGame[0].indices) {
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
        boardGame[(0..3).random()][(0..3).random()] = if (Random.nextBoolean()) 2 else 4
        if (Random.nextBoolean()) {
            val heightIndex: Int = (0..3).random()
            val widthIndex: Int = (0..3).random()
            boardGame[heightIndex][widthIndex] = if (Random.nextBoolean()) 2 else 4;
        }
    }


}