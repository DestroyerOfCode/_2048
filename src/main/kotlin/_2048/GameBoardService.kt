package _2048

interface GameBoardService {

    fun shift(direction: Direction): Array<IntArray>
    fun shiftUp(): Array<IntArray>
    fun shiftDown(): Array<IntArray>
    fun shiftRight(): Array<IntArray>
    fun shiftLeft(): Array<IntArray>
    fun printBoard()
    fun start()
}