package _2048

class GameBoardServiceImpl(private var gameBoard: GameBoard): GameBoardService {

    override fun shift(direction: Direction): Array<IntArray> {
        return when (direction) {
            Direction.UP -> shiftUp()
            Direction.DOWN -> shiftDown()
            Direction.LEFT -> shiftLeft()
            Direction.RIGHT -> shiftRight()
        }
    }

    override fun printBoard() {
        for (x in gameBoard.playingArea.indices) {
            for (y in gameBoard.playingArea[0].indices) {
                if (y != gameBoard.playingArea[0].lastIndex) {
                    print("${gameBoard.playingArea[x][y]} ")
                } else {
                    print(gameBoard.playingArea[x][y])
                }
            }
            println()
        }
    }

    override fun shiftRight(): Array<IntArray> {
        compactTilesRight()
        mergeTilesRight()
        compactTilesRight()
        return gameBoard.playingArea
    }

    private fun mergeTilesRight() {
        for (row in gameBoard.playingArea.indices.reversed()) {
            columnLoop@ for (column in gameBoard.playingArea[0].indices.reversed()) {
                if (gameBoard.playingArea[row][column] != 0) {
                    for (column2 in (column - 1 downTo 0)) {
                        if (gameBoard.playingArea[row][column2] == gameBoard.playingArea[row][column]) {
                            gameBoard.playingArea[row][column] = gameBoard.playingArea[row][column] shl 1
                            gameBoard.playingArea[row][column2] = 0
                            continue@columnLoop
                        } else if (gameBoard.playingArea[row][column2] != 0 &&
                            gameBoard.playingArea[row][column2] != gameBoard.playingArea[row][column]
                        ) {
                            break
                        }
                    }
                }
            }
        }
    }

    private fun compactTilesRight() {
        for (row in gameBoard.playingArea.indices.reversed()) {
            for (column in gameBoard.playingArea[0].indices.reversed()) {
                if (0 == gameBoard.playingArea[row][column]) {
                    for (column2 in column - 1 downTo 0) {
                        if (0 != gameBoard.playingArea[row][column2]) {
                            gameBoard.playingArea[row][column] = gameBoard.playingArea[row][column2]
                            gameBoard.playingArea[row][column2] = 0
                            break
                        }
                    }
                }
            }
        }
    }

    override fun shiftLeft(): Array<IntArray> {
        compactTilesLeft()
        mergeTilesLeft()
        compactTilesLeft()
        return gameBoard.playingArea
    }

    private fun mergeTilesLeft() {
        for (row in gameBoard.playingArea.indices) {
            columnLoop@ for (column in gameBoard.playingArea[0].indices) {
                if (gameBoard.playingArea[row][column] != 0) {
                    for (column2 in (column + 1 until GameBoard.BOX_HEIGHT)) {
                        if (gameBoard.playingArea[row][column2] == gameBoard.playingArea[row][column]) {
                            gameBoard.playingArea[row][column] = gameBoard.playingArea[row][column] shl 1
                            gameBoard.playingArea[row][column2] = 0
                            continue@columnLoop
                        } else if (gameBoard.playingArea[row][column2] != 0 &&
                            gameBoard.playingArea[row][column2] != gameBoard.playingArea[row][column]
                        ) {
                            break
                        }
                    }
                }
            }
        }
    }

    private fun compactTilesLeft() {
        for (row in gameBoard.playingArea.indices) {
            for (column in gameBoard.playingArea[0].indices) {
                if (0 == gameBoard.playingArea[row][column]) {
                    for (column2 in column + 1 until GameBoard.BOX_HEIGHT) {
                        if (0 != gameBoard.playingArea[row][column2]) {
                            gameBoard.playingArea[row][column] = gameBoard.playingArea[row][column2]
                            gameBoard.playingArea[row][column2] = 0
                            break
                        }
                    }
                }
            }
        }
    }

    override fun shiftDown(): Array<IntArray> {
        compactTilesDown()
        mergeTilesDown()
        compactTilesDown()
        return gameBoard.playingArea
    }

    private fun compactTilesDown() {
        for (row in gameBoard.playingArea.indices.reversed()) {
            for (column in gameBoard.playingArea[0].indices.reversed()) {
                if (0 == gameBoard.playingArea[row][column]) {
                    for (row2 in row - 1 downTo 0) {
                        if (0 != gameBoard.playingArea[row2][column]) {
                            gameBoard.playingArea[row][column] = gameBoard.playingArea[row2][column]
                            gameBoard.playingArea[row2][column] = 0
                            break
                        }
                    }
                }
            }
        }
    }

    private fun mergeTilesDown() {
        for (row in gameBoard.playingArea.indices.reversed()) {
            columnLoop@ for (column in gameBoard.playingArea[0].indices.reversed()) {
                if (0 != gameBoard.playingArea[row][column]) {
                    for (row2 in (row - 1 downTo 0)) {
                        if (gameBoard.playingArea[row2][column] == gameBoard.playingArea[row][column]) {
                            gameBoard.playingArea[row][column] = gameBoard.playingArea[row][column] shl 1
                            gameBoard.playingArea[row2][column] = 0
                            continue@columnLoop
                        } else if (gameBoard.playingArea[row2][column] != 0 &&
                            gameBoard.playingArea[row2][column] != gameBoard.playingArea[row][column]
                        ) {
                            break
                        }
                    }
                }
            }
        }
    }

    override fun shiftUp(): Array<IntArray> {
        compactTilesUp()
        mergeTilesUp()
        compactTilesUp()
        return gameBoard.playingArea
    }

    private fun mergeTilesUp() {
        for (row in gameBoard.playingArea.indices) {
            columnLoop@ for (column in gameBoard.playingArea[0].indices) {
                if (0 != gameBoard.playingArea[row][column]) {
                    for (row2 in (row + 1 until GameBoard.BOX_HEIGHT)) {
                        if (gameBoard.playingArea[row2][column] == gameBoard.playingArea[row][column]) {
                            gameBoard.playingArea[row][column] = gameBoard.playingArea[row][column] shl 1
                            gameBoard.playingArea[row2][column] = 0
                            continue@columnLoop
                        } else if (gameBoard.playingArea[row2][column] != 0 &&
                            gameBoard.playingArea[row2][column] != gameBoard.playingArea[row][column]
                        ) {
                            break
                        }
                    }
                }

            }
        }
    }

    private fun compactTilesUp() {
        for (row in gameBoard.playingArea.indices) {
            for (column in gameBoard.playingArea[0].indices) {
                if (0 == gameBoard.playingArea[row][column]) {
                    for (row2 in row + 1 until GameBoard.BOX_WIDTH) {
                        if (0 != gameBoard.playingArea[row2][column]) {
                            gameBoard.playingArea[row][column] = gameBoard.playingArea[row2][column]
                            gameBoard.playingArea[row2][column] = 0
                            break
                        }
                    }
                }
            }
        }
    }
}