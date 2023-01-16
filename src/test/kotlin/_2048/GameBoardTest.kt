package _2048

import org.junit.jupiter.api.Test
import kotlin.test.assertContentEquals

class GameBoardTest {

    companion object {

        const val BOX_HEIGHT = 4
        const val BOX_WIDTH = 4
    }

    private var gameBoard: GameBoard = GameBoard(Array(BOX_HEIGHT) { IntArray(BOX_WIDTH) })


    @Test
    fun start() {
        //given

    }

    @Test
    fun whenShiftUpAndNoConcatenation_ThenShiftBoardUp() {
        //given
        val board: Array<IntArray> = arrayOf(
            intArrayOf(0, 0, 0, 0),
            intArrayOf(0, 0, 0, 0),
            intArrayOf(0, 0, 0, 0),
            intArrayOf(0, 0, 2, 0)
        )
        gameBoard = GameBoard(board)

        // when
        val boardRes: Array<IntArray> = gameBoard.shift(Direction.UP)

        //then
        assertContentEquals(intArrayOf(0, 0, 2, 0), boardRes[0])   //0
        assertContentEquals(intArrayOf(0, 0, 0, 0), boardRes[1])   //1
        assertContentEquals(intArrayOf(0, 0, 0, 0), boardRes[2])   //2
        assertContentEquals(intArrayOf(0, 0, 0, 0), boardRes[3])   //3

    }

    @Test
    fun whenShiftUpAndConcatenationWithoutBlockingNumber_ThenShiftBoardUpAndAddNumber() {
        //given
        val board: Array<IntArray> = arrayOf(
            intArrayOf(0, 0, 0, 0),
            intArrayOf(0, 0, 0, 0),
            intArrayOf(0, 0, 2, 0),
            intArrayOf(0, 0, 2, 0)
        )
        gameBoard = GameBoard(board)

        // when
        val boardRes: Array<IntArray> = gameBoard.shift(Direction.UP)

        //then
        assertContentEquals(intArrayOf(0, 0, 4, 0), boardRes[0])   //0
        assertContentEquals(intArrayOf(0, 0, 0, 0), boardRes[1])   //1
        assertContentEquals(intArrayOf(0, 0, 0, 0), boardRes[2])   //2
        assertContentEquals(intArrayOf(0, 0, 0, 0), boardRes[3])   //3

    }

    @Test
    fun whenShiftUpAndConcatenationWithBlockingNumber_ThenShiftBoardUpAndAddNumber() {
        //given
        val board: Array<IntArray> = arrayOf(
            intArrayOf(0, 0, 0, 0),
            intArrayOf(0, 0, 4, 0),
            intArrayOf(0, 0, 2, 0),
            intArrayOf(0, 0, 2, 0)
        )
        gameBoard = GameBoard(board)

        // when
        val boardRes: Array<IntArray> = gameBoard.shift(Direction.UP)
        gameBoard.printBoard()
        //then
        assertContentEquals(intArrayOf(0, 0, 4, 0), boardRes[0])   //0
        assertContentEquals(intArrayOf(0, 0, 4, 0), boardRes[1])   //1
        assertContentEquals(intArrayOf(0, 0, 0, 0), boardRes[2])   //2
        assertContentEquals(intArrayOf(0, 0, 0, 0), boardRes[3])   //3

    }

    @Test
    fun whenShiftUpAndConcatenationWithBlockingNumberAndSingleNumberInColumn_ThenShiftBoardUpAndAddNumber() {
        //given
        val board: Array<IntArray> = arrayOf(
            intArrayOf(0, 0, 0, 0), //0
            intArrayOf(0, 0, 4, 0), //1
            intArrayOf(0, 0, 2, 0), //2
            intArrayOf(2, 0, 2, 0)
        ) //3
        gameBoard = GameBoard(board)

        // when
        val boardRes: Array<IntArray> = gameBoard.shift(Direction.UP)

        //then
        /*  2,0,4,0
            0,0,4,0
            0,0,0,0
            0,0,0,0 */
        gameBoard.printBoard()
        assertContentEquals(intArrayOf(2, 0, 4, 0), boardRes[0])   //0
        assertContentEquals(intArrayOf(0, 0, 4, 0), boardRes[1])   //1
        assertContentEquals(intArrayOf(0, 0, 0, 0), boardRes[2])   //2
        assertContentEquals(intArrayOf(0, 0, 0, 0), boardRes[3])   //3

    }

