package _2048

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import kotlin.test.assertContentEquals

class MovementServiceImplTest {

    private lateinit var movementService: MovementService

    @Test
    fun whenShiftUpAndNoConcatenation_ThenShiftBoardUp() {
        //given
        val board: Array<IntArray> = arrayOf(
            intArrayOf(0, 0, 0, 0),
            intArrayOf(0, 0, 0, 0),
            intArrayOf(0, 0, 0, 0),
            intArrayOf(0, 0, 2, 0)
        )
        movementService = MovementServiceImpl(GameBoard(board))

        // when
        val playingAreaRes: Array<IntArray> = movementService.shift(Direction.UP)

        //then
        assertContentEquals(intArrayOf(0, 0, 2, 0), playingAreaRes[0])   //0
        assertContentEquals(intArrayOf(0, 0, 0, 0), playingAreaRes[1])   //1
        assertContentEquals(intArrayOf(0, 0, 0, 0), playingAreaRes[2])   //2
        assertContentEquals(intArrayOf(0, 0, 0, 0), playingAreaRes[3])   //3

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
        movementService = MovementServiceImpl(GameBoard(board))

        // when
        val playingAreaRes: Array<IntArray> = movementService.shift(Direction.UP)

        //then
        assertContentEquals(intArrayOf(0, 0, 4, 0), playingAreaRes[0])   //0
        assertContentEquals(intArrayOf(0, 0, 0, 0), playingAreaRes[1])   //1
        assertContentEquals(intArrayOf(0, 0, 0, 0), playingAreaRes[2])   //2
        assertContentEquals(intArrayOf(0, 0, 0, 0), playingAreaRes[3])   //3

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
        movementService = MovementServiceImpl(GameBoard(board))

        // when
        val playingAreaRes: Array<IntArray> = movementService.shift(Direction.UP)

        //then
        assertContentEquals(intArrayOf(0, 0, 4, 0), playingAreaRes[0])   //0
        assertContentEquals(intArrayOf(0, 0, 4, 0), playingAreaRes[1])   //1
        assertContentEquals(intArrayOf(0, 0, 0, 0), playingAreaRes[2])   //2
        assertContentEquals(intArrayOf(0, 0, 0, 0), playingAreaRes[3])   //3

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
        movementService = MovementServiceImpl(GameBoard(board))

        // when
        val playingAreaRes: Array<IntArray> = movementService.shift(Direction.UP)

        //then
        /*  2,0,4,0
            0,0,4,0
            0,0,0,0
            0,0,0,0 */
        assertContentEquals(intArrayOf(2, 0, 4, 0), playingAreaRes[0])   //0
        assertContentEquals(intArrayOf(0, 0, 4, 0), playingAreaRes[1])   //1
        assertContentEquals(intArrayOf(0, 0, 0, 0), playingAreaRes[2])   //2
        assertContentEquals(intArrayOf(0, 0, 0, 0), playingAreaRes[3])   //3

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
        movementService = MovementServiceImpl(GameBoard(board))

        // when

        val playingAreaRes: Array<IntArray> = movementService.shift(Direction.UP)
        //then
        /*  2,4,4,4
            0,0,4,2
            0,0,2,0
            0,0,0,0 */
        assertContentEquals(intArrayOf(2, 4, 4, 4), playingAreaRes[0])   //0
        assertContentEquals(intArrayOf(0, 0, 4, 2), playingAreaRes[1])   //1
        assertContentEquals(intArrayOf(0, 0, 2, 0), playingAreaRes[2])   //2
        assertContentEquals(intArrayOf(0, 0, 0, 0), playingAreaRes[3])   //3

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
        movementService = MovementServiceImpl(GameBoard(board))

        //when
        val playingAreaRes: Array<IntArray> = movementService.shift(Direction.UP)

        //then
        /*  2,4,4,4
            0,0,2,2
            0,0,4,0
            0,0,0,0 */
        assertContentEquals(intArrayOf(2, 4, 4, 4), playingAreaRes[0])   //0
        assertContentEquals(intArrayOf(0, 0, 2, 2), playingAreaRes[1])   //1
        assertContentEquals(intArrayOf(0, 0, 4, 0), playingAreaRes[2])   //2
        assertContentEquals(intArrayOf(0, 0, 0, 0), playingAreaRes[3])   //3
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
        movementService = MovementServiceImpl(GameBoard(board))

        //when
        val playingAreaRes: Array<IntArray> = movementService.shift(Direction.UP)

        //then
        /*  16,32,2,128
            4,  2,8, 64
            0, 32,8,  4
            0,  0,0,  0 */
        assertContentEquals(intArrayOf(16, 32, 2, 128), playingAreaRes[0])   //0
        assertContentEquals(intArrayOf(4, 2, 8, 64), playingAreaRes[1])   //1
        assertContentEquals(intArrayOf(0, 32, 8, 4), playingAreaRes[2])   //2
        assertContentEquals(intArrayOf(0, 0, 0, 0), playingAreaRes[3])   //3
    }

