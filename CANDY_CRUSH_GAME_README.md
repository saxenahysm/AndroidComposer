# Candy Crush Game - Kotlin Android Implementation

A fun match-3 puzzle game built with Kotlin and Android. This is a Candy Crush-like game with core game mechanics similar to the popular mobile game.

## Features

### Game Mechanics
- **8x8 Game Board**: A classic grid-based match-3 puzzle board
- **6 Different Candy Types**: Red, Green, Blue, Yellow, Purple, and Orange candies
- **Match Detection**: Automatically detects matches of 3 or more candies in a row (horizontal or vertical)
- **Cascade System**: Candies fall down after matches are removed, and new candies appear to fill empty spaces
- **Score System**: Earn 10 points for each matched candy
- **Move Limit**: 30 moves to reach a score of 1000 points to win the game

### Game Rules
1. **Select and Swap**: Click on two adjacent candies to swap them
2. **Create Matches**: Make matches of 3 or more identical candies in a row (horizontal or vertical)
3. **Cascade**: When candies are matched and removed, remaining candies fall down and new ones fill the gaps
4. **Scoring**: Earn points by making matches - the more candies in a match, the better!
5. **Win/Lose**: 
   - **Win**: Reach 1000+ points before running out of moves
   - **Lose**: Run out of moves without reaching 1000 points

## Project Structure

### Kotlin Classes

1. **CandyTypes.kt**
   - `CandyType` enum: Defines the 6 types of candies plus EMPTY state

2. **Candy.kt**
   - `Candy` data class: Represents a single candy with position and state

3. **GameBoard.kt**
   - `GameBoard` class: Core game logic
   - Methods:
     - `getCandy()`: Get candy at position
     - `swapCandies()`: Swap two adjacent candies
     - `isAdjacent()`: Check if two candies are adjacent
     - `findMatches()`: Detect all matches on the board
     - `removeMatches()`: Remove matched candies and update score
     - `applyGravity()`: Make candies fall down
     - `fillEmptySpaces()`: Fill empty spaces with new candies
     - `hasValidMoves()`: Check if any valid moves remain

4. **GameViewModel.kt**
   - `GameViewModel` class: MVVM architecture for game state management
   - Uses LiveData for reactive UI updates
   - Manages game flow, animations, and user interactions
   - Handles coroutines for asynchronous operations

5. **CandyView.kt**
   - Custom View that renders the game board
   - Handles touch input for candy selection and swapping
   - Draws candies as colored circles with selection highlights

6. **GameActivity.kt**
   - Main activity displaying the game
   - Shows score, moves remaining, and game status
   - Displays "New Game" button to restart

7. **MainActivity.kt**
   - Launch activity with button to start the game

### Layout Files

- **activity_main.xml**: Menu screen with "Play Game" button
- **activity_game.xml**: Main game screen with:
  - Game board display (CandyView)
  - Score display
  - Moves remaining display
  - Game status display (Win/Lose)
  - "New Game" button

## How to Play

1. Launch the app and tap "Play Game"
2. The game board will display with 8x8 grid of random candies
3. Tap on a candy to select it (it will be highlighted with a white circle)
4. Tap an adjacent candy to swap with the selected candy
5. If 3+ candies of the same color match horizontally or vertically:
   - They are removed
   - You earn 10 points per candy removed
   - Remaining candies fall down (gravity effect)
   - New candies fill the empty spaces
6. Continue making matches to increase your score
7. **Goal**: Reach 1000+ points before running out of moves

## Technical Stack

- **Language**: Kotlin
- **Architecture**: MVVM with LiveData
- **Android Version**: Min SDK 29, Target SDK 33
- **Dependencies**:
  - AndroidX Core KTX
  - AndroidX AppCompat
  - AndroidX Lifecycle (ViewModel, LiveData)
  - Kotlin Coroutines
  - Material Design

## Key Design Patterns

1. **MVVM Architecture**: Separation of concerns between UI and business logic
2. **LiveData**: Reactive data binding for UI updates
3. **Coroutines**: Asynchronous operations for game animations and delays
4. **Custom View**: Custom canvas drawing for game board rendering
5. **ViewModel**: Manages game state and survives configuration changes

## Algorithm Details

### Match Finding Algorithm
The game uses a two-pass approach:
1. **Horizontal Pass**: Check each row for sequences of 3+ identical candies
2. **Vertical Pass**: Check each column for sequences of 3+ identical candies

### Gravity Simulation
Candies fall down using:
1. For each column, iterate from bottom to top
2. Find the next non-empty candy
3. Move it to the lowest available position
4. Update candy positions

### Cascade System
1. Remove matched candies
2. Apply gravity
3. Fill empty spaces with new candies
4. Check for new matches and repeat until no more matches exist

## Future Enhancement Ideas

- Sound effects and music
- Power-ups and special candies
- Difficulty levels
- Leaderboard system
- More visual effects and animations
- Different game modes (time attack, survival)
- Touch gesture animations (swipe-to-swap)
- Undo functionality
- Daily challenges

## Building and Running

1. Open the project in Android Studio
2. Ensure you have Android SDK 34 installed
3. Run the app on an emulator or physical device with Android 10+ (API 29+)
4. Tap "Play Game" to start playing

Enjoy the game!

