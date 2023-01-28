package _2048

import _2048.gameboard.GameBoard
import _2048.gameboard.GameBoardService
import _2048.gameboard.GameBoardServiceImpl
import _2048.movement.MovementService
import _2048.movement.MovementServiceImpl
import _2048.player.PlayerService
import _2048.player.PlayerServiceImpl

fun main() {
    val gameBoard = GameBoard()
    val playerService: PlayerService = PlayerServiceImpl(gameBoard)
    val movementService: MovementService = MovementServiceImpl(gameBoard)
    val gameBoardService: GameBoardService = GameBoardServiceImpl(gameBoard, playerService, movementService)
    gameBoardService.playGame()
}