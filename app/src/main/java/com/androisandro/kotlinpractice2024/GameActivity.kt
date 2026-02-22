package com.androisandro.kotlinpractice2024

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

/**
 * Main activity for the Candy Crush game
 */
class GameActivity : AppCompatActivity() {
    private lateinit var gameViewModel: GameViewModel
    private lateinit var candyView: CandyView
    private lateinit var scoreTextView: TextView
    private lateinit var movesTextView: TextView
    private lateinit var gameStatusTextView: TextView
    private lateinit var resetButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        // Initialize ViewModel
        gameViewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        // Initialize views
        candyView = findViewById(R.id.candyView)
        scoreTextView = findViewById(R.id.scoreTextView)
        movesTextView = findViewById(R.id.movesTextView)
        gameStatusTextView = findViewById(R.id.gameStatusTextView)
        resetButton = findViewById(R.id.resetButton)

        // Set up candy view with view model
        candyView.setViewModel(gameViewModel)

        // Observe board changes
        gameViewModel.board.observe(this) { board ->
            candyView.updateBoard(board)
        }

        // Observe selected candy changes
        gameViewModel.selectedCandyLiveData.observe(this) { candy ->
            candyView.updateSelectedCandy(candy)
        }

        // Observe score gameViewModel.score.observe(this) { score ->
            scoreTextView.text = "Score: $score"
        }

        // Observe moves
        gameViewModel.moves.observe(this) { moves ->
            movesTextView.text = "Moves: $moves"
        }

        // Observe game over
        gameViewModel.gameOver.observe(this) { gameOver ->
            if (gameOver) {
                gameStatusTextView.text = "Game Over!"
                gameStatusTextView.setTextColor(android.graphics.Color.RED)
            }
        }

        // Observe game won
        gameViewModel.gameWon.observe(this) { gameWon ->
            if (gameWon) {
                gameStatusTextView.text = "You Won!"
                gameStatusTextView.setTextColor(android.graphics.Color.GREEN)
            }
        }

        // Reset button
        resetButton.setOnClickListener {
            gameViewModel.resetGame()
            gameStatusTextView.text = ""
            gameStatusTextView.setTextColor(android.graphics.Color.BLACK)
        }

        // Initialize the game
        gameViewModel.initializeGame()

        // Observe swap animation event
        gameViewModel.swapEvent.observe(this) { swap ->
            swap?.let { (from, to) ->
                candyView.animateSwap(from, to) {
                    // After animation, update board
                    gameViewModel.swapEvent.value = null
                }
            }
        }
        // Observe match fade animation event
        gameViewModel.matchFadeEvent.observe(this) { matches ->
            matches?.let {
                candyView.animateMatchFade(it) {
                    gameViewModel.matchFadeEvent.value = null
                }
            }
        }
    }
}
