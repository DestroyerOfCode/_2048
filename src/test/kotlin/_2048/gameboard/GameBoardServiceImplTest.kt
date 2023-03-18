package _2048.gameboard

import _2048.gameboard.Direction.UP
import _2048.movement.MovementService
import _2048.movement.MovementServiceImpl
import _2048.player.PlayerService
import _2048.player.PlayerServiceImpl
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.*
import org.mockito.Mockito
import org.mockito.Mockito.doReturn
import java.io.ByteArrayInputStream
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GameBoardServiceImplTest {

    private lateinit var gameBoardService: GameBoardServiceImpl
    private lateinit var testIn: ByteArrayInputStream
    private var movementService: MovementService = Mockito.mock(MovementServiceImpl::class.java)
    private var playerService: PlayerService = Mockito.mock(PlayerServiceImpl::class.java)

    @Test
    suspend fun whenPlayGameAndMakeIllegalMove_ThenThrowsException() {
        //given
        gameBoardService = GameBoardServiceImpl(GameBoard(), playerService, movementService)
        given(movementService.canMakeMove()).willReturn(true, false)
        doCallRealMethod().`when`(playerService).getDirectionOfShift("q")
        provideInput("q")

        //when
        gameBoardService.playGame()

        //then
        verify(playerService, never()).addNewTile()
        isShiftInAnyDirectionEverCalled()

    }

    @Test
    suspend fun whenPlayGameAndMakeInvalidMove_ThenContinueGameLoop() {
        //given
        val playingBoard = arrayOf(
            intArrayOf(2, 8, 0, 0),
            intArrayOf(2, 8, 2, 0),
            intArrayOf(2, 8, 4, 2),
            intArrayOf(2, 4, 0, 0),
        )
        gameBoardService = GameBoardServiceImpl(GameBoard(playingBoard), playerService, movementService)
        given(movementService.canMakeMove()).willReturn(true, false)
        doCallRealMethod().`when`(playerService).getDirectionOfShift("a")
        provideInput("a")

        //when
        gameBoardService.playGame()

        //then
        verify(playerService, never()).addNewTile()
        isShiftInAnyDirectionEverCalled()

    }

    @Test
    suspend fun whenPlayGameAndMakeValidMove_ThenCallShiftAndAddNewTile() {
        //given
        gameBoardService = GameBoardServiceImpl(GameBoard(), playerService, movementService)
        given(playerService.getDirectionOfShift("w")).willReturn(UP)
        given(movementService.canMakeMove()).willReturn(true, false)
        doReturn(true).`when`(movementService).isMoveLegal(UP)
        provideInput("w")

        //when
        gameBoardService.playGame()

        //then
        verify(playerService, times(1)).getDirectionOfShift("w")
        verify(movementService, times(1)).shift(UP)
        verify(playerService, times(1)).addNewTile()
    }

    @Test
    fun whenInitWithZeroWidth_ThenThrowException() {
        //given and when
        val ex = Assertions.assertThrows(IllegalPlayingBoardSizeException::class.java)
        { GameBoard(arrayOf(intArrayOf(1), intArrayOf())) }

        //then
        assertTrue(
            ex is IllegalPlayingBoardSizeException,
            "Exception should be of type IllegalPlayingBoardSizeException!"
        )
        assertEquals("You cannot pick an empty Row or Column!", ex.message)
    }

    @Test
    fun whenInitWithZeroHeight_ThenThrowException() {
        //given and when
        val ex = Assertions.assertThrows(IllegalPlayingBoardSizeException::class.java)
        { GameBoard(arrayOf()) }

        //then
        assertTrue(
            ex is IllegalPlayingBoardSizeException,
            "Exception should be of type IllegalPlayingBoardSizeException!"
        )
        assertEquals("You cannot pick an empty Row or Column!", ex.message)
    }

    @Test
    fun whenInitWithUnevenSizes_ThenThrowException() {
        //given and when
        val ex = Assertions.assertThrows(IllegalPlayingBoardSizeException::class.java)
        { GameBoard(arrayOf(intArrayOf(2, 2, 2, 2))) }

        //then
        assertTrue(
            ex is IllegalPlayingBoardSizeException,
            "Exception should be of type IllegalPlayingBoardSizeException!"
        )

        assertEquals("Height and Width of Playing Board must be indifferent!", ex.message)
    }

    @AfterEach
    fun tearDown() {
        System.setIn(System.`in`)
    }

    private fun provideInput(input: String) {
        testIn = ByteArrayInputStream(input.toByteArray())
        System.setIn(testIn)
    }

    private fun isShiftInAnyDirectionEverCalled() {
        Direction.values().forEach { direction: Direction ->
            verify(movementService, never()).shift(direction)
        }
    }
}