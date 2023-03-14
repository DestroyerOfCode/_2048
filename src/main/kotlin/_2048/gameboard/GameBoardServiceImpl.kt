package _2048.gameboard

import _2048.movement.IllegalMoveException
import _2048.movement.MovementService
import _2048.player.PlayerService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlinx.coroutines.withContext
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class GameBoardServiceImpl(
    private val gameBoard: GameBoard = GameBoard(movementChannel = Channel()),
    private val playerService: PlayerService,
    private val movementService: MovementService,
) : GameBoardService {
    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(GameBoardServiceImpl::class.java)
    }

    override suspend fun playGame() {
        gameLoop@ while (movementService.canMakeMove()) {
            try {
                val direction: Direction = gameBoard.movementChannel.receive()

                //when making a move that would change no position of tiles
                if (!movementService.isMoveLegal(direction)) {
                    gameBoard.isMovedChannel.send(false)
                    continue@gameLoop
                }
                //both players movement
                playRound(direction)
                gameBoard.isMovedChannel.send(true)
            } catch (ex: IllegalMoveException) {
                LOGGER.error(ex.message)
            } catch (ex: ClosedReceiveChannelException) {
                LOGGER.error("Error on channel: ${ex.message}")
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
//        printBoard()
//        println(gameBoard.score)
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