    @Test
    fun whenShiftUp_ThenMoveAndCompactUp2() {
        //given
        val board: Array<IntArray> = arrayOf(
            /*0*/ intArrayOf(2, 4, 2, 2),
            /*1*/ intArrayOf(2, 2, 2, 0),
            /*2*/ intArrayOf(4, 2, 0, 4),
            /*3*/ intArrayOf(2, 4, 2, 2)
        )
        movementService = MovementServiceImpl(GameBoard(board))

        //when
        val playingAreaRes: Array<IntArray> = movementService.shift(Direction.UP)

        //then
        /*  4, 4, 4, 2
            4, 4, 2, 4
            2, 4, 0, 2
            0, 0, 0, 0 */
        assertContentEquals(intArrayOf(4, 4, 4, 2), playingAreaRes[0])   //0
        assertContentEquals(intArrayOf(4, 4, 2, 4), playingAreaRes[1])   //1
        assertContentEquals(intArrayOf(2, 4, 0, 2), playingAreaRes[2])   //2
        assertContentEquals(intArrayOf(0, 0, 0, 0), playingAreaRes[3])   //3
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
        movementService = MovementServiceImpl(GameBoard(board))

        //when
        val playingAreaRes: Array<IntArray> = movementService.shift(Direction.DOWN)

        //then
        /*  0,0,0,0
            0,0,4,0
            0,0,2,2
            2,4,4,4 */
        assertContentEquals(intArrayOf(0, 0, 0, 0), playingAreaRes[0])   //0
        assertContentEquals(intArrayOf(0, 0, 4, 0), playingAreaRes[1])   //1
        assertContentEquals(intArrayOf(0, 0, 2, 2), playingAreaRes[2])   //2
        assertContentEquals(intArrayOf(2, 4, 4, 4), playingAreaRes[3])   //3
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
        movementService = MovementServiceImpl(GameBoard(board))

        //when
        val playingAreaRes: Array<IntArray> = movementService.shift(Direction.DOWN)

        //then
        /*  0, 0, 0, 0
            0, 32, 2, 128
            16, 2, 8, 64
            4, 32, 8, 4 */
        assertContentEquals(intArrayOf(0, 0, 0, 0), playingAreaRes[0])   //0
        assertContentEquals(intArrayOf(0, 32, 2, 128), playingAreaRes[1])   //1
        assertContentEquals(intArrayOf(16, 2, 8, 64), playingAreaRes[2])   //2
        assertContentEquals(intArrayOf(4, 32, 8, 4), playingAreaRes[3])   //3
    }

    @Test
    fun whenShiftDownWithLargeNumbers2_ThenMoveAndCompactDown() {
        //given
        val board: Array<IntArray> = arrayOf(
            /*0*/ intArrayOf(0, 32, 8, 2),
            /*1*/ intArrayOf(4, 2, 4, 2),
            /*2*/ intArrayOf(8, 32, 4, 64),
            /*3*/ intArrayOf(8, 0, 2, 128)
        )
        movementService = MovementServiceImpl(GameBoard(board))

        //when
        val playingAreaRes: Array<IntArray> = movementService.shift(Direction.DOWN)

        //then
        /*  0, 0, 0, 0
            0, 32, 8, 4
            4, 2, 8, 64
            16, 32, 2, 128 */
        assertContentEquals(intArrayOf(0, 0, 0, 0), playingAreaRes[0])   //0
        assertContentEquals(intArrayOf(0, 32, 8, 4), playingAreaRes[1])   //1
        assertContentEquals(intArrayOf(4, 2, 8, 64), playingAreaRes[2])   //2
        assertContentEquals(intArrayOf(16, 32, 2, 128), playingAreaRes[3])   //3
    }

