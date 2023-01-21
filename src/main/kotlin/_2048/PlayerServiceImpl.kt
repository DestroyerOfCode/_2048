package _2048

import kotlin.random.Random

class PlayerServiceImpl(
    private var gameBoard: GameBoard,
    private var gameBoardService: GameBoardService
) : PlayerService {

    override fun addNewTile(): GameBoard {

        val freeTiles: MutableMap<Int, MutableList<Int>> = HashMap()

        findFreeTiles(freeTiles)

        addTileToRandomFreeSpot(freeTiles)

        return gameBoard
    }

    override fun makeMove() {

        val move: String = readln()
        if (move !in ("w, W, s, S, a, A, d, D")) {
            throw IllegalMoveException()
        }

        gameBoardService.shift(Direction.valueOf(move))
    }

    private fun findFreeTiles(freeTiles: MutableMap<Int, MutableList<Int>>) {
        gameBoard.playingArea.forEachIndexed columnLoop@{ indexRow, row ->
            row.forEachIndexed { indexColumn, tile ->
                if (0 != tile) {
                    return@columnLoop
                }

                if (freeTiles[indexRow] == null) {
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