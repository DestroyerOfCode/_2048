package _2048

import _2048.Direction.UP
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.doCallRealMethod
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.never
import org.mockito.BDDMockito.times
import org.mockito.BDDMockito.verify
import org.mockito.Mockito
import org.mockito.Mockito.doReturn
import java.io.ByteArrayInputStream
import kotlin.test.assertEquals

class GameBoardServiceImplTest {

    private lateinit var gameBoardService: GameBoardServiceImpl
    private var playerService: PlayerService = Mockito.mock(PlayerServiceImpl::class.java)
    private var movementService: MovementService = Mockito.mock(MovementServiceImpl::class.java)
    private lateinit var testIn: ByteArrayInputStream

    @Test
    fun whenPlayGameAndMakeIllegalMove_ThenThrowsException() {
        //given
        gameBoardService = GameBoardServiceImpl(GameBoard(), playerService, movementService)
        given(movementService.canMakeMove()).willReturn(true, false)
        doCallRealMethod().`when`(playerService).getDirectionOfShift("q")
        provideInput("q")

        //when
        gameBoardService.playGame()

        //then
        verify(playerService, never()).addNewTile()
        Direction.values().forEach { direction: Direction ->
            verify(movementService, never()).shift(direction)
        }

    }

    @Test
    fun whenPlayGameAndMakeInvalidMove_ThenContinueGameLoop() {
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
//        verify(movementService, never()).shift(any(Direction::class.java))
        Direction.values().forEach { direction: Direction ->
            verify(movementService, never()).shift(direction)
        }

    }

    @Test
    fun whenPlayGameAndMakeValidMove_ThenCallShiftAndAddNewTile() {
        //given
        gameBoardService = GameBoardServiceImpl(GameBoard(), playerService, movementService)
        given(playerService.getDirectionOfShift("w")).willReturn(UP)
        given(movementService.canMakeMove()).willReturn(true, false)
        doReturn(true).`when`(movementService).isMoveLegal(UP)
        doReturn(arrayOf(intArrayOf())).`when`(movementService).shift(UP)
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
        val ex: IllegalPlayingBoardSizeException = Assertions.assertThrows(IllegalPlayingBoardSizeException::class.java)
        { GameBoard(arrayOf(intArrayOf(1), intArrayOf())) }

        //then
        assertEquals("You cannot pick an empty Row or Column!", ex.message)
    }

    @Test
    fun whenInitWithZeroHeight_ThenThrowException() {
        //given and when
        val ex: IllegalPlayingBoardSizeException = Assertions.assertThrows(IllegalPlayingBoardSizeException::class.java)
        { GameBoard(arrayOf()) }

        //then
        assertEquals("You cannot pick an empty Row or Column!", ex.message)
    }

    @Test
    fun whenInitWithUnevenSizes_ThenThrowException() {
        //given and when
        val ex: IllegalPlayingBoardSizeException = Assertions.assertThrows(IllegalPlayingBoardSizeException::class.java)
        { GameBoard(arrayOf(intArrayOf(2, 2, 2, 2))) }

        //then
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
}