    @Test
    fun whenShiftLeftWithLargeNumbers_ThenMoveAndCompactLeft() {
        //given
        val board: Array<IntArray> = arrayOf(
            /*0*/ intArrayOf(8, 8, 4, 0),
            /*1*/ intArrayOf(0, 32, 2, 32),
            /*2*/ intArrayOf(2, 4, 4, 8),
            /*3*/ intArrayOf(128, 64, 2, 2)
        )
        movementService = MovementServiceImpl(GameBoard(board))

        //when
        val playingAreaRes: Array<IntArray> = movementService.shift(Direction.LEFT)

        //then
        /*  16, 4, 0, 0
            32, 2, 32, 0
            2, 8, 8, 0
            128, 64, 4, 0 */
        assertContentEquals(intArrayOf(16, 4, 0, 0), playingAreaRes[0])   //0
        assertContentEquals(intArrayOf(32, 2, 32, 0), playingAreaRes[1])   //1
        assertContentEquals(intArrayOf(2, 8, 8, 0), playingAreaRes[2])   //2
        assertContentEquals(intArrayOf(128, 64, 4, 0), playingAreaRes[3])   //3
    }

    @Test
    fun whenShiftLeft_ThenMoveAndCompactLeft() {
        //given
        val board: Array<IntArray> = arrayOf(
            /*0*/ intArrayOf(2, 2, 4, 2),
            /*1*/ intArrayOf(4, 2, 2, 4),
            /*2*/ intArrayOf(2, 2, 0, 2),
            /*3*/ intArrayOf(2, 0, 4, 2)
        )
        movementService = MovementServiceImpl(GameBoard(board))

        //when
        val playingAreaRes: Array<IntArray> = movementService.shift(Direction.LEFT)

        //then
        /*  4, 4, 2, 0
            4, 4, 4, 0
            4, 2, 0, 0
            2, 4, 2, 0 */
        assertContentEquals(intArrayOf(4, 4, 2, 0), playingAreaRes[0])   //0
        assertContentEquals(intArrayOf(4, 4, 4, 0), playingAreaRes[1])   //1
        assertContentEquals(intArrayOf(4, 2, 0, 0), playingAreaRes[2])   //2
        assertContentEquals(intArrayOf(2, 4, 2, 0), playingAreaRes[3])   //3
    }

    @Test
    fun whenShiftRightWithLargeNumbers_ThenMoveAndCompactRight() {
        //given
        val board: Array<IntArray> = arrayOf(
            /*0*/ intArrayOf(0, 4, 8, 8),
            /*1*/ intArrayOf(32, 2, 32, 0),
            /*2*/ intArrayOf(8, 4, 4, 2),
            /*3*/ intArrayOf(2, 2, 64, 128)
        )
        movementService = MovementServiceImpl(GameBoard(board))

        //when
        val playingAreaRes: Array<IntArray> = movementService.shift(Direction.RIGHT)

        //then
        /*  0, 0, 4, 16
            0, 32, 2, 32
            0, 8, 8, 2
            0, 4, 64, 128 */
        assertContentEquals(intArrayOf(0, 0, 4, 16), playingAreaRes[0])   //0
        assertContentEquals(intArrayOf(0, 32, 2, 32), playingAreaRes[1])   //1
        assertContentEquals(intArrayOf(0, 8, 8, 2), playingAreaRes[2])   //2
        assertContentEquals(intArrayOf(0, 4, 64, 128), playingAreaRes[3])   //3
    }

