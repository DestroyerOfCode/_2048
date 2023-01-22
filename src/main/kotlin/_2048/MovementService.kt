package _2048

interface MovementService {
    fun shift(direction: Direction): Array<IntArray>
    fun shiftUp(): Array<IntArray>
    fun shiftDown(): Array<IntArray>
    fun shiftRight(): Array<IntArray>
    fun shiftLeft(): Array<IntArray>
    fun canMakeMove(direction: Direction): Boolean
    fun canMakeMove(): Boolean

}