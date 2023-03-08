package _2048.gameboard

import _2048.GameBoardView
import _2048.movement.IllegalMoveException
import _2048.movement.MovementService
import _2048.player.PlayerService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class GameBoardServiceImpl(
    private val gameBoard: GameBoard = GameBoard(),
    private val playerService: PlayerService,
    private val movementService: MovementService,
    private val gameBoardView: GameBoardView,
) : GameBoardService {
    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(GameBoardServiceImpl::class.java)
    }

    override suspend fun playGame() {
        gameLoop@ while (movementService.canMakeMove()) {
            try {
                val direction: Direction = withContext(Dispatchers.Default) {
                    Direction.DOWN
                }
//                    chooseDirectionToShift()
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

    private fun printBoard() {
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

    override fun playRound(direction: Direction) {
        movementService.shift(direction)
        playerService.addNewTile()
        printBoard()
        println(gameBoard.score)
    }

    override fun chooseDirection(direction: Direction) {
        direction
    }
    private fun chooseDirectionToShift(): Direction {
        val directionString: String = readln()
        val direction: Direction? = playerService.getDirectionOfShift(directionString)

        return direction!!
    }
}