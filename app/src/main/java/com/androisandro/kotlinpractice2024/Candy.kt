package com.androisandro.kotlinpractice2024

/**
 * Data class representing a single candy in the game board
 * @param type The type of candy
 * @param row The row position on the board
 * @param col The column position on the board
 */
data class Candy(
    var type: CandyType = CandyType.EMPTY,
    var row: Int = 0,
    var col: Int = 0,
    var isMatched: Boolean = false
)

