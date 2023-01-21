package _2048

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class PlayerServiceImplTest {

    private lateinit var playerService: PlayerService

    @Test
    fun whenAddNewTileToEmptyBoard_ThenGameBoardSizeIncrementsToOne() {
        //given
        val wantedNumberOfNonZeroTiles = intArrayOf(2, 3)
        playerService = PlayerServiceImpl(GameBoard())

        //when
        val boardRes: GameBoard = playerService.addNewTile()

        //then
        assertTrue(countNonEmptyTiles(boardRes, wantedNumberOfNonZeroTiles), "Number of non-zero tiles should be 1")
    }

    @Test
    fun whenAddNewTileToFilledPlayingArea_ThenGameBoardSizeIncrementsByOne() {
        //given
        val wantedNumberOfNonZeroTiles = intArrayOf(13)
        playerService = PlayerServiceImpl(
            GameBoard(
                arrayOf(
                    intArrayOf(2, 0, 2, 2, 2),
                    intArrayOf(0, 0, 2, 2, 0),
                    intArrayOf(2, 2, 2),
                    intArrayOf(2, 0, 2, 2),
                    intArrayOf(0, 0, 0, 0),
                    intArrayOf(0)
                )
            )
        )

        //when
        val boardRes: GameBoard = playerService.addNewTile()

        //then
        assertTrue(countNonEmptyTiles(boardRes, wantedNumberOfNonZeroTiles), "Number of non-zero tiles should be 10")

    }

    @Test
    fun whenMakeMoveWithValidCharacter_ThenCallShift() {
        //given
        val playerMove = "W"
        playerService = PlayerServiceImpl(GameBoard())

        //when
        val direction: Direction? = playerService.makeMove(playerMove)

        //then
        Assertions.assertEquals(Direction.UP, direction)
    }

    @Test
    fun whenAddNewTileToFullPlayingArea_ThenGameBoardSizeRemainsSame() {
        //given
        val wantedNumberOfNonZeroTiles = intArrayOf(16)
        playerService = PlayerServiceImpl(
            GameBoard(
                arrayOf(
                    intArrayOf(2, 2, 2, 2, 2),
                    intArrayOf(2, 2, 2),
                    intArrayOf(2, 2, 2, 2),
                    intArrayOf(2, 2, 2),
                    intArrayOf(2)
                )
            )
        )

        //when
        val boardRes: GameBoard = playerService.addNewTile()

        //then
        assertTrue(countNonEmptyTiles(boardRes, wantedNumberOfNonZeroTiles), "Number of non-zero tiles should be 10")


    }

    @Test
    fun whenMakeMoveWithInvalidCharacter_ThenThrowIllegalMoveException() {
        //given
        val playerMove = "b"
        playerService = PlayerServiceImpl(GameBoard())

        //when
        val illegalMoveException: IllegalMoveException =
            Assertions.assertThrows(IllegalMoveException::class.java) { playerService.makeMove(playerMove) }

        //then
        Assertions.assertEquals(
            "Invalid move $playerMove. Please use one of 'w', 's', 'a', 'd', 'W', 'S', 'A', 'D'",
            illegalMoveException.message
        )
    }

    private fun countNonEmptyTiles(boardRes: GameBoard, wantedNumberOfNonZeroTiles: IntArray): () -> Boolean {
        var numberOfNonZeroTiles = 0
        return {
            boardRes.playingArea.forEach { row ->
                row.forEach {
                    if (it in intArrayOf(2, 4)) ++numberOfNonZeroTiles
                }
            }
            wantedNumberOfNonZeroTiles.contains(numberOfNonZeroTiles)
        }
    }
}