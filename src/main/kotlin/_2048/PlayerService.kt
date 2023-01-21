package _2048

interface PlayerService {
    fun addNewTile(): GameBoard
    fun makeMove(move: String): Direction?
}