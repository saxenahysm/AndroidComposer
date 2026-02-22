package com.androisandro.kotlinpractice2024

import kotlin.random.Random

/**
 * Class managing the game board and game logic
 * @param rows Number of rows in the board
 * @param cols Number of columns in the board
 */
class GameBoard(val rows: Int = 8, val cols: Int = 8) {
    private val candyTypes = listOf(
        CandyType.RED, CandyType.GREEN, CandyType.BLUE,
        CandyType.YELLOW, CandyType.PURPLE, CandyType.ORANGE
    )

    private val board = Array(rows) { row ->
        Array(cols) { col ->
            Candy(getRandomCandyType(), row, col)
        }
    }

    var score = 0
    var moves = 30

    /**
     * Get a candy at a specific position
     */
    fun getCandy(row: Int, col: Int): Candy? {
        return if (row in 0 until rows && col in 0 until cols) {
            board[row][col]
        } else {
            null
        }
    }

    /**
     * Get all candies on the board
     */
    fun getAllCandies(): Array<Array<Candy>> = board

    /**
     * Get a random candy type
     */
    private fun getRandomCandyType(): CandyType {
        return candyTypes[Random.nextInt(candyTypes.size)]
    }

    /**
     * Swap two candies
     */
    fun swapCandies(row1: Int, col1: Int, row2: Int, col2: Int) {
        if (isValidPosition(row1, col1) && isValidPosition(row2, col2)) {
            val temp = board[row1][col1]
            board[row1][col1] = board[row2][col2]
            board[row2][col2] = temp

            // Update positions
            board[row1][col1].row = row1
            board[row1][col1].col = col1
            board[row2][col2].row = row2
            board[row2][col2].col = col2
        }
    }

    /**
     * Check if position is valid
     */
    private fun isValidPosition(row: Int, col: Int): Boolean {
        return row in 0 until rows && col in 0 until cols
    }

    /**
     * Check if two positions are adjacent
     */
    fun isAdjacent(row1: Int, col1: Int, row2: Int, col2: Int): Boolean {
        val rowDiff = kotlin.math.abs(row1 - row2)
        val colDiff = kotlin.math.abs(col1 - col2)
        return (rowDiff + colDiff) == 1
    }

    /**
     * Find all matches on the board
     */
    fun findMatches(): Set<Pair<Int, Int>> {
        val matches = mutableSetOf<Pair<Int, Int>>()

        // Check horizontal matches
        for (row in 0 until rows) {
            for (col in 0 until cols - 2) {
                val candyType = board[row][col].type
                if (candyType == CandyType.EMPTY) continue

                if (board[row][col + 1].type == candyType &&
                    board[row][col + 2].type == candyType
                ) {
                    matches.add(Pair(row, col))
                    matches.add(Pair(row, col + 1))
                    matches.add(Pair(row, col + 2))
                }
            }
        }

        // Check vertical matches
        for (row in 0 until rows - 2) {
            for (col in 0 until cols) {
                val candyType = board[row][col].type
                if (candyType == CandyType.EMPTY) continue

                if (board[row + 1][col].type == candyType &&
                    board[row + 2][col].type == candyType
                ) {
                    matches.add(Pair(row, col))
                    matches.add(Pair(row + 1, col))
                    matches.add(Pair(row + 2, col))
                }
            }
        }

        return matches
    }

    /**
     * Remove matched candies
     */
    fun removeMatches(matches: Set<Pair<Int, Int>>) {
        for ((row, col) in matches) {
            board[row][col].type = CandyType.EMPTY
            board[row][col].isMatched = true
            score += 10
        }
    }

    /**
     * Apply gravity - candies fall down
     */
    fun applyGravity() {
        for (col in 0 until cols) {
            var writePos = rows - 1
            for (row in rows - 1 downTo 0) {
                if (board[row][col].type != CandyType.EMPTY) {
                    if (row != writePos) {
                        board[writePos][col] = board[row][col]
                        board[writePos][col].row = writePos
                        board[writePos][col].col = col
                        board[row][col] = Candy(CandyType.EMPTY, row, col)
                    }
                    writePos--
                }
            }
        }
    }

    /**
     * Fill empty spaces with new candies
     */
    fun fillEmptySpaces() {
        for (row in 0 until rows) {
            for (col in 0 until cols) {
                if (board[row][col].type == CandyType.EMPTY) {
                    board[row][col].type = getRandomCandyType()
                    board[row][col].isMatched = false
                }
            }
        }
    }

    /**
     * Check if there are any valid moves
     */
    fun hasValidMoves(): Boolean {
        for (row in 0 until rows) {
            for (col in 0 until cols) {
                // Check right neighbor
                if (col < cols - 1) {
                    swapCandies(row, col, row, col + 1)
                    if (findMatches().isNotEmpty()) {
                        swapCandies(row, col, row, col + 1)
                        return true
                    }
                    swapCandies(row, col, row, col + 1)
                }
                // Check down neighbor
                if (row < rows - 1) {
                    swapCandies(row, col, row + 1, col)
                    if (findMatches().isNotEmpty()) {
                        swapCandies(row, col, row + 1, col)
                        return true
                    }
                    swapCandies(row, col, row + 1, col)
                }
            }
        }
        return false
    }

    /**
     * Reset the board
     */
    fun reset() {
        score = 0
        moves = 30
        for (row in 0 until rows) {
            for (col in 0 until cols) {
                board[row][col].type = getRandomCandyType()
                board[row][col].isMatched = false
            }
        }
    }
}
