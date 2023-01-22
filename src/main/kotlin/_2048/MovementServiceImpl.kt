package _2048

class MovementServiceImpl(private val gameBoard: GameBoard = GameBoard()) : MovementService {

    override fun canMakeMove(): Boolean {
        return isPlayingAreaNotFull() || Direction.values().map { direction -> canShiftBoard(direction) }
            .contains(true)
    }

    override fun canMakeMove(direction: Direction): Boolean {
        return canShiftBoard(direction)
    }

    override fun shift(direction: Direction): Array<IntArray> {
        return when (direction) {
            Direction.UP -> shiftUp()
            Direction.DOWN -> shiftDown()
            Direction.LEFT -> shiftLeft()
            Direction.RIGHT -> shiftRight()
        }
    }

    override fun shiftRight(): Array<IntArray> {
        compactTilesRight()
        mergeTilesRight()
        compactTilesRight()
        return gameBoard.playingArea
    }

    private fun canShiftBoard(direction: Direction): Boolean {
        val comparedGameBoard: Array<IntArray> =
            Array(gameBoard.playingArea.size) { (gameBoard.playingArea[it]).copyOf() }

        return when (direction) {
            Direction.UP -> (this.gameBoard.playingArea contentDeepEquals compactTilesUp(comparedGameBoard)).not() or
                    (this.gameBoard.playingArea contentDeepEquals mergeTilesUp(
                        comparedGameBoard
                    )).not()

            Direction.DOWN -> (this.gameBoard.playingArea contentDeepEquals compactTilesDown(comparedGameBoard)).not() or
                    (this.gameBoard.playingArea contentDeepEquals mergeTilesDown(
                        comparedGameBoard
                    )).not()


            Direction.LEFT -> (this.gameBoard.playingArea contentDeepEquals compactTilesLeft(comparedGameBoard)).not() or
                    (this.gameBoard.playingArea contentDeepEquals mergeTilesLeft(
                        comparedGameBoard
                    )).not()

            Direction.RIGHT -> (this.gameBoard.playingArea contentDeepEquals compactTilesRight(comparedGameBoard)).not() or
                    (this.gameBoard.playingArea contentDeepEquals mergeTilesRight(
                        comparedGameBoard
                    )).not()
        }
    }

    private fun isPlayingAreaNotFull(): Boolean {
        var isNotFull = false
        gameBoard.playingArea.forEach { row ->
            row.forEach { tile ->
                if (0 == tile) {
                    isNotFull = true
                }
            }
        }
        return isNotFull
    }

    private fun mergeTilesRight(): Array<IntArray> {
        return mergeTilesRight(gameBoard.playingArea)
    }

    private fun mergeTilesRight(playingArea: Array<IntArray>): Array<IntArray> {
        playingArea.reversed().forEachIndexed { indexRow, row ->
            columnLoop@ for (column in playingArea[0].indices.reversed()) {
                if (row[column] == 0) {
                    continue@columnLoop
                }
                for (column2 in (column - 1 downTo 0)) {
                    if (row[column2] == row[column]) {
                        row[column] = row[column] shl 1
                        row[column2] = 0
                        playingArea[playingArea.lastIndex - indexRow] = row
                        continue@columnLoop
                    } else if (areTilesNonZeroAndDifferent(row[column2], row[column])) {
                        break
                    }
                }
            }
        }
        return playingArea
    }

    private fun compactTilesRight() {
        compactTilesRight(gameBoard.playingArea)
    }

    private fun compactTilesRight(playingArea: Array<IntArray>): Array<IntArray> {
        for (row in playingArea.indices.reversed()) {
            for (column in playingArea[0].indices.reversed()) {
                if (0 == playingArea[row][column]) {
                    for (column2 in column - 1 downTo 0) {
                        if (0 != playingArea[row][column2]) {
                            playingArea[row][column] = playingArea[row][column2]
                            playingArea[row][column2] = 0
                            break
                        }
                    }
                }
            }
        }

        return playingArea
    }

    override fun shiftLeft(): Array<IntArray> {
        compactTilesLeft()
        mergeTilesLeft()
        compactTilesLeft()
        return gameBoard.playingArea
    }


    private fun mergeTilesLeft(): Array<IntArray> {
        return mergeTilesLeft(gameBoard.playingArea)
    }

