package _2048

interface MovementService {
    fun shift(direction: Direction): Array<IntArray>
    fun isMoveLegal(direction: Direction): Boolean
    fun canMakeMove(): Boolean

}