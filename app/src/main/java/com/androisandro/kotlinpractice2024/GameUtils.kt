package com.androisandro.kotlinpractice2024

/**
 * Game statistics and utilities
 */
data class GameStats(
    val score: Int = 0,
    val movesRemaining: Int = 30,
    val totalMatches: Int = 0,
    val totalCandiesRemoved: Int = 0,
    val gameStartTime: Long = System.currentTimeMillis()
) {
    fun getDuration(): Long = System.currentTimeMillis() - gameStartTime
}

/**
 * Game difficulty levels
 */
enum class GameDifficulty {
    EASY,      // 50 moves, score goal 500
    MEDIUM,    // 30 moves, score goal 1000
    HARD,      // 20 moves, score goal 1500
    EXPERT     // 15 moves, score goal 2000
}

/**
 * Difficulty configuration
 */
data class DifficultyConfig(
    val difficulty: GameDifficulty,
    val initialMoves: Int,
    val scoreGoal: Int,
    val boardSize: Int = 8
) {
    companion object {
        fun getConfig(difficulty: GameDifficulty): DifficultyConfig {
            return when (difficulty) {
                GameDifficulty.EASY -> DifficultyConfig(GameDifficulty.EASY, 50, 500)
                GameDifficulty.MEDIUM -> DifficultyConfig(GameDifficulty.MEDIUM, 30, 1000)
                GameDifficulty.HARD -> DifficultyConfig(GameDifficulty.HARD, 20, 1500)
                GameDifficulty.EXPERT -> DifficultyConfig(GameDifficulty.EXPERT, 15, 2000)
            }
        }
    }
}

/**
 * Utility object for game-related calculations
 */
object GameUtils {
    /**
     * Calculate bonus points based on match size
     */
    fun calculateBonusPoints(matchSize: Int): Int {
        return when {
            matchSize < 3 -> 0
            matchSize == 3 -> 10
            matchSize == 4 -> 25
            matchSize == 5 -> 50
            else -> 50 + (matchSize - 5) * 10
        }
    }

    /**
     * Check if game is won
     */
    fun isGameWon(score: Int, goalScore: Int): Boolean {
        return score >= goalScore
    }

    /**
     * Check if game is lost
     */
    fun isGameLost(movesRemaining: Int, score: Int, goalScore: Int): Boolean {
        return movesRemaining <= 0 && score < goalScore
    }

    /**
     * Get difficulty level description
     */
    fun getDifficultyDescription(difficulty: GameDifficulty): String {
        return when (difficulty) {
            GameDifficulty.EASY -> "Relaxed pace, plenty of moves"
            GameDifficulty.MEDIUM -> "Standard puzzle challenge"
            GameDifficulty.HARD -> "Challenging puzzle experience"
            GameDifficulty.EXPERT -> "Ultimate puzzle mastery"
        }
    }

    /**
     * Calculate time-based bonuses
     */
    fun calculateTimeBonus(secondsRemaining: Long): Int {
        return when {
            secondsRemaining > 60 -> 100
            secondsRemaining > 30 -> 50
            secondsRemaining > 10 -> 25
            else -> 0
        }
    }
}

