package _2048

interface PlayerService {
    fun addNewTile(): GameBoard
    fun getDirectionOfShift(move: String): Direction?
}