    @Test
    fun whenShiftRight_ThenMoveAndCompactRight() {
        //given
        val board: Array<IntArray> = arrayOf(
            /*0*/ intArrayOf(2, 4, 2, 2),
            /*1*/ intArrayOf(4, 2, 2, 4),
            /*2*/ intArrayOf(2, 0, 2, 2),
            /*3*/ intArrayOf(2, 4, 0, 2)
        )
        movementService = MovementServiceImpl(GameBoard(board))

        //when
        val playingAreaRes: Array<IntArray> = movementService.shift(Direction.RIGHT)

        //then
        /*  0, 2, 4, 4
            0, 4, 4, 4
            0, 0, 2, 4
            0, 2, 4, 2 */
        assertContentEquals(intArrayOf(0, 2, 4, 4), playingAreaRes[0])   //0
        assertContentEquals(intArrayOf(0, 4, 4, 4), playingAreaRes[1])   //1
        assertContentEquals(intArrayOf(0, 0, 2, 4), playingAreaRes[2])   //2
        assertContentEquals(intArrayOf(0, 2, 4, 2), playingAreaRes[3])   //3
    }

    @Test
    fun whenShiftRight_ThenMoveAndCompactRight2() {
        //given
        val board: Array<IntArray> = arrayOf(
            /*0*/ intArrayOf(2, 2, 2, 2),
            /*1*/ intArrayOf(4, 4, 4, 4),
            /*2*/ intArrayOf(2, 0, 2, 2),
            /*3*/ intArrayOf(2, 4, 0, 2)
        )
        movementService = MovementServiceImpl(GameBoard(board))

        //when
        val playingAreaRes: Array<IntArray> = movementService.shift(Direction.RIGHT)

        //then
        /*  0, 0, 4, 4
            0, 0, 8, 8
            0, 0, 2, 4
            0, 2, 4, 2 */
        assertContentEquals(intArrayOf(0, 0, 4, 4), playingAreaRes[0])   //0
        assertContentEquals(intArrayOf(0, 0, 8, 8), playingAreaRes[1])   //1
        assertContentEquals(intArrayOf(0, 0, 2, 4), playingAreaRes[2])   //2
        assertContentEquals(intArrayOf(0, 2, 4, 2), playingAreaRes[3])   //3
    }

    @Test
    fun whenShiftRightWithBiggerBoard_ThenMoveAndCompactRight() {
        //given
        val board: Array<IntArray> = arrayOf(
            /*0*/ intArrayOf(2, 4, 2, 2, 2),
            /*1*/ intArrayOf(4, 2, 2, 4, 4),
            /*2*/ intArrayOf(2, 0, 2, 2, 8),
            /*3*/ intArrayOf(2, 4, 0, 2, 0)
        )
        movementService = MovementServiceImpl(GameBoard(board))

        //when
        val playingAreaRes: Array<IntArray> = movementService.shift(Direction.RIGHT)

        //then
        /*  0, 2, 4, 2, 4
            0, 0, 4, 4, 8
            0, 0, 2, 4, 8
            0, 0, 2, 4, 2 */

        assertContentEquals(intArrayOf(0, 2, 4, 2, 4), playingAreaRes[0])   //0
        assertContentEquals(intArrayOf(0, 0, 4, 4, 8), playingAreaRes[1])   //1
        assertContentEquals(intArrayOf(0, 0, 2, 4, 8), playingAreaRes[2])   //2
        assertContentEquals(intArrayOf(0, 0, 2, 4, 2), playingAreaRes[3])   //3
    }

    @Test
    fun whenMakeMoveToFreeDirectionUp_ThenReturnTrue() {
        //given
        val board: Array<IntArray> = arrayOf(
            /*0*/ intArrayOf(2, 2, 2, 2, 2),
            /*1*/ intArrayOf(4, 4, 4, 4, 4),
            /*2*/ intArrayOf(2, 0, 0, 0, 0),
            /*3*/ intArrayOf(2, 0, 0, 0, 0)
        )
        movementService = MovementServiceImpl(GameBoard(board))

        //when
        val canMakeMove = movementService.canMakeMove(Direction.UP)

        //then
        Assertions.assertTrue(canMakeMove)
    }

    @Test
    fun whenMakeMoveToFreeDirectionDown_ThenReturnTrue() {
        //given
        val board: Array<IntArray> = arrayOf(
            /*0*/ intArrayOf(4, 4, 4),
            /*1*/ intArrayOf(8, 2, 16),
            /*2*/ intArrayOf(2, 4, 4),
            /*3*/ intArrayOf(2, 2, 2)
        )
        movementService = MovementServiceImpl(GameBoard(board))

        //when
        val canMakeMove = movementService.canMakeMove(Direction.DOWN)

        //then
        Assertions.assertTrue(canMakeMove)
    }

