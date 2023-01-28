package _2048.player

import _2048.gameboard.Direction
import _2048.gameboard.GameBoard
import _2048.movement.IllegalMoveException
import java.util.Objects.isNull
import kotlin.random.Random

class PlayerServiceImpl(
    private val gameBoard: GameBoard,
) : PlayerService {
    companion object {
        val validMoves = listOf("w", "s", "a", "d", "W", "S", "A", "D")
    }

    override fun addNewTile() {
        val freeTiles: MutableMap<Int, MutableList<Int>> = HashMap()

        findFreeTiles(freeTiles)

        addTileToRandomFreeSpot(freeTiles)
    }

    override fun getDirectionOfShift(move: String): Direction? {
        isMoveValid(move)

        return Direction.values().find { it.keyboardButton == move.lowercase() }
    }

    private fun isMoveValid(move: String) {
        if (!validMoves.contains(move)) {
            throw IllegalMoveException("Invalid move $move. Please use one of ${validMoves.joinToString { "'$it'" }}")
        }
    }

    private fun findFreeTiles(freeTiles: MutableMap<Int, MutableList<Int>>) {
        gameBoard.playingArea.forEachIndexed { indexRow, row ->
            row.forEachIndexed columnLoop@{ indexColumn, tile ->
                if (0 != tile) {
                    return@columnLoop
                }

                if (isNull(freeTiles[indexRow])) {
                    freeTiles[indexRow] = mutableListOf(indexColumn)
                    return@columnLoop
                }

                freeTiles[indexRow]?.add(indexColumn)
            }
        }
    }

    private fun addTileToRandomFreeSpot(freeTiles: MutableMap<Int, MutableList<Int>>) {
        if (freeTiles.keys.isEmpty()) {
            return
        }
        val newHeightIndex: Int? = freeTiles.keys.asSequence().shuffled().find { true }
        val newColumnIndex: Int? = freeTiles[newHeightIndex]?.asSequence()?.shuffled()?.find { true }

        gameBoard.playingArea[newHeightIndex!!][newColumnIndex!!] = if (Random.nextBoolean()) 2 else 4
    }
}