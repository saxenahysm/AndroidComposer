# Candy Crush Game Implementation Guide

## Overview

This is a complete Kotlin-based implementation of a Candy Crush-like match-3 puzzle game for Android. The game features a classic 8x8 grid with 6 different candy types, with the objective of creating matches and clearing candies to earn points.

## Project Structure

```
KotlinPractice2024/
├── app/
│   └── src/
│       └── main/
│           ├── java/com/androisandro/kotlinpractice2024/
│           │   ├── MainActivity.kt              # Launch screen
│           │   ├── GameActivity.kt              # Main game activity
│           │   ├── GameViewModel.kt             # MVVM ViewModel
│           │   ├── GameBoard.kt                 # Game logic and board management
│           │   ├── CandyView.kt                 # Custom view for rendering
│           │   ├── CandyTypes.kt                # Candy type enum
│           │   ├── Candy.kt                     # Candy data class
│           │   ├── GameAnimations.kt            # Animation utilities
│           │   └── GameUtils.kt                 # Game utility functions
│           └── res/
│               └── layout/
│                   ├── activity_main.xml        # Launch screen layout
│                   └── activity_game.xml        # Game screen layout
└── CANDY_CRUSH_GAME_README.md
```

## Key Components

### 1. Data Models

#### CandyTypes.kt
```kotlin
enum class CandyType {
    RED, GREEN, BLUE, YELLOW, PURPLE, ORANGE, EMPTY
}
```
Defines the six candy colors available in the game, plus an EMPTY state for cleared positions.

#### Candy.kt
```kotlin
data class Candy(
    var type: CandyType = CandyType.EMPTY,
    var row: Int = 0,
    var col: Int = 0,
    var isMatched: Boolean = false
)
```
Represents a single candy with its type, position, and match status.

### 2. Game Logic

#### GameBoard.kt
The core game engine managing:
- Board state (8x8 grid)
- Candy swapping
- Match detection (horizontal and vertical)
- Gravity simulation (candies falling)
- Score management
- Move tracking

**Key Methods:**
- `swapCandies(row1, col1, row2, col2)` - Swap two adjacent candies
- `findMatches()` - Detect all current matches
- `removeMatches(matches)` - Remove matched candies and update score
- `applyGravity()` - Make candies fall after removal
- `fillEmptySpaces()` - Create new candies in empty positions
- `hasValidMoves()` - Check if any moves remain

**Algorithm: Match Detection**
```kotlin
fun findMatches(): Set<Pair<Int, Int>> {
    val matches = mutableSetOf<Pair<Int, Int>>()
    
    // Horizontal: Check each row for 3+ consecutive candies
    for (row in 0 until rows) {
        for (col in 0 until cols - 2) {
            if (board[row][col].type == board[row][col+1].type &&
                board[row][col+1].type == board[row][col+2].type) {
                // Add all matching candies
            }
        }
    }
    
    // Vertical: Check each column for 3+ consecutive candies
    for (row in 0 until rows - 2) {
        for (col in 0 until cols) {
            if (board[row][col].type == board[row+1][col].type &&
                board[row+1][col].type == board[row+2][col].type) {
                // Add all matching candies
            }
        }
    }
    
    return matches
}
```

### 3. UI Layer

#### GameViewModel.kt
Implements MVVM architecture:
- Manages game state (board, score, moves, game status)
- Uses LiveData for reactive updates
- Handles user interactions (candy clicks)
- Manages game flow and animations via coroutines
- Survives configuration changes

**State Management:**
```kotlin
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
```

#### CandyView.kt
Custom View rendering:
- Draws 8x8 candy grid with colored circles
- Handles touch input for candy selection
- Displays selection highlights
- Updates when board state changes

**Rendering Logic:**
```kotlin
override fun onDraw(canvas: Canvas) {
    for (row in 0..7) {
        for (col in 0..7) {
            val candy = board[row][col]
            val x = col * candySize
            val y = row * candySize
            
            // Draw candy circle with color based on type
            canvas.drawCircle(x + candySize/2, y + candySize/2, 
                            candySize/2 - 4, paint)
        }
    }
}
```

#### GameActivity.kt
Main game activity:
- Displays game board and UI elements
- Observes ViewModel live data
- Updates score, moves, and status
- Provides reset button

### 4. Utility Classes

#### GameUtils.kt
Provides:
- Difficulty configurations (EASY, MEDIUM, HARD, EXPERT)
- Point calculation functions
- Game state checking utilities
- Time bonus calculations

#### GameAnimations.kt
Animation helpers:
- Fade out animation for matched candies
- Pop animation for visual feedback
- Fall animation for gravity effect

## Game Flow

```
1. Start App → MainActivity (Menu Screen)
   ↓
2. Click "Play Game" → GameActivity
   ↓
3. GameViewModel initializes GameBoard with random candies
   ↓
4. User touches candy → CandyView.onTouchEvent()
   ↓
5. GameViewModel.onCandyClicked() handles selection/swap
   ↓
6. If swap creates match:
   - GameBoard.findMatches() identifies matched candies
   - GameBoard.removeMatches() removes them and updates score
   - GameBoard.applyGravity() makes candies fall
   - GameBoard.fillEmptySpaces() creates new candies
   - Repeat until no more matches (cascade effect)
   ↓
7. Moves decrement, check win/lose conditions
   ↓
8. Continue or click "New Game" to restart
```

## Game Rules & Mechanics

### Match System
- **Minimum**: 3 consecutive candies of same color (horizontal/vertical)
- **Scoring**: 10 points per matched candy
- **Bonus**: Extra points for larger matches

### Cascade Effect
1. Candies are removed
2. Gravity pulls remaining candies down
3. Empty spaces filled with new random candies
4. Check for new matches automatically
5. Repeat until no more cascades

### Win Condition
- Reach 1000+ points before running out of moves

### Lose Condition
- Run out of 30 moves with score < 1000 points

## Technical Details

### Architecture Patterns
1. **MVVM**: Separation between UI (Activity/View) and logic (ViewModel)
2. **LiveData**: Reactive data binding for automatic UI updates
3. **Coroutines**: Asynchronous operations for animations
4. **Custom View**: Canvas-based rendering for smooth drawing

### Threading Model
- Main thread: UI updates and drawing
- Coroutine scope: Game animations and timing delays
- Async cascade processing with delays for visual feedback

### State Preservation
- ViewModel survives configuration changes (rotation)
- Game state maintained during lifecycle events
- Board state reactive through LiveData

## How to Build and Run

### Prerequisites
- Android Studio (latest version)
- Android SDK 34 (Compile SDK)
- Kotlin 1.9+
- Gradle 8.0+

### Build Steps
1. Open project in Android Studio
2. Sync Gradle files
3. Run → Select emulator or device
4. Game launches on MainActivity menu screen

### Testing
- Test on different screen sizes (phones, tablets)
- Test rotation (ViewModel should preserve state)
- Test edge cases (no valid moves, rapid clicking)

## Performance Considerations

1. **Canvas Drawing**: Efficient circle rendering using Paint
2. **Match Detection**: O(n²) algorithm, acceptable for 8x8 board
3. **Gravity**: O(n) per column, efficient implementation
4. **Memory**: Small memory footprint (64 Candy objects max)
5. **Coroutines**: Non-blocking animations

## Future Enhancements

### Level 1: Visual Enhancements
- [ ] Particle effects for matched candies
- [ ] Smooth transition animations
- [ ] Background colors and patterns
- [ ] High score display

### Level 2: Gameplay Features
- [ ] Power-ups (bombs, dynamite, striped candies)
- [ ] Special candy combinations
- [ ] Difficulty selection before game start
- [ ] Time attack mode

### Level 3: Social Features
- [ ] Local leaderboard
- [ ] Share score functionality
- [ ] Daily challenges
- [ ] Achievements/badges

### Level 4: Advanced Features
- [ ] Persistent save/load game state
- [ ] Sound effects and background music
- [ ] Tutorial/help system
- [ ] Accessibility improvements

## Troubleshooting

### Common Issues

**1. Gradle Sync Fails**
- Clear Gradle cache: `gradle clean`
- Update SDK: Check Android SDK Manager

**2. App Crashes on Launch**
- Check logcat for stack traces
- Verify all imports are correct
- Ensure resources are properly referenced

**3. Game Feels Sluggish**
- Reduce animation durations if device is slow
- Check for ANR (Application Not Responding) warnings

**4. Candies Not Responding to Touches**
- Verify CandyView is properly initialized
- Check touch event coordinates calculation

## Code Examples

### Creating a Custom Game Board Size
```kotlin
// In GameActivity or MainActivity
val gameBoard = GameBoard(rows = 10, cols = 10)
```

### Accessing Game State from ViewModel
```kotlin
viewModel.score.observe(this) { score ->
    scoreTextView.text = "Score: $score"
}

viewModel.gameOver.observe(this) { isOver ->
    if (isOver) handleGameOver()
}
```

### Adding New Candy Type
```kotlin
// In CandyTypes.kt
enum class CandyType {
    RED, GREEN, BLUE, YELLOW, PURPLE, ORANGE, PINK, EMPTY
}

// In GameBoard.kt - update candyTypes list
private val candyTypes = listOf(
    CandyType.RED, CandyType.GREEN, CandyType.BLUE,
    CandyType.YELLOW, CandyType.PURPLE, CandyType.ORANGE,
    CandyType.PINK
)

// In CandyView.kt - update colors map
private val candyColors = mapOf(
    // ... existing colors ...
    CandyType.PINK to Color.rgb(255, 192, 203)
)
```

## Conclusion

This Candy Crush-like game implementation demonstrates:
- Clean Kotlin code practices
- Android MVVM architecture
- Canvas-based custom views
- Coroutine-based animations
- LiveData reactive programming
- Game algorithm implementation

The codebase is well-structured, documented, and ready for further enhancements!

