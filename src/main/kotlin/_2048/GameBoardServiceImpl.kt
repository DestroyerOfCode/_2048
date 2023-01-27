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
        printBoard()
        gameLoop@ while (movementService.canMakeMove()) {
            try {
                val direction: Direction = chooseDirectionToShift()

                //when making a move that would change no position of tiles
                if (!movementService.isMoveLegal(direction)) {
                    continue@gameLoop
                }

                //both players movement
                playRound(direction)

            } catch (ex: IllegalMoveException) {
                LOGGER.error(ex.message)
            }
        }
    }

    private fun playRound(direction: Direction) {
        gameBoard.playingArea = movementService.shift(direction)
        playerService.addNewTile()
        printBoard()
        println(gameBoard.score)
    }

    private fun chooseDirectionToShift(): Direction {
        val directionString: String = readln()
        val direction: Direction? = playerService.getDirectionOfShift(directionString)

        return direction!!
    }

}