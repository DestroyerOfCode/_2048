package _2048

class GameBoardService(private val gameBoard: GameBoard) {

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

    private fun shiftLeft(): Array<IntArray> {
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

    private fun shiftDown(): Array<IntArray> {
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
                if (gameBoard.playingArea[row][column] != 0) {
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

    private fun shiftUp(): Array<IntArray> {
        compactTilesUp()
        mergeTilesUp()
        compactTilesUp()
        return gameBoard.playingArea
    }

    private fun mergeTilesUp() {
        for (row in gameBoard.playingArea.indices) {
            columnLoop@ for (column in gameBoard.playingArea[0].indices) {
                if (gameBoard.playingArea[row][column] != 0) {
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