    @Test
    fun whenMakeMoveToFreeDirectionLeft_ThenReturnTrue() {
        //given
        val board: Array<IntArray> = arrayOf(
            /*0*/ intArrayOf(2, 4, 2, 0, 0),
            /*1*/ intArrayOf(4, 2, 2, 4, 4),
            /*2*/ intArrayOf(0, 0, 16, 32, 2),
            /*3*/ intArrayOf(2, 4, 0, 0, 2)
        )
        movementService = MovementServiceImpl(GameBoard(board))

        //when
        val canMakeMove = movementService.canMakeMove(Direction.LEFT)

        //then
        Assertions.assertTrue(canMakeMove)
    }

    @Test
    fun whenMakeMoveToFreeDirectionRight_ThenReturnTrue() {
        //given
        val board: Array<IntArray> = arrayOf(
            /*0*/ intArrayOf(0, 0, 0, 0),
            /*1*/ intArrayOf(0, 0, 0, 2),
            /*2*/ intArrayOf(2, 0, 0, 2),
            /*3*/ intArrayOf(0, 0, 4, 2)
        )
        movementService = MovementServiceImpl(GameBoard(board))

        //when
        val canMakeMove = movementService.canMakeMove(Direction.RIGHT)

        //then
        Assertions.assertTrue(canMakeMove)
    }

    @Test
    fun whenMakeMoveToFilledDirectionUp_ThenReturnFalse() {
        //given
        val board: Array<IntArray> = arrayOf(
            /*0*/ intArrayOf(128, 128, 128, 128),
            /*1*/ intArrayOf(64, 64, 64, 64),
            /*2*/ intArrayOf(2, 2, 2, 2),
            /*3*/ intArrayOf(4, 4, 4, 4)
        )
        movementService = MovementServiceImpl(GameBoard(board))

        //when
        val canMakeMove = movementService.canMakeMove(Direction.UP)

        //then
        Assertions.assertFalse(canMakeMove)
    }

    @Test
    fun whenMakeMoveToFilledDirectionDown_ThenReturnFalse() {
        //given
        val board: Array<IntArray> = arrayOf(
            /*0*/ intArrayOf(0, 0, 0, 0),
            /*1*/ intArrayOf(64, 64, 64, 64),
            /*2*/ intArrayOf(2, 2, 2, 2),
            /*3*/ intArrayOf(4, 4, 4, 4)
        )
        movementService = MovementServiceImpl(GameBoard(board))

        //when
        val canMakeMove = movementService.canMakeMove(Direction.DOWN)

        //then
        Assertions.assertFalse(canMakeMove)
    }

    @Test
    fun whenMakeMoveToFilledDirectionLeft_ThenReturnFalse() {
        //given
        val board: Array<IntArray> = arrayOf(
            /*0*/ intArrayOf(16, 2, 4, 8),
            /*1*/ intArrayOf(16, 2, 4, 8),
            /*2*/ intArrayOf(16, 2, 4, 8),
            /*3*/ intArrayOf(16, 2, 4, 8)
        )
        movementService = MovementServiceImpl(GameBoard(board))

        //when
        val canMakeMove = movementService.canMakeMove(Direction.LEFT)

        //then
        Assertions.assertFalse(canMakeMove)
    }

    @Test
    fun whenMakeMoveToFilledDirectionRight_ThenReturnFalse() {
        //given
        val board: Array<IntArray> = arrayOf(
            /*0*/ intArrayOf(0, 2, 4, 8),
            /*1*/ intArrayOf(0, 2, 4, 8),
            /*2*/ intArrayOf(0, 2, 4, 8),
            /*3*/ intArrayOf(0, 2, 4, 8)
        )
        movementService = MovementServiceImpl(GameBoard(board))

        //when
        val canMakeMove = movementService.canMakeMove(Direction.RIGHT)

        //then
        Assertions.assertFalse(canMakeMove)
    }
}