    @Test
    fun whenShiftUpAndThreeSameNumbers_ThenMergeOnlyUppermostPair() {
        //given
        val board: Array<IntArray> = arrayOf(
            intArrayOf(0, 0, 4, 2), //0
            intArrayOf(0, 2, 2, 2), //1
            intArrayOf(0, 2, 2, 0), //2
            intArrayOf(2, 0, 2, 2)
        ) //3
        gameBoard = GameBoard(board)

        // when

        val boardRes: Array<IntArray> = gameBoard.shift(Direction.UP)
        //then
        /*  2,4,4,4
            0,0,4,2
            0,0,2,0
            0,0,0,0 */
        gameBoard.printBoard()
        assertContentEquals(intArrayOf(2, 4, 4, 4), boardRes[0])   //0
        assertContentEquals(intArrayOf(0, 0, 4, 2), boardRes[1])   //1
        assertContentEquals(intArrayOf(0, 0, 2, 0), boardRes[2])   //2
        assertContentEquals(intArrayOf(0, 0, 0, 0), boardRes[3])   //3

    }

    @Test
    fun whenShiftUp_ThenMoveAndCompactUp() {
        //given
        val board: Array<IntArray> = arrayOf(
            intArrayOf(0, 0, 2, 2), //0
            intArrayOf(2, 2, 2, 0), //1
            intArrayOf(0, 2, 2, 2), //2
            intArrayOf(0, 0, 4, 2)
        ) //3
        gameBoard = GameBoard(board)

        //when
        val boardRes: Array<IntArray> = gameBoard.shift(Direction.UP)

        //then
        /*  2,4,4,4
            0,0,2,2
            0,0,4,0
            0,0,0,0 */
        gameBoard.printBoard()
        assertContentEquals(intArrayOf(2, 4, 4, 4), boardRes[0])   //0
        assertContentEquals(intArrayOf(0, 0, 2, 2), boardRes[1])   //1
        assertContentEquals(intArrayOf(0, 0, 4, 0), boardRes[2])   //2
        assertContentEquals(intArrayOf(0, 0, 0, 0), boardRes[3])   //3
    }

    @Test
    fun whenShiftUpWithLargerNumbers_ThenMoveAndCompactUp() {
        //given
        val board: Array<IntArray> = arrayOf(
            /*0*/ intArrayOf(8, 0, 2, 128),
            /*1*/ intArrayOf(8, 32, 4, 64),
            /*2*/ intArrayOf(4, 2, 4, 2),
            /*3*/ intArrayOf(0, 32, 8, 2)
        ) //3
        gameBoard = GameBoard(board)

        //when
        val boardRes: Array<IntArray> = gameBoard.shift(Direction.UP)

        //then
        /*  16,32,2,128
            4,  2,8, 64
            0, 32,8,  4
            0,  0,0,  0 */
        gameBoard.printBoard()
        assertContentEquals(intArrayOf(16, 32, 2, 128), boardRes[0])   //0
        assertContentEquals(intArrayOf(4, 2, 8, 64), boardRes[1])   //1
        assertContentEquals(intArrayOf(0, 32, 8, 4), boardRes[2])   //2
        assertContentEquals(intArrayOf(0, 0, 0, 0), boardRes[3])   //3
    }

    @Test
    fun whenShiftDown_ThenMoveAndCompactDown() {
        //given
        val board: Array<IntArray> = arrayOf(
            intArrayOf(0, 0, 4, 2), //0
            intArrayOf(2, 2, 2, 2), //1
            intArrayOf(0, 2, 2, 0), //2
            intArrayOf(0, 0, 2, 2)  //3
        )
        gameBoard = GameBoard(board)

        //when
        val boardRes: Array<IntArray> = gameBoard.shift(Direction.DOWN)

        //then
        /*  0,0,0,0
            0,0,4,0
            0,0,2,2
            2,4,4,4 */
        gameBoard.printBoard()
        assertContentEquals(intArrayOf(0, 0, 0, 0), boardRes.get(0))   //0
        assertContentEquals(intArrayOf(0, 0, 4, 0), boardRes.get(1))   //1
        assertContentEquals(intArrayOf(0, 0, 2, 2), boardRes.get(2))   //2
        assertContentEquals(intArrayOf(2, 4, 4, 4), boardRes.get(3))   //3
    }

    @Test
    fun whenShiftDownWithLargeNumbers_ThenMoveAndCompactDown() {
        //given
        val board: Array<IntArray> = arrayOf(
            /*0*/ intArrayOf(8, 0, 2, 128),
            /*1*/ intArrayOf(8, 32, 4, 64),
            /*2*/ intArrayOf(4, 2, 4, 2),
            /*3*/ intArrayOf(0, 32, 8, 2)
        )
        gameBoard = GameBoard(board)

        //when
        val boardRes: Array<IntArray> = gameBoard.shift(Direction.DOWN)

        //then
        /*  16,32,2,128
            4,  2,8, 64
            0, 32,8,  4
            0,  0,0,  0 */
        gameBoard.printBoard()
        assertContentEquals(intArrayOf(0, 0, 0, 0), boardRes[0])   //0
        assertContentEquals(intArrayOf(0, 32, 2, 128), boardRes[1])   //1
        assertContentEquals(intArrayOf(16, 2, 8, 64), boardRes[2])   //2
        assertContentEquals(intArrayOf(4, 32, 8, 4), boardRes[3])   //3
    }
}