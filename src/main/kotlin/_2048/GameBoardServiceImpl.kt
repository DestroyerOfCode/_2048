package _2048

class GameBoardServiceImpl(private val gameBoard: GameBoard = GameBoard(),
    private val playerService: PlayerService) : GameBoardService {

    override fun shift(direction: Direction): Array<IntArray> {
        return when (direction) {
            Direction.UP -> shiftUp()
            Direction.DOWN -> shiftDown()
            Direction.LEFT -> shiftLeft()
            Direction.RIGHT -> shiftRight()
        }
    }

    override fun printBoard() {
        gameBoard.playingArea.forEach { row ->
            row.forEachIndexed { indexColumn, tile ->
                if (indexColumn != gameBoard.playingArea[0].lastIndex) {
                    print("$tile ")
                } else {
                    print(tile)
                }
            }
            println()
        }
    }

    override fun start() {
        while (canMakeMove()) {
            val directionString: String = readln()
            var direction: Direction?
            try {
                direction = playerService.makeMove(directionString)
                shift(direction!!)
                playerService.addNewTile()
            } catch (ex: IllegalMoveException) {

            }
        }
    }

    private fun canMakeMove(): Boolean {
        TODO("Not yet implemented")
    }

    override fun shiftRight(): Array<IntArray> {
        compactTilesRight()
        mergeTilesRight()
        compactTilesRight()
        return gameBoard.playingArea
    }

    private fun mergeTilesRight() {
        gameBoard.playingArea.reversed().forEachIndexed { indexRow, row ->
            columnLoop@ for (column in gameBoard.playingArea[0].indices.reversed()) {
                if (row[column] == 0) {
                    continue@columnLoop
                }
                for (column2 in (column - 1 downTo 0)) {
                    if (row[column2] == row[column]) {
                        row[column] = row[column] shl 1
                        row[column2] = 0
                        gameBoard.playingArea[GameBoard.BOX_HEIGHT - indexRow - 1] = row
                        continue@columnLoop
                    } else if (areTilesNonZeroAndDifferent(row[column2], row[column])) {
                        break
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
        gameBoard.playingArea.forEachIndexed { indexRow, row ->
            row.forEachIndexed columnLoop@{ indexColumn, tile ->
                if (0 == tile) {
                    return@columnLoop
                }
                for (column2 in (indexColumn + 1 until gameBoard.playingArea.lastIndex + 1)) {
                    if (row[column2] == tile) {
                        row[indexColumn] = tile shl 1
                        row[column2] = 0
                        gameBoard.playingArea[indexRow] = row
                        return@columnLoop
                    } else if (areTilesNonZeroAndDifferent(gameBoard.playingArea[indexRow][column2], tile)) {
                        break
                    }

                }
            }
        }
    }


    private fun compactTilesLeft() {
        gameBoard.playingArea.forEachIndexed { indexRow, row ->
            row.forEachIndexed columnLoop@{ indexColumn, tile ->
                if (0 != tile) {
                    return@columnLoop
                }
                for (column2 in indexColumn + 1 until gameBoard.playingArea.lastIndex + 1) {
                    if (0 != row[column2]) {
                        row[indexColumn] = row[column2]
                        row[column2] = 0
                        gameBoard.playingArea[indexRow] = row
                        break
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
        gameBoard.playingArea.forEachIndexed { indexRow, row ->
            row.forEachIndexed columnLoop@{ indexColumn, tile ->
                if (0 == tile) {
                    return@columnLoop
                }
                for (row2 in (indexRow + 1 until gameBoard.playingArea.lastIndex + 1)) {
                    if (gameBoard.playingArea[row2][indexColumn] == tile) {
                        gameBoard.playingArea[indexRow][indexColumn] = tile shl 1
                        gameBoard.playingArea[row2][indexColumn] = 0
                        return@columnLoop
                    } else if (areTilesNonZeroAndDifferent(gameBoard.playingArea[row2][indexColumn], tile)) {
                        break
                    }
                }

            }
        }
    }

    private fun compactTilesUp() {
        gameBoard.playingArea.forEachIndexed { indexRow, row ->
            row.forEachIndexed columnLoop@{ indexColumn, tile ->
                if (0 != tile) {
                    return@columnLoop
                }
                for (row2 in indexRow + 1 until row.lastIndex + 1) {
                    if (0 != gameBoard.playingArea[row2][indexColumn]) {
                        gameBoard.playingArea[indexRow][indexColumn] = gameBoard.playingArea[row2][indexColumn]
                        gameBoard.playingArea[row2][indexColumn] = 0
                        break
                    }
                }
            }
        }
    }

    private fun areTilesNonZeroAndDifferent(tile1: Int, tile2: Int): Boolean {
        return 0 != tile1 && tile1 != tile2
    }

}