package _2048.gameboard

interface GameBoardService {
    /**
     * Method to initialize the game
     */
    suspend fun playGame()
}