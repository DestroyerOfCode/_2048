package _2048.gameboard

import _2048.gameboard.Direction
import _2048.gameboard.GameBoard
import kotlinx.coroutines.channels.Channel
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class GameBoardChannelSingleton {
    companion object {
        private var instance: GameBoardChannelSingleton? = null
        private val gameBoardChannel: Channel<Array<IntArray>> = Channel(capacity = Channel.UNLIMITED)

        fun getInstance(): GameBoardChannelSingleton {
            if (instance == null) {
                instance = GameBoardChannelSingleton()
            }
            return instance as GameBoardChannelSingleton
        }
    }

    suspend fun sendPlayingBoard(playingBoard: Array<IntArray>) {
        gameBoardChannel.send(playingBoard)
    }

    suspend fun receivePlayingBoard(): Array<IntArray> {
        return gameBoardChannel.receive()
    }
}