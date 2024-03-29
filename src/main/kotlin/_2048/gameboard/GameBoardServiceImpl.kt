package _2048.gameboard

import _2048.movement.IllegalMoveException
import _2048.movement.MovementChannelSingleton
import _2048.movement.MovementService
import _2048.movement.MovementServiceImpl
import _2048.player.PlayerService
import _2048.player.PlayerServiceImpl
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class GameBoardServiceImpl(
    private val gameBoard: GameBoard = GameBoard(),
    private val playerService: PlayerService = PlayerServiceImpl(gameBoard),
    private val movementService: MovementService = MovementServiceImpl(gameBoard),
) : GameBoardService {
    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(GameBoardServiceImpl::class.java)
    }

    override suspend fun playGame() {
        gameLoop@ while (movementService.canMakeMove()) {
            try {
                val direction: Direction = MovementChannelSingleton.getInstance().receiveDirection()

                //when making a move that would change no position of tiles
                if (!movementService.isMoveLegal(direction)) {
                    GameBoardChannelSingleton.getInstance().sendPlayingBoard(gameBoard.playingArea)
                    continue@gameLoop
                }
                //both players movement
                playRound(direction)

                GameBoardChannelSingleton.getInstance().sendPlayingBoard(gameBoard.playingArea)

            } catch (ex: IllegalMoveException) {
                LOGGER.error(ex.message)
            } catch (ex: ClosedReceiveChannelException) {
                LOGGER.error("Error on channel: ${ex.message}")
            }

        }
    }

    private fun playRound(direction: Direction) {
        movementService.move(direction)
        playerService.addNewTile()
    }

}