    private fun mergeTilesLeft(playingArea: Array<IntArray>): Array<IntArray> {
        playingArea.forEachIndexed { indexRow, row ->
            row.forEachIndexed columnLoop@{ indexColumn, tile ->
                if (0 == tile) {
                    return@columnLoop
                }
                for (column2 in (indexColumn + 1 until playingArea.lastIndex + 1)) {
                    if (row[column2] == tile) {
                        row[indexColumn] = tile shl 1
                        row[column2] = 0
                        playingArea[indexRow] = row
                        return@columnLoop
                    } else if (areTilesNonZeroAndDifferent(playingArea[indexRow][column2], tile)) {
                        break
                    }

                }
            }
        }
        return playingArea
    }


    private fun compactTilesLeft(): Array<IntArray> {
        return compactTilesLeft(gameBoard.playingArea)
    }

    private fun compactTilesLeft(playingArea: Array<IntArray>): Array<IntArray> {
        playingArea.forEachIndexed { indexRow, row ->
            row.forEachIndexed columnLoop@{ indexColumn, tile ->
                if (0 != tile) {
                    return@columnLoop
                }
                for (column2 in indexColumn + 1 until playingArea.lastIndex + 1) {
                    if (0 != row[column2]) {
                        row[indexColumn] = row[column2]
                        row[column2] = 0
                        playingArea[indexRow] = row
                        break
                    }
                }
            }
        }
        return playingArea
    }


    override fun shiftDown(): Array<IntArray> {
        compactTilesDown()
        mergeTilesDown()
        compactTilesDown()
        return gameBoard.playingArea
    }

    private fun compactTilesDown(): Array<IntArray> {
        return compactTilesDown(gameBoard.playingArea)
    }

    private fun compactTilesDown(playingArea: Array<IntArray>): Array<IntArray> {
        for (row in playingArea.indices.reversed()) {
            for (column in playingArea[0].indices.reversed()) {
                if (0 == playingArea[row][column]) {
                    for (row2 in row - 1 downTo 0) {
                        if (0 != playingArea[row2][column]) {
                            playingArea[row][column] = playingArea[row2][column]
                            playingArea[row2][column] = 0
                            break
                        }
                    }
                }
            }
        }
        return playingArea
    }

    private fun mergeTilesDown() {
        mergeTilesDown(gameBoard.playingArea)
    }

    private fun mergeTilesDown(playingArea: Array<IntArray>): Array<IntArray> {
        for (row in playingArea.indices.reversed()) {
            columnLoop@ for (column in playingArea[0].indices.reversed()) {
                if (0 != playingArea[row][column]) {
                    for (row2 in (row - 1 downTo 0)) {
                        if (playingArea[row2][column] == playingArea[row][column]) {
                            playingArea[row][column] = playingArea[row][column] shl 1
                            playingArea[row2][column] = 0
                            continue@columnLoop
                        } else if (playingArea[row2][column] != 0 &&
                            playingArea[row2][column] != playingArea[row][column]
                        ) {
                            break
                        }
                    }
                }
            }
        }
        return playingArea
    }

    override fun shiftUp(): Array<IntArray> {
        compactTilesUp()
        mergeTilesUp()
        compactTilesUp()
        return gameBoard.playingArea
    }

    private fun mergeTilesUp(): Array<IntArray> {
        return mergeTilesUp(gameBoard.playingArea)
    }

    private fun mergeTilesUp(playingArea: Array<IntArray>): Array<IntArray> {
        playingArea.forEachIndexed { indexRow, row ->
            row.forEachIndexed columnLoop@{ indexColumn, tile ->
                if (0 == tile) {
                    return@columnLoop
                }
                for (row2 in (indexRow + 1 until playingArea.lastIndex + 1)) {
                    if (playingArea[row2][indexColumn] == tile) {
                        playingArea[indexRow][indexColumn] = tile shl 1
                        playingArea[row2][indexColumn] = 0
                        return@columnLoop
                    } else if (areTilesNonZeroAndDifferent(playingArea[row2][indexColumn], tile)) {
                        break
                    }
                }

            }
        }
        return playingArea
    }

    private fun compactTilesUp() {
        compactTilesUp(gameBoard.playingArea)
    }

    private fun compactTilesUp(playingArea: Array<IntArray>): Array<IntArray> {
        playingArea.forEachIndexed { indexRow, row ->
            row.forEachIndexed columnLoop@{ indexColumn, tile ->
                if (0 != tile) {
                    return@columnLoop
                }
                for (row2 in indexRow + 1 until playingArea.lastIndex + 1) {
                    if (0 != playingArea[row2][indexColumn]) {
                        playingArea[indexRow][indexColumn] = playingArea[row2][indexColumn]
                        playingArea[row2][indexColumn] = 0
                        break
                    }
                }
            }
        }
        return playingArea
    }

    private fun areTilesNonZeroAndDifferent(tile1: Int, tile2: Int): Boolean {
        return 0 != tile1 && tile1 != tile2
    }

}