package _2048

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.Mockito

class PlayerServiceImplTest {

    private lateinit var playerService: PlayerService

    private var gameBoardService: GameBoardService = Mockito.mock(GameBoardService::class.java)

    @Test
    fun whenAddNewTileToEmptyBoard_ThenGameBoardSizeIncrementsToOne() {
        //given
        val wantedNumberOfNonZeroTiles = 1
        playerService = PlayerServiceImpl(GameBoard(), gameBoardService)

        //when
        val boardRes: GameBoard = playerService.addNewTile()

        //then
        assertTrue(countNonEmptyTiles(boardRes, wantedNumberOfNonZeroTiles), "Number of non-zero tiles should be 1")
    }

    @Test
    fun whenAddNewTileToFilledPlayingArea_ThenGameBoardSizeIncrementsByOne() {
        //given
        val wantedNumberOfNonZeroTiles = 13
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
            ),
            gameBoardService
        )

        //when
        val boardRes: GameBoard = playerService.addNewTile()

        //then
        assertTrue(countNonEmptyTiles(boardRes, wantedNumberOfNonZeroTiles), "Number of non-zero tiles should be 10")

    }

    @Test
    fun whenAddNewTileToFullPlayingArea_ThenGameBoardSizeRemainsSame() {
        //given
        val wantedNumberOfNonZeroTiles = 16
        playerService = PlayerServiceImpl(
            GameBoard(
                arrayOf(
                    intArrayOf(2, 2, 2, 2, 2),
                    intArrayOf(2, 2, 2),
                    intArrayOf(2, 2, 2, 2),
                    intArrayOf(2, 2, 2),
                    intArrayOf(2)
                )
            ),
            gameBoardService
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
        playerService = PlayerServiceImpl(GameBoard(), gameBoardService)
        Mockito.doReturn(arrayOf(intArrayOf())).`when`(gameBoardService).shift(Direction.UP)

        //when
        playerService.makeMove(playerMove)

        //then
        Mockito.verify(gameBoardService, Mockito.times(1)).shift(Direction.UP)
    }

    @Test
    fun whenMakeMoveWithInvalidCharacter_ThenThrowIllegalMoveException() {
        //given
        val playerMove = "b"
        playerService = PlayerServiceImpl(GameBoard(), gameBoardService)

        //when
        val illegalMoveException: IllegalMoveException =
            Assertions.assertThrows(IllegalMoveException::class.java) { playerService.makeMove(playerMove) }

        //then
        Assertions.assertEquals(
            "Invalid move $playerMove. Please use one of 'w', 's', 'a', 'd', 'W', 'S', 'A', 'D'",
            illegalMoveException.message
        )
    }

    private fun countNonEmptyTiles(boardRes: GameBoard, wantedNumberOfNonZeroTiles: Int): () -> Boolean {
        var numberOfNonZeroTiles = 0
        return {
            boardRes.playingArea.forEach { row ->
                row.forEach {
                    if (it in intArrayOf(2, 4)) ++numberOfNonZeroTiles
                }
            }
            numberOfNonZeroTiles == wantedNumberOfNonZeroTiles
        }
    }
}