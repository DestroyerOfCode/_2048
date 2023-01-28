package _2048.player

import _2048.gameboard.Direction
import _2048.gameboard.GameBoard

interface PlayerService {
    fun addNewTile(): GameBoard
    fun getDirectionOfShift(move: String): Direction?
}