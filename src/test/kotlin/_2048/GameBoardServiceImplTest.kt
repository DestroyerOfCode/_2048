package _2048

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.BDDMockito
import java.io.ByteArrayInputStream

class GameBoardServiceImplTest {

    private lateinit var gameBoardService: GameBoardServiceImpl
    private var playerService: PlayerService = Mockito.mock(PlayerServiceImpl::class.java)
    private var movementService: MovementService = Mockito.mock(MovementServiceImpl::class.java)
    private lateinit var testIn: ByteArrayInputStream

    @Test
    fun whenPlayGameAndMakeIllegalMove_ThenThrowsException() {
        //given
        gameBoardService = GameBoardServiceImpl(GameBoard(), playerService, movementService)
        BDDMockito.given(movementService.canMakeMove()).willReturn(true, false)
        BDDMockito.doCallRealMethod().`when`(playerService).makeMove("q")
        provideInput("q")

        //when
        gameBoardService.playGame()

        //then
        BDDMockito.verify(playerService, BDDMockito.never()).addNewTile()
        Direction.values().forEach { direction: Direction ->
            BDDMockito.verify(movementService, BDDMockito.never()).shift(direction)
        }

    }

    @Test
    fun whenPlayGameAndMakeValidMove_ThenCallShiftAndAddNewTile() {
        //given
        gameBoardService = GameBoardServiceImpl(GameBoard(), playerService, movementService)
        BDDMockito.given(playerService.makeMove("w")).willReturn(Direction.UP)
        BDDMockito.given(movementService.canMakeMove()).willReturn(true, false)
        provideInput("w")

        //when
        gameBoardService.playGame()

        //then
        BDDMockito.verify(playerService, BDDMockito.times(1)).makeMove("w")
        BDDMockito.verify(movementService, BDDMockito.times(1)).shift(Direction.UP)
        BDDMockito.verify(playerService, BDDMockito.times(1)).addNewTile()
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