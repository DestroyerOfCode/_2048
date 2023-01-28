package _2048.player

import _2048.movement.IllegalMoveException
import _2048.gameboard.Direction
import _2048.gameboard.Direction.UP
import _2048.gameboard.GameBoard
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class PlayerServiceImplTest {

    private lateinit var playerService: PlayerService

    @Test
    fun whenAddNewTileToEmptyBoard_ThenGameBoardSizeIncrementsToOne() {
        //given
        val wantedNumberOfNonZeroTiles = intArrayOf(2, 3)
        val gameBoard = GameBoard()
        playerService = PlayerServiceImpl(gameBoard)

        //when
        playerService.addNewTile()

        //then
        assertTrue(countNonEmptyTiles(gameBoard.playingArea, wantedNumberOfNonZeroTiles), "Number of non-zero tiles should be 1")
    }

    @Test
    fun whenAddNewTileToFilledPlayingArea_ThenGameBoardSizeIncrementsByOne() {
        //given
        val wantedNumberOfNonZeroTiles = intArrayOf(15)
        val playingArea =
            arrayOf(
                intArrayOf(2, 0, 2, 2, 2),
                intArrayOf(0, 0, 2, 2, 0),
                intArrayOf(2, 2, 2, 2, 2),
                intArrayOf(2, 0, 2, 2, 0),
                intArrayOf(0, 0, 0, 0, 0)
            )
        val gameBoard = GameBoard(playingArea)
        playerService = PlayerServiceImpl(gameBoard)

        //when
        playerService.addNewTile()

        //then
        assertTrue(countNonEmptyTiles(gameBoard.playingArea, wantedNumberOfNonZeroTiles), "Number of non-zero tiles should be 15")
    }

    @Test
    fun whenMakeMoveWithValidCharacter_ThenCallShift() {
        //given
        val playerMove = "W"
        playerService = PlayerServiceImpl(GameBoard())

        //when
        val direction: Direction? = playerService.getDirectionOfShift(playerMove)

        //then
        Assertions.assertEquals(UP, direction)
    }

    @Test
    fun whenAddNewTileToFullPlayingArea_ThenGameBoardSizeRemainsSame() {
        //given
        val wantedNumberOfNonZeroTiles = intArrayOf(16)
        val playingArea =
            arrayOf(
                intArrayOf(2, 2, 2, 2, 2),
                intArrayOf(2, 2, 2),
                intArrayOf(2, 2, 2, 2),
                intArrayOf(2, 2, 2),
                intArrayOf(2)
            )
        val gameBoard = GameBoard(playingArea)
        playerService = PlayerServiceImpl(gameBoard)

        //when
        playerService.addNewTile()

        //then
        assertTrue(countNonEmptyTiles(gameBoard.playingArea, wantedNumberOfNonZeroTiles), "Number of non-zero tiles should be 10")
    }

    @Test
    fun whenMakeMoveWithInvalidCharacter_ThenThrowIllegalMoveException() {
        //given
        val playerMove = "b"
        playerService = PlayerServiceImpl(GameBoard())

        //when
        val illegalMoveException: IllegalMoveException =
            Assertions.assertThrows(IllegalMoveException::class.java) { playerService.getDirectionOfShift(playerMove) }

        //then
        Assertions.assertEquals(
            "Invalid move b. Please use one of 'w', 's', 'a', 'd', 'W', 'S', 'A', 'D'", illegalMoveException.message
        )
    }

    private fun countNonEmptyTiles(playingArea: Array<IntArray>, wantedNumberOfNonZeroTiles: IntArray): () -> Boolean {
        var numberOfNonZeroTiles = 0
        return {
            playingArea.forEach { row ->
                row.forEach {
                    if (it in intArrayOf(2, 4)) ++numberOfNonZeroTiles
                }
            }
            wantedNumberOfNonZeroTiles.contains(numberOfNonZeroTiles)
        }
    }
}