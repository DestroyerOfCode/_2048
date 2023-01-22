package _2048

import org.slf4j.Logger
import org.slf4j.LoggerFactory

class GameBoardServiceImpl(
    private val gameBoard: GameBoard = GameBoard(),
    private val playerService: PlayerService,
    private val movementService: MovementService
) : GameBoardService {

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(GameBoardServiceImpl::class.java)
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

    override fun playGame() {
        while (movementService.canMakeMove()) {
            try {
                playRound()
            } catch (ex: IllegalMoveException) {
                LOGGER.error(ex.message)
            }
        }
    }

    private fun playRound() {
        val directionString: String = readln()
        val direction: Direction? = playerService.makeMove(directionString)
        movementService.shift(direction!!)
        playerService.addNewTile()
//        calculateScoreCount()
        printBoard()
    }

    override fun calculateScoreCount() {
        TODO("Not yet implemented")
    }
}