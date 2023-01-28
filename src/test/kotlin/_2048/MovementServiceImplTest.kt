package _2048

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertContentEquals

class MovementServiceImplTest {

    private lateinit var movementService: MovementService

    private companion object {
        @JvmStatic
        private fun whenShift_ThenMoveWholeBoard(): List<Arguments> = listOf(
            Arguments.of(
                arrayOf(
                    intArrayOf(0, 0, 0, 0),
                    intArrayOf(0, 0, 0, 0),
                    intArrayOf(0, 0, 0, 0),
                    intArrayOf(0, 0, 2, 0)
                ), arrayOf(
                    intArrayOf(0, 0, 2, 0),
                    intArrayOf(0, 0, 0, 0),
                    intArrayOf(0, 0, 0, 0),
                    intArrayOf(0, 0, 0, 0)
                ), Direction.UP
            ),
            Arguments.of(
                arrayOf(
                    intArrayOf(0, 0, 0, 0),
                    intArrayOf(0, 0, 0, 0),
                    intArrayOf(0, 0, 2, 0),
                    intArrayOf(0, 0, 2, 0)
                ), arrayOf(
                    intArrayOf(0, 0, 4, 0),
                    intArrayOf(0, 0, 0, 0),
                    intArrayOf(0, 0, 0, 0),
                    intArrayOf(0, 0, 0, 0)
                ), Direction.UP
            ),
            Arguments.of(
                arrayOf(
                    intArrayOf(0, 0, 0, 0),
                    intArrayOf(0, 0, 4, 0),
                    intArrayOf(0, 0, 2, 0),
                    intArrayOf(0, 0, 2, 0)
                ), arrayOf(
                    intArrayOf(0, 0, 4, 0),
                    intArrayOf(0, 0, 4, 0),
                    intArrayOf(0, 0, 0, 0),
                    intArrayOf(0, 0, 0, 0)
                ), Direction.UP
            ),
            Arguments.of(
                arrayOf(
                    intArrayOf(0, 0, 0, 0),
                    intArrayOf(0, 0, 4, 0),
                    intArrayOf(0, 0, 2, 0),
                    intArrayOf(2, 0, 2, 0)
                ), arrayOf(
                    intArrayOf(2, 0, 4, 0),
                    intArrayOf(0, 0, 4, 0),
                    intArrayOf(0, 0, 0, 0),
                    intArrayOf(0, 0, 0, 0)
                ), Direction.UP
            ),
            Arguments.of(
                arrayOf(
                    intArrayOf(0, 0, 4, 2),
                    intArrayOf(0, 2, 2, 2),
                    intArrayOf(0, 2, 2, 0),
                    intArrayOf(2, 0, 2, 2)
                ), arrayOf(
                    intArrayOf(2, 4, 4, 4),
                    intArrayOf(0, 0, 4, 2),
                    intArrayOf(0, 0, 2, 0),
                    intArrayOf(0, 0, 0, 0)
                ), Direction.UP
            ),
            Arguments.of(
                arrayOf(
                    intArrayOf(0, 0, 2, 2),
                    intArrayOf(2, 2, 2, 0),
                    intArrayOf(0, 2, 2, 2),
                    intArrayOf(0, 0, 4, 2)
                ), arrayOf(
                    intArrayOf(2, 4, 4, 4),
                    intArrayOf(0, 0, 2, 2),
                    intArrayOf(0, 0, 4, 0),
                    intArrayOf(0, 0, 0, 0)
                ), Direction.UP
            ),
            Arguments.of(
                arrayOf(
                    intArrayOf(8, 0, 2, 128),
                    intArrayOf(8, 32, 4, 64),
                    intArrayOf(4, 2, 4, 2),
                    intArrayOf(0, 32, 8, 2)
                ), arrayOf(
                    intArrayOf(16, 32, 2, 128),
                    intArrayOf(4, 2, 8, 64),
                    intArrayOf(0, 32, 8, 4),
                    intArrayOf(0, 0, 0, 0)
                ), Direction.UP
            ),
            Arguments.of(
                arrayOf(
                    intArrayOf(2, 4, 2, 2),
                    intArrayOf(2, 2, 2, 0),
                    intArrayOf(4, 2, 0, 4),
                    intArrayOf(2, 4, 2, 2)
                ), arrayOf(
                    intArrayOf(4, 4, 4, 2),
                    intArrayOf(4, 4, 2, 4),
                    intArrayOf(2, 4, 0, 2),
                    intArrayOf(0, 0, 0, 0)
                ), Direction.UP
            ),
            Arguments.of(
                arrayOf(
                    intArrayOf(2, 2, 2, 2),
                    intArrayOf(4, 4, 4, 4),
                    intArrayOf(2, 0, 0, 0),
                    intArrayOf(2, 0, 0, 0)
                ), arrayOf(
                    intArrayOf(2, 2, 2, 2),
                    intArrayOf(4, 4, 4, 4),
                    intArrayOf(4, 0, 0, 0),
                    intArrayOf(0, 0, 0, 0)
                ), Direction.UP
            ),
            Arguments.of(
                arrayOf(
                    intArrayOf(0, 0, 4, 2),
                    intArrayOf(2, 2, 2, 2),
                    intArrayOf(0, 2, 2, 0),
                    intArrayOf(0, 0, 2, 2)
                ), arrayOf(
                    intArrayOf(0, 0, 0, 0),
                    intArrayOf(0, 0, 4, 0),
                    intArrayOf(0, 0, 2, 2),
                    intArrayOf(2, 4, 4, 4)
                ), Direction.DOWN
            ),
            Arguments.of(
                arrayOf(
                    intArrayOf(8, 0, 2, 128),
                    intArrayOf(8, 32, 4, 64),
                    intArrayOf(4, 2, 4, 2),
                    intArrayOf(0, 32, 8, 2)
                ), arrayOf(
                    intArrayOf(0, 0, 0, 0),
                    intArrayOf(0, 32, 2, 128),
                    intArrayOf(16, 2, 8, 64),
                    intArrayOf(4, 32, 8, 4)
                ), Direction.DOWN
            ),
            Arguments.of(
                arrayOf(
                    intArrayOf(0, 32, 8, 2),
                    intArrayOf(4, 2, 4, 2),
                    intArrayOf(8, 32, 4, 64),
                    intArrayOf(8, 0, 2, 128)
                ), arrayOf(
                    intArrayOf(0, 0, 0, 0),
                    intArrayOf(0, 32, 8, 4),
                    intArrayOf(4, 2, 8, 64),
                    intArrayOf(16, 32, 2, 128)
                ), Direction.DOWN
            ),
            Arguments.of(
                arrayOf(
                    intArrayOf(8, 8, 4, 0),
                    intArrayOf(0, 32, 2, 32),
                    intArrayOf(2, 4, 4, 8),
                    intArrayOf(128, 64, 2, 2)
                ), arrayOf(
                    intArrayOf(16, 4, 0, 0),
                    intArrayOf(32, 2, 32, 0),
                    intArrayOf(2, 8, 8, 0),
                    intArrayOf(128, 64, 4, 0)
                ), Direction.LEFT
            ),
            Arguments.of(
                arrayOf(
                    intArrayOf(2, 2, 4, 2),
                    intArrayOf(4, 2, 2, 4),
                    intArrayOf(2, 2, 0, 2),
                    intArrayOf(2, 0, 4, 2)
                ), arrayOf(
                    intArrayOf(4, 4, 2, 0),
                    intArrayOf(4, 4, 4, 0),
                    intArrayOf(4, 2, 0, 0),
                    intArrayOf(2, 4, 2, 0)
                ), Direction.LEFT
            ),
            Arguments.of(
                arrayOf(
                    intArrayOf(2, 2, 4, 8),
                    intArrayOf(8, 16, 32, 16),
                    intArrayOf(16, 4, 8, 4),
                    intArrayOf(4, 2, 4, 8)
                ), arrayOf(
                    intArrayOf(4, 4, 8, 0),
                    intArrayOf(8, 16, 32, 16),
                    intArrayOf(16, 4, 8, 4),
                    intArrayOf(4, 2, 4, 8)
                ), Direction.LEFT
            ),
            Arguments.of(
                arrayOf(
                    intArrayOf(0, 4, 8, 8),
                    intArrayOf(32, 2, 32, 0),
                    intArrayOf(8, 4, 4, 2),
                    intArrayOf(2, 2, 64, 128)
                ), arrayOf(
                    intArrayOf(0, 0, 4, 16),
                    intArrayOf(0, 32, 2, 32),
                    intArrayOf(0, 8, 8, 2),
                    intArrayOf(0, 4, 64, 128)
                ), Direction.RIGHT
            ),
            Arguments.of(
                arrayOf(
                    intArrayOf(2, 4, 2, 2),
                    intArrayOf(4, 2, 2, 4),
                    intArrayOf(2, 0, 2, 2),
                    intArrayOf(2, 4, 0, 2)
                ), arrayOf(
                    intArrayOf(0, 2, 4, 4),
                    intArrayOf(0, 4, 4, 4),
                    intArrayOf(0, 0, 2, 4),
                    intArrayOf(0, 2, 4, 2)
                ), Direction.RIGHT
            ),
            Arguments.of(
                arrayOf(
                    intArrayOf(2, 2, 2, 2),
                    intArrayOf(4, 4, 4, 4),
                    intArrayOf(2, 0, 2, 2),
                    intArrayOf(2, 4, 0, 2)
                ), arrayOf(
                    intArrayOf(0, 0, 4, 4),
                    intArrayOf(0, 0, 8, 8),
                    intArrayOf(0, 0, 2, 4),
                    intArrayOf(0, 2, 4, 2)
                ), Direction.RIGHT
            ),
            Arguments.of(
                arrayOf(
                    intArrayOf(4, 2, 2, 2),
                    intArrayOf(2, 2, 4, 4),
                    intArrayOf(0, 2, 2, 8),
                    intArrayOf(4, 0, 2, 0)
                ), arrayOf(
                    intArrayOf(0, 4, 2, 4),
                    intArrayOf(0, 0, 4, 8),
                    intArrayOf(0, 0, 4, 8),
                    intArrayOf(0, 0, 4, 2)
                ), Direction.RIGHT
            ),
        )

        @JvmStatic
        private fun whenIsMoveLegal_ThenReturnExpectedBoolean(): List<Arguments> = listOf(
            Arguments.of(
                arrayOf(
                    intArrayOf(2, 2, 2, 2),
                    intArrayOf(4, 4, 4, 4),
                    intArrayOf(2, 0, 0, 0),
                    intArrayOf(2, 0, 0, 0)
                ),
                Direction.UP,
                true
            ),
            Arguments.of(
                arrayOf(
                    intArrayOf(4, 4, 4, 0),
                    intArrayOf(8, 2, 16, 0),
                    intArrayOf(2, 4, 4, 0),
                    intArrayOf(2, 2, 2, 0)
                ),
                Direction.DOWN,
                true
            ),
            Arguments.of(
                arrayOf(
                    intArrayOf(4, 2, 0, 0),
                    intArrayOf(2, 2, 4, 4),
                    intArrayOf(0, 16, 32, 2),
                    intArrayOf(4, 0, 0, 2)
                ),
                Direction.LEFT,
                true
            ),
            Arguments.of(
                arrayOf(
                    intArrayOf(0, 0, 0, 0),
                    intArrayOf(0, 0, 0, 2),
                    intArrayOf(2, 0, 0, 2),
                    intArrayOf(0, 0, 4, 2)
                ),
                Direction.RIGHT,
                true
            ),
            Arguments.of(
                arrayOf(
                    intArrayOf(128, 128, 128, 128),
                    intArrayOf(64, 64, 64, 64),
                    intArrayOf(2, 2, 2, 2),
                    intArrayOf(4, 4, 4, 4)
                ),
                Direction.UP,
                false
            ),
            Arguments.of(
                arrayOf(
                    intArrayOf(0, 0, 0, 0),
                    intArrayOf(64, 64, 64, 64),
                    intArrayOf(2, 2, 2, 2),
                    intArrayOf(4, 4, 4, 4)
                ),
                Direction.DOWN,
                false
            ),
            Arguments.of(
                arrayOf(
                    intArrayOf(16, 2, 4, 8),
                    intArrayOf(16, 2, 4, 8),
                    intArrayOf(16, 2, 4, 8),
                    intArrayOf(16, 2, 4, 8)
                ),
                Direction.LEFT,
                false
            ),
            Arguments.of(
                arrayOf(
                    intArrayOf(2, 4, 2, 0),
                    intArrayOf(2, 4, 2, 0),
                    intArrayOf(4, 0, 0, 0),
                    intArrayOf(8, 2, 0, 0)
                ),
                Direction.LEFT,
                false
            ),
            Arguments.of(
                arrayOf(
                    intArrayOf(0, 2, 4, 8),
                    intArrayOf(0, 2, 4, 8),
                    intArrayOf(0, 2, 4, 8),
                    intArrayOf(0, 2, 4, 8)
                ),
                Direction.RIGHT,
                false
            ),
        )

        @JvmStatic
        private fun whenCanMakeMove_ThenReturnExpectedBoolean(): List<Arguments> = listOf(
            Arguments.of(
                arrayOf(
                    intArrayOf(2, 2, 4, 8),
                    intArrayOf(8, 16, 32, 16),
                    intArrayOf(16, 4, 8, 4),
                    intArrayOf(4, 2, 4, 8)
                ),
                true
            ),
            Arguments.of(
                arrayOf(
                    intArrayOf(16, 2, 4, 8),
                    intArrayOf(8, 16, 32, 16),
                    intArrayOf(16, 4, 8, 4),
                    intArrayOf(4, 2, 4, 8)
                ),
                false
            ),
            Arguments.of(
                arrayOf(
                    intArrayOf(2, 4, 8, 0),
                    intArrayOf(4, 2, 4, 2),
                    intArrayOf(8, 4, 16, 4),
                    intArrayOf(2, 8, 2, 16)
                ),
                true
            ),
        )
    }

    @ParameterizedTest
    @MethodSource
    fun whenShift_ThenMoveWholeBoard(
        givenPlayingArea: Array<IntArray>,
        outputPlayingArea: Array<IntArray>,
        direction: Direction
    ) {
        //given
        movementService = MovementServiceImpl(GameBoard(givenPlayingArea))

        // when
        val playingAreaRes: Array<IntArray> = movementService.shift(direction)

        //then
        assertTwoDimensionalArrays(outputPlayingArea, playingAreaRes)
    }

    private fun assertTwoDimensionalArrays(expected: Array<IntArray>, outcome: Array<IntArray>) =
        expected.forEachIndexed { index, arrayRow -> assertContentEquals(arrayRow, outcome[index]) }

    @ParameterizedTest
    @MethodSource
    fun whenIsMoveLegal_ThenReturnExpectedBoolean(
        playingBoard: Array<IntArray>,
        direction: Direction,
        expectedMove: Boolean
    ) {
        //given
        movementService = MovementServiceImpl(GameBoard(playingBoard))

        //when
        val canMakeMove = movementService.isMoveLegal(direction)

        //then
        Assertions.assertEquals(expectedMove, canMakeMove)
    }

    @ParameterizedTest
    @MethodSource
    fun whenCanMakeMove_ThenReturnExpectedBoolean(
        playingBoard: Array<IntArray>,
        expectedMove: Boolean
    ) {
        //given
        movementService = MovementServiceImpl(GameBoard(playingBoard))

        //when
        val canMakeMove = movementService.canMakeMove()

        //then
        Assertions.assertEquals(expectedMove, canMakeMove)
    }

}
