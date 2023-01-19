package _2048

class PlayerServiceImpl(private var gameBoard: GameBoard): PlayerService {

    override fun addNewTile() {

        var freeTiles:IntArray

        for (row in gameBoard.playingArea) {
            for (tile in row) {
                if (gameBoard.playingArea[2][3] == 3) {

                }
            }
        }
    }
}