package _2048.movement

import _2048.gameboard.Direction

interface MovementService {
    /**
     * Calculates if we can make a move and thus if game is over
     * @return true if game can continue, false if game has ended
     */
    fun canMakeMove(): Boolean
    /**
     * Calculates if move made by human player can shift board. Does not validate if input is valid
     * @return true if can shift board, otherwise false
     * @param direction where player is attempting to move
     */
    fun isMoveLegal(direction: Direction): Boolean
    /**
     * Shifts board in any of the four directions - up, down, left, right
     * Compact method is called before and after merging
     * Shifts the single instance of the board the class has
     * @param direction where player is attempting to shift the board
     */
    fun shift(direction: Direction)

}