package _2048

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertNull

class GameBoardTest {

    private var gameBoard: GameBoard = GameBoard();

    companion object {
        const val UP: Char = 'w'
        const val DOWN: Char = 's'
        const val RIGHT: Char = 'd'
        const val LEFT: Char = 'a'
        const val BOX_HEIGHT = 4
        const val BOX_WIDTH = 4
    }

    @Test
    fun start() {
        //given

    }
    @Test
    fun whenShiftUpAndNoConcatenation_ThenShiftBoardUp() {
        //given
        val board: Array<IntArray> = arrayOf(
            intArrayOf(0,0,0,0),
            intArrayOf(0,0,0,0),
            intArrayOf(0,0,0,0),
            intArrayOf(0,0,2,0))
        gameBoard = GameBoard(board)

        // when
        val boardRes: Array<IntArray>? = gameBoard.shift(Direction.UP)

        //then
        assertContentEquals(intArrayOf(0,0,2,0), boardRes?.get(0))   //0
        assertContentEquals(intArrayOf(0,0,0,0), boardRes?.get(1))   //1
        assertContentEquals(intArrayOf(0,0,0,0), boardRes?.get(2))   //2
        assertContentEquals(intArrayOf(0,0,0,0), boardRes?.get(3))   //3

    }
    @Test
    fun whenShiftUpAndConcatenationWithoutBlockingNumber_ThenShiftBoardUpAndAddNumber() {
        //given
        val board: Array<IntArray> = arrayOf(
            intArrayOf(0,0,0,0),
            intArrayOf(0,0,0,0),
            intArrayOf(0,0,2,0),
            intArrayOf(0,0,2,0))
        gameBoard = GameBoard(board)

        // when
        val boardRes: Array<IntArray>? = gameBoard.shift(Direction.UP)

        //then
        assertContentEquals(intArrayOf(0,0,4,0), boardRes?.get(0))   //0
        assertContentEquals(intArrayOf(0,0,0,0), boardRes?.get(1))   //1
        assertContentEquals(intArrayOf(0,0,0,0), boardRes?.get(2))   //2
        assertContentEquals(intArrayOf(0,0,0,0), boardRes?.get(3))   //3

    }
    @Test
    fun whenShiftUpAndConcatenationWithBlockingNumber_ThenShiftBoardUpAndAddNumber() {
        //given
        val board: Array<IntArray> = arrayOf(
            intArrayOf(0,0,0,0),
            intArrayOf(0,0,4,0),
            intArrayOf(0,0,2,0),
            intArrayOf(0,0,2,0))
        gameBoard = GameBoard(board)

        // when
        val boardRes: Array<IntArray>? = gameBoard.shift(Direction.UP)
gameBoard.printBoard()
        //then
        assertContentEquals(intArrayOf(0,0,4,0), boardRes?.get(0))   //0
        assertContentEquals(intArrayOf(0,0,4,0), boardRes?.get(1))   //1
        assertContentEquals(intArrayOf(0,0,0,0), boardRes?.get(2))   //2
        assertContentEquals(intArrayOf(0,0,0,0), boardRes?.get(3))   //3

    }
    @Test
    fun whenShiftUpAndConcatenationWithBlockingNumberAndSingleNumberInColumn_ThenShiftBoardUpAndAddNumber() {
        //given
        val board: Array<IntArray> = arrayOf(
            intArrayOf(0,0,0,0), //0
            intArrayOf(0,0,4,0), //1
            intArrayOf(0,0,2,0), //2
            intArrayOf(2,0,2,0)) //3
        gameBoard = GameBoard(board)

        // when
        val boardRes: Array<IntArray>? = gameBoard.shift(Direction.UP)

        //then
        /*  2,0,4,0
            0,0,4,0
            0,0,0,0
            0,0,0,0 */
        gameBoard.printBoard()
        assertContentEquals(intArrayOf(2,0,4,0), boardRes?.get(0))   //0
        assertContentEquals(intArrayOf(0,0,4,0), boardRes?.get(1))   //1
        assertContentEquals(intArrayOf(0,0,0,0), boardRes?.get(2))   //2
        assertContentEquals(intArrayOf(0,0,0,0), boardRes?.get(3))   //3

    }
    @Test
    fun whenShiftUpAndThreeSameNumbers_ThenMergeOnlyUppermostPair() {
        //given
        val board: Array<IntArray> = arrayOf(
            intArrayOf(0,0,4,2), //0
            intArrayOf(0,2,2,2), //1
            intArrayOf(0,2,2,0), //2
            intArrayOf(2,0,2,2)) //3
        gameBoard = GameBoard(board)

        // when
        val boardRes: Array<IntArray>? = gameBoard.shift(Direction.UP)

        //then
        /*  2,4,4,4
            0,0,4,2
            0,0,2,0
            0,0,0,0 */
        gameBoard.printBoard()
        assertContentEquals(intArrayOf(2,4,4,4), boardRes?.get(0))   //0
        assertContentEquals(intArrayOf(0,0,4,2), boardRes?.get(1))   //1
        assertContentEquals(intArrayOf(0,0,2,0), boardRes?.get(2))   //2
        assertContentEquals(intArrayOf(0,0,0,0), boardRes?.get(3))   //3

    }

    @Test
    @Disabled
    fun whenShiftDown_ThenMoveAndCompactDown() {
        //given
        val board: Array<IntArray> = arrayOf(
            intArrayOf(0,0,4,2), //0
            intArrayOf(2,2,2,2), //1
            intArrayOf(0,2,2,0), //2
            intArrayOf(0,0,2,2)) //3
        gameBoard = GameBoard(board)

        //when
        val boardRes: Array<IntArray>? = gameBoard.shift(Direction.DOWN)

        //then
        /*  0,0,0,0
            0,0,4,0
            0,0,2,2
            2,4,4,4 */
        gameBoard.printBoard()
        assertContentEquals(intArrayOf(0,0,0,0), boardRes?.get(0))   //0
        assertContentEquals(intArrayOf(0,0,4,0), boardRes?.get(1))   //1
        assertContentEquals(intArrayOf(0,0,2,2), boardRes?.get(2))   //2
        assertContentEquals(intArrayOf(2,4,4,4), boardRes?.get(3))   //3
    }
}