package com.androisandro.kotlinpractice2024

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

/**
 * ViewModel for managing game state and logic
 */
class GameViewModel : ViewModel() {
    private val _board = MutableLiveData<Array<Array<Candy>>>()
    val board: LiveData<Array<Array<Candy>>> = _board

    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int> = _score

    private val _moves = MutableLiveData<Int>()
    val moves: LiveData<Int> = _moves

    private val _gameOver = MutableLiveData<Boolean>()
    val gameOver: LiveData<Boolean> = _gameOver

    private val _gameWon = MutableLiveData<Boolean>()
    val gameWon: LiveData<Boolean> = _gameWon

    private val _selectedCandy = MutableLiveData<Pair<Int, Int>?>()
    val selectedCandyLiveData: LiveData<Pair<Int, Int>?> = _selectedCandy

    val swapEvent = MutableLiveData<Pair<Pair<Int, Int>, Pair<Int, Int>>?>()
    val matchFadeEvent = MutableLiveData<Set<Pair<Int, Int>>?>()
    val dropEvent = MutableLiveData<List<Pair<Pair<Int, Int>, Pair<Int, Int>>>?>()

    private val gameBoard = GameBoard()
    private var selectedCandy: Pair<Int, Int>? = null
    private val viewModelScope = CoroutineScope(Dispatchers.Main + Job())


    /**
     * Initialize the game
     */
    fun initializeGame() {
        val candies = gameBoard.getAllCandies()
        if (candies.size != 8 || candies[0].size != 8) {
            // Fallback: re-create board if invalid
            val newBoard = GameBoard()
            _board.value = newBoard.getAllCandies()
        } else {
            _board.value = candies
        }
        _score.value = gameBoard.score
        _moves.value = gameBoard.moves
        _gameOver.value = false
        _gameWon.value = false
    }

    /**
     * Handle candy selection and swapping
     */
    fun onCandyClicked(row: Int, col: Int) {
        if (_gameOver.value == true || _gameWon.value == true) return

        if (selectedCandy == null) {
            selectedCandy = Pair(row, col)
            _selectedCandy.value = selectedCandy
        } else {
            val (prevRow, prevCol) = selectedCandy!!
            if (prevRow == row && prevCol == col) {
                // Deselect
                selectedCandy = null
                _selectedCandy.value = null
                return
            }

            if (gameBoard.isAdjacent(prevRow, prevCol, row, col)) {
                swapEvent.value = Pair(Pair(prevRow, prevCol), Pair(row, col))
                gameBoard.swapCandies(prevRow, prevCol, row, col)
                _board.value = gameBoard.getAllCandies()

                val matches = gameBoard.findMatches()
                if (matches.isNotEmpty()) {
                    matchFadeEvent.value = matches
                    processMatches()
                } else {
                    // Swap back if no match
                    viewModelScope.launch {
                        delay(200)
                        swapEvent.value = Pair(Pair(row, col), Pair(prevRow, prevCol))
                        gameBoard.swapCandies(prevRow, prevCol, row, col)
                        _board.value = gameBoard.getAllCandies()
                        selectedCandy = null
                        _selectedCandy.value = null
                    }
                    return
                }

                selectedCandy = null
                _selectedCandy.value = null
                gameBoard.moves--
                _moves.value = gameBoard.moves

                if (gameBoard.moves <= 0) {
                    if (gameBoard.score >= 1000) {
                        _gameWon.value = true
                    } else {
                        _gameOver.value = true
                    }
                }
            }

            selectedCandy = null
            _selectedCandy.value = null
        }
    }

    /**
     * Process matches and cascade
     */
    private fun processMatches() {
        viewModelScope.launch {
            var hasMatches = true
            while (hasMatches) {
                delay(300)
                val matches = gameBoard.findMatches()
                if (matches.isEmpty()) {
                    hasMatches = false
                } else {
                    matchFadeEvent.value = matches
                    gameBoard.removeMatches(matches)
                    _score.value = gameBoard.score
                    _board.value = gameBoard.getAllCandies()

                    delay(300)
                    // TODO: dropEvent for gravity
                    gameBoard.applyGravity()
                    _board.value = gameBoard.getAllCandies()

                    delay(300)
                    gameBoard.fillEmptySpaces()
                    _board.value = gameBoard.getAllCandies()
                }
            }
        }
    }

    /**
     * Reset the game
     */
    fun resetGame() {
        gameBoard.reset()
        selectedCandy = null
        _selectedCandy.value = null
        initializeGame()
    }

    /**
     * Get selected candy
     */
    fun getSelectedCandy(): Pair<Int, Int>? = selectedCandy

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}
