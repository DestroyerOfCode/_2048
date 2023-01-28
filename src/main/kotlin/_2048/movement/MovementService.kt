package _2048.movement

import _2048.gameboard.Direction

interface MovementService {
    fun shift(direction: Direction): Array<IntArray>
    fun isMoveLegal(direction: Direction): Boolean
    fun canMakeMove(): Boolean

}