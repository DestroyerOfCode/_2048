package _2048.gameboard

enum class Direction(val keyboardButton: String) {
    UP("w"),
    DOWN("s"),
    LEFT("a"),
    RIGHT("d"),
    NONE("")
}