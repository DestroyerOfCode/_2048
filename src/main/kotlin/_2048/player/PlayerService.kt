package _2048.player

import _2048.gameboard.Direction

interface PlayerService {
    /**
     * Appends an additional 2 or 4 tile to the board randomly
     * Before appending a validation is run to prevent the method from adding
     * it on a non-empty tile
     */
    fun addNewTile()
    /**
     * Transposes user input from string to Direction enum
     * Validated for invalid user input
     * @return Direction Enumeration type of (UP, DOWN, LEFT, RIGHT)
     * @param move is taken from user input valid if one of 'w', 'W', 's', 'S', 'd', 'D', 'a', 'A'
     */
    fun getDirectionOfShift(move: String): Direction?
}