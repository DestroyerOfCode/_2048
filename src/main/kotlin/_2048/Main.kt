package _2048

fun main() {
    val gameBoard = GameBoard()
    val playerService: PlayerService = PlayerServiceImpl(gameBoard)
    val gameBoardService: GameBoardService = GameBoardServiceImpl(gameBoard, playerService)
    gameBoardService.start()
}