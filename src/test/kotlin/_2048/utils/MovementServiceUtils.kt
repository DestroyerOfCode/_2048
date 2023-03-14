package _2048.utils

import _2048.gameboard.Direction.*
import org.junit.jupiter.params.provider.Arguments
import java.util.stream.Stream

open class MovementServiceTestUtils {
    class ShiftingMovements : MovementServiceTestUtils() {
        companion object {
            fun createVerticalShiftingArguments(): Stream<Arguments> = Stream.concat(
                createShiftUpArguments(),
                createShiftDownArguments(),
            )

            fun createHorizontalShiftingArguments(): Stream<Arguments> = Stream.concat(
                createShiftLeftArguments(),
                createShiftRightArguments()
            )

            private fun createShiftUpArguments(): Stream<Arguments> = Stream.of(
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
                    ), UP
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
                    ), UP
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
                    ), UP
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
                    ), UP
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
                    ), UP
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
                    ), UP
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
                    ), UP
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
                    ), UP
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
                    ), UP
                )
            )

            private fun createShiftDownArguments(): Stream<Arguments> = Stream.of(
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
                    ), DOWN
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
                    ), DOWN
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
                    ), DOWN
                ),
            )

            private fun createShiftLeftArguments(): Stream<Arguments> = Stream.of(
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
                    ), LEFT
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
                    ), LEFT
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
                    ), LEFT
                )
            )

            private fun createShiftRightArguments(): Stream<Arguments> = Stream.of(
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
                    ), RIGHT
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
                    ), RIGHT
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
                    ), RIGHT
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
                    ), RIGHT
                ),
            )
        }
    }

    class LegalityMovements : MovementServiceTestUtils() {
        companion object {
            fun createMakeMoveTrueArguments(): Stream<Arguments> = Stream.of(
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
                        intArrayOf(2, 4, 8, 0),
                        intArrayOf(4, 2, 4, 2),
                        intArrayOf(8, 4, 16, 4),
                        intArrayOf(2, 8, 2, 16)
                    ),
                    true
                ),
                Arguments.of(
                    arrayOf(
                        intArrayOf(2, 0, 0, 0),
                        intArrayOf(0, 0, 0, 0),
                        intArrayOf(0, 0, 0, 0),
                        intArrayOf(0, 0, 0, 0)
                    ),
                    true
                )

            )

            fun createMakeMoveFalseArguments(): Stream<Arguments> = Stream.of(
                Arguments.of(
                    arrayOf(
                        intArrayOf(16, 2, 4, 8),
                        intArrayOf(8, 16, 32, 16),
                        intArrayOf(16, 4, 8, 4),
                        intArrayOf(4, 2, 4, 8)
                    ),
                    false
                )
            )

            fun createHorizontalIsMoveLegalArguments(): Stream<Arguments> = Stream.concat(
                createIsMoveLegalLeftArguments(),
                createIsMoveLegalRightArguments()
            )

            fun createVerticalIsMoveLegalArguments(): Stream<Arguments> = Stream.concat(
                createIsMoveLegalUpArguments(),
                createIsMoveLegalDownArguments(),
            )

            private fun createIsMoveLegalUpArguments(): Stream<Arguments> = Stream.of(
                Arguments.of(
                    arrayOf(
                        intArrayOf(128, 128, 128, 128),
                        intArrayOf(64, 64, 64, 64),
                        intArrayOf(2, 2, 2, 2),
                        intArrayOf(4, 4, 4, 4)
                    ),
                    UP,
                    false
                ),
                Arguments.of(
                    arrayOf(
                        intArrayOf(2, 2, 2, 2),
                        intArrayOf(4, 4, 4, 4),
                        intArrayOf(2, 0, 0, 0),
                        intArrayOf(2, 0, 0, 0)
                    ),
                    UP,
                    true
                )
            )

            private fun createIsMoveLegalDownArguments(): Stream<Arguments> = Stream.of(
                Arguments.of(
                    arrayOf(
                        intArrayOf(0, 0, 0, 0),
                        intArrayOf(64, 64, 64, 64),
                        intArrayOf(2, 2, 2, 2),
                        intArrayOf(4, 4, 4, 4)
                    ),
                    DOWN,
                    false
                ),
                Arguments.of(
                    arrayOf(
                        intArrayOf(4, 4, 4, 0),
                        intArrayOf(8, 2, 16, 0),
                        intArrayOf(2, 4, 4, 0),
                        intArrayOf(2, 2, 2, 0)
                    ),
                    DOWN,
                    true
                ),
            )

            private fun createIsMoveLegalLeftArguments(): Stream<Arguments> = Stream.of(
                Arguments.of(
                    arrayOf(
                        intArrayOf(4, 2, 0, 0),
                        intArrayOf(2, 2, 4, 4),
                        intArrayOf(0, 16, 32, 2),
                        intArrayOf(4, 0, 0, 2)
                    ),
                    LEFT,
                    true
                ),
                Arguments.of(
                    arrayOf(
                        intArrayOf(16, 2, 4, 8),
                        intArrayOf(16, 2, 4, 8),
                        intArrayOf(16, 2, 4, 8),
                        intArrayOf(16, 2, 4, 8)
                    ),
                    LEFT,
                    false
                ),
                Arguments.of(
                    arrayOf(
                        intArrayOf(0, 0, 0, 0),
                        intArrayOf(0, 0, 0, 0),
                        intArrayOf(2, 0, 0, 0),
                        intArrayOf(0, 0, 0, 0)
                    ),
                    LEFT,
                    false
                ),
                Arguments.of(
                    arrayOf(
                        intArrayOf(0, 0, 0, 2),
                        intArrayOf(0, 0, 0, 0),
                        intArrayOf(0, 0, 0, 0),
                        intArrayOf(0, 0, 0, 0)
                    ),
                    LEFT,
                    true
                ),
                Arguments.of(
                    arrayOf(
                        intArrayOf(0, 0, 0, 0),
                        intArrayOf(0, 0, 0, 0),
                        intArrayOf(0, 0, 0, 0),
                        intArrayOf(0, 0, 0, 2)
                    ),
                    LEFT,
                    true
                ),
                Arguments.of(
                    arrayOf(
                        intArrayOf(0, 0, 0, 0),
                        intArrayOf(0, 0, 0, 0),
                        intArrayOf(0, 0, 0, 0),
                        intArrayOf(0, 2, 0, 0)
                    ),
                    LEFT,
                    true
                ),
                Arguments.of(
                    arrayOf(
                        intArrayOf(2, 0, 0, 0),
                        intArrayOf(0, 0, 0, 0),
                        intArrayOf(0, 0, 0, 0),
                        intArrayOf(0, 0, 0, 0)
                    ),
                    LEFT,
                    false
                ),
                Arguments.of(
                    arrayOf(
                        intArrayOf(0, 0, 0, 0),
                        intArrayOf(0, 0, 0, 0),
                        intArrayOf(0, 0, 0, 0),
                        intArrayOf(2, 0, 0, 0)
                    ),
                    LEFT,
                    false
                ),
                Arguments.of(
                    arrayOf(
                        intArrayOf(0, 0, 0, 0),
                        intArrayOf(0, 0, 0, 0),
                        intArrayOf(0, 0, 0, 2),
                        intArrayOf(2, 2, 2, 8)
                    ),
                    LEFT,
                    true
                ),
                Arguments.of(
                    arrayOf(
                        intArrayOf(2, 4, 8, 32),
                        intArrayOf(2, 4, 8, 32),
                        intArrayOf(2, 4, 8, 32),
                        intArrayOf(2, 2, 4, 16)
                    ),
                    LEFT,
                    true
                ),
                Arguments.of(
                    arrayOf(
                        intArrayOf(2, 4, 8, 32),
                        intArrayOf(2, 4, 8, 32),
                        intArrayOf(2, 4, 8, 32),
                        intArrayOf(2, 4, 4, 16)
                    ),
                    LEFT,
                    true
                ),
                Arguments.of(
                    arrayOf(
                        intArrayOf(2, 4, 8, 32),
                        intArrayOf(2, 4, 8, 32),
                        intArrayOf(2, 4, 8, 32),
                        intArrayOf(2, 8, 4, 16)
                    ),
                    LEFT,
                    false
                ),
                Arguments.of(
                    arrayOf(
                        intArrayOf(2, 4, 2, 0),
                        intArrayOf(2, 4, 2, 0),
                        intArrayOf(4, 0, 0, 0),
                        intArrayOf(8, 2, 0, 0)
                    ),
                    LEFT,
                    false
                ),
            )

            private fun createIsMoveLegalRightArguments(): Stream<Arguments> = Stream.of(
                Arguments.of(
                    arrayOf(
                        intArrayOf(0, 0, 0, 0),
                        intArrayOf(0, 0, 0, 2),
                        intArrayOf(2, 0, 0, 2),
                        intArrayOf(0, 0, 4, 2)
                    ),
                    RIGHT,
                    true
                ),
                Arguments.of(
                    arrayOf(
                        intArrayOf(0, 2, 4, 8),
                        intArrayOf(0, 2, 4, 8),
                        intArrayOf(0, 2, 4, 8),
                        intArrayOf(0, 2, 4, 8)
                    ),
                    RIGHT,
                    false
                ),
                Arguments.of(
                    arrayOf(
                        intArrayOf(0, 2, 4, 8),
                        intArrayOf(0, 2, 4, 8),
                        intArrayOf(0, 2, 4, 8),
                        intArrayOf(2, 2, 4, 8)
                    ),
                    RIGHT,
                    true
                ),
            Arguments.of(
                    arrayOf(
                        intArrayOf(0, 2, 2, 8),
                        intArrayOf(0, 2, 4, 8),
                        intArrayOf(0, 2, 4, 8),
                        intArrayOf(0, 2, 4, 8)
                    ),
                    RIGHT,
                    true
                ),
            Arguments.of(
                    arrayOf(
                        intArrayOf(0, 2, 0, 2),
                        intArrayOf(0, 2, 4, 2),
                        intArrayOf(0, 2, 4, 8),
                        intArrayOf(0, 2, 4, 8)
                    ),
                    RIGHT,
                    true
                ),
            )
        }
    }
}