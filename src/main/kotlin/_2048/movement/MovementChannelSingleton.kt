package _2048.movement

import _2048.gameboard.Direction
import _2048.gameboard.GameBoard
import kotlinx.coroutines.channels.Channel
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class MovementChannelSingleton {
    companion object {
        private var instance: MovementChannelSingleton? = null
        private val LOGGER: Logger = LoggerFactory.getLogger(GameBoard::class.java)
        private val movementChannel: Channel<Direction> = Channel(
            capacity = Channel.UNLIMITED,
            onUndeliveredElement = { LOGGER.error("Undelivered message $this") })

        fun getInstance(): MovementChannelSingleton {
            if (instance == null) {
                instance = MovementChannelSingleton()
            }
            return instance as MovementChannelSingleton
        }


    }

    suspend fun sendDirection(direction: Direction) {
        movementChannel.send(direction)
    }

    suspend fun receiveDirection(): Direction {
        return movementChannel.receive()
    }
}