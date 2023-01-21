package _2048

class GameBoardServiceImpl(
    private val gameBoard: GameBoard = GameBoard(),
    private val playerService: PlayerService,
    private val movementService: MovementService
) : GameBoardService {

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

    override fun start() {
        while (canMakeMove()) {
            val directionString: String = readln()
            var direction: Direction?
            try {
                direction = playerService.makeMove(directionString)
                movementService.shift(direction!!)
                playerService.addNewTile()
            } catch (ex: IllegalMoveException) {

            }
        }
    }

    private fun canMakeMove(): Boolean {
        TODO("Not yet implemented")
    }
}