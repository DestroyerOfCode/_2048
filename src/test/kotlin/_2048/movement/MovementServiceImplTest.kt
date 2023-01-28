package _2048.movement

import _2048.gameboard.Direction
import _2048.gameboard.GameBoard
import _2048.utils.MovementServiceTestUtils.LegalityMovements.Companion.createHorizontalIsMoveLegalArguments
import _2048.utils.MovementServiceTestUtils.LegalityMovements.Companion.createMakeMoveFalseArguments
import _2048.utils.MovementServiceTestUtils.LegalityMovements.Companion.createMakeMoveTrueArguments
import _2048.utils.MovementServiceTestUtils.LegalityMovements.Companion.createVerticalIsMoveLegalArguments
import _2048.utils.MovementServiceTestUtils.ShiftingMovements.Companion.createHorizontalShiftingArguments
import _2048.utils.MovementServiceTestUtils.ShiftingMovements.Companion.createVerticalShiftingArguments
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import kotlin.test.assertContentEquals

class MovementServiceImplTest {

    private lateinit var movementService: MovementService

    private companion object {
        @JvmStatic
        private fun whenShift_ThenMoveWholeBoard(): Stream<Arguments> = Stream.concat(
            createVerticalShiftingArguments(), createHorizontalShiftingArguments()
        )

        @JvmStatic
        private fun whenIsMoveLegal_ThenReturnExpectedBoolean(): Stream<Arguments> = Stream.concat(
            createVerticalIsMoveLegalArguments(), createHorizontalIsMoveLegalArguments()
        )

        @JvmStatic
        private fun whenCanMakeMove_ThenReturnExpectedBoolean(): Stream<Arguments> = Stream.concat(
            createMakeMoveTrueArguments(), createMakeMoveFalseArguments()
        )
    }

    @ParameterizedTest
    @MethodSource
    fun whenShift_ThenMoveWholeBoard(
        givenPlayingArea: Array<IntArray>, outputPlayingArea: Array<IntArray>, direction: Direction
    ) {
        //given
        movementService = MovementServiceImpl(GameBoard(givenPlayingArea))

        // when
        val playingAreaRes: Array<IntArray> = movementService.shift(direction)

        //then
        assertTwoDimensionalArrays(outputPlayingArea, playingAreaRes)
    }

    @ParameterizedTest
    @MethodSource
    fun whenIsMoveLegal_ThenReturnExpectedBoolean(
        playingBoard: Array<IntArray>, direction: Direction, expectedMove: Boolean
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
        playingBoard: Array<IntArray>, expectedMove: Boolean
    ) {
        //given
        movementService = MovementServiceImpl(GameBoard(playingBoard))

        //when
        val canMakeMove = movementService.canMakeMove()

        //then
        Assertions.assertEquals(expectedMove, canMakeMove)
    }

    private fun assertTwoDimensionalArrays(expected: Array<IntArray>, outcome: Array<IntArray>) =
        expected.forEachIndexed { index, arrayRow -> assertContentEquals(arrayRow, outcome[index]) }
}
