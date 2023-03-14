package _2048.movement

import _2048.gameboard.Direction
import _2048.gameboard.Direction.*
import _2048.gameboard.GameBoard
import kotlinx.coroutines.channels.Channel

class MovementServiceImpl(private val gameBoard: GameBoard = GameBoard(movementChannel = Channel())) : MovementService {
    override fun canMakeMove(): Boolean {
        return isPlayingAreaNotFull() or Direction.values().filter { it != NONE }
            .any { direction -> isMoveLegal(direction) }
    }

    override fun isMoveLegal(direction: Direction): Boolean {
        return when (direction) {
            UP -> canShift(transposeFromRowToColumn(gameBoard.playingArea))
            DOWN -> canShift(transposeFromRowToColumn(gameBoard.playingArea.map { it.reversedArray() }.toTypedArray()))
            LEFT -> canShift(gameBoard.playingArea.map { it.reversedArray() }.toTypedArray())
            RIGHT -> canShift(gameBoard.playingArea)
            NONE -> false
        }
    }

    override fun shift(direction: Direction) {
        when (direction) {
            UP -> shiftUpMirrorImageAndRevertBack()
            DOWN -> shiftDownMirrorImageAndRevertBack()
            LEFT -> shiftLeft()
            RIGHT -> shiftRightMirrorImageAndRevertBack()
            NONE -> {}
        }
    }

    private fun shiftLeft() {
        gameBoard.playingArea = shift(gameBoard.playingArea)
    }

    //reversing twice because I need to return the original board
    private fun shiftRightMirrorImageAndRevertBack() {
        val shiftedPlayingArea: Array<IntArray> = shift(gameBoard.playingArea.map { it.reversedArray() }.toTypedArray())
        gameBoard.playingArea = shiftedPlayingArea.map { it.reversedArray() }.toTypedArray()
    }

    private fun shiftUpMirrorImageAndRevertBack() {
        val shiftedPlayingArea: Array<IntArray> = shift(transposeFromRowToColumn(gameBoard.playingArea))
        gameBoard.playingArea = transposeFromRowToColumn(shiftedPlayingArea)
    }

    //transposing and reversing twice because I need to return the original board
    private fun shiftDownMirrorImageAndRevertBack() {
        val shiftedPlayingArea: Array<IntArray> =
            shift(transposeFromRowToColumn(gameBoard.playingArea).map { it.reversedArray() }.toTypedArray())
        gameBoard.playingArea = transposeFromRowToColumn(shiftedPlayingArea).reversedArray()
    }

    private fun canCompactTiles(i: Int, row: IntArray): Boolean {
        for (j in i - 1 downTo 0) {
            if (row[j] != 0) {
                return true
            }
        }
        return false
    }

    private fun canMergeTiles(rowIndex: Int, row: IntArray): Boolean {
        for (j in rowIndex - 1 downTo 0) {
            if (row[rowIndex] == row[j]) {
                return true
            }
            if (row[j] != 0) {
                break
            }
        }
        return false
    }

    private fun canShift(row: IntArray): Boolean {
        for (i in row.lastIndex downTo 1) {
            if (isRowEmpty(row, i)) {
                if (canCompactTiles(i, row)) {
                    return true
                }
            } else {
                if (canMergeTiles(i, row)) {
                    return true
                }
            }
        }
        return false
    }

    private fun shift(playingArea: Array<IntArray>): Array<IntArray> {
        compactTiles(playingArea)
        mergeTiles(playingArea)
        compactTiles(playingArea)
        return playingArea
    }

    private fun canShift(playingArea: Array<IntArray>): Boolean {
        return playingArea.any { canShift(it) }
    }

    private fun compactTiles(playingArea: Array<IntArray>): Array<IntArray> {
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

    private fun mergeTiles(playingArea: Array<IntArray>): Array<IntArray> {
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
                        gameBoard.score += tile
                        return@columnLoop
                    } else if (areTilesNonZeroAndDifferent(playingArea[indexRow][column2], tile)) {
                        break
                    }
                }
            }
        }
        return playingArea
    }

    private fun transposeFromRowToColumn(playingArea: Array<IntArray>): Array<IntArray> {
        val transposedPlayingArea: Array<IntArray> = Array(playingArea[0].size) { IntArray(playingArea.size) }
        playingArea.forEachIndexed { rowIndex, row ->
            for (i in 0 until row.lastIndex + 1) {
                transposedPlayingArea[i][rowIndex] = playingArea[rowIndex][i]
            }
        }

        return transposedPlayingArea
    }

    private fun isPlayingAreaNotFull(): Boolean = gameBoard.playingArea.any { row -> row.contains(0) }
    private fun isRowEmpty(row: IntArray, i: Int) = 0 == row[i]
    private fun areTilesNonZeroAndDifferent(tile1: Int, tile2: Int): Boolean = 0 != tile1 && tile1 != tile2
}