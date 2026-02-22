# Candy Crush Game - Visual Architecture & Flow

## 🏗️ Architecture Diagram

```
┌─────────────────────────────────────────────────────────────────┐
│                         User Interface Layer                     │
├─────────────────────────────────────────────────────────────────┤
│                                                                   │
│  MainActivity              GameActivity              CandyView   │
│  ┌─────────────┐          ┌──────────────┐        ┌───────────┐ │
│  │ Menu Screen │          │ Game Screen  │        │ Canvas    │ │
│  │             │          │              │        │ Rendering │ │
│  │ [Play Game] │──────→   │ Score: 0     │        │           │ │
│  │  Button     │          │ Moves: 30    │        │ ✓ Touch   │ │
│  └─────────────┘          │              │        │ ✓ Draw    │ │
│                           │ [New Game]   │        └───────────┘ │
│                           └──────────────┘                        │
│                                  ▲                                │
│                                  │ Observes                       │
│                                  │                                │
├─────────────────────────────────┼────────────────────────────────┤
│                    State Management Layer (MVVM)                 │
├─────────────────────────────────┼────────────────────────────────┤
│                                  │                                │
│                        GameViewModel                             │
│                    ┌──────────────────────┐                      │
│                    │  LiveData:           │                      │
│                    │  • board             │                      │
│                    │  • score             │                      │
│                    │  • moves             │                      │
│                    │  • gameOver          │                      │
│                    │  • gameWon           │                      │
│                    │                      │                      │
│                    │  Methods:            │                      │
│                    │  • onCandyClicked()  │                      │
│                    │  • resetGame()       │                      │
│                    │  • processMatches()  │                      │
│                    └──────────────────────┘                      │
│                                  │ Uses                           │
│                                  ▼                                │
├─────────────────────────────────┬────────────────────────────────┤
│                    Game Logic Layer                              │
├─────────────────────────────────┬────────────────────────────────┤
│                                  │                                │
│                        GameBoard                                 │
│    ┌──────────────────────────────────────────────┐             │
│    │  • board: Array[8][8]                        │             │
│    │  • score, moves                              │             │
│    │                                              │             │
│    │  Core Methods:                               │             │
│    │  • findMatches()        → Set<Pair>          │             │
│    │  • removeMatches()      → Update score       │             │
│    │  • applyGravity()       → Drop candies      │             │
│    │  • fillEmptySpaces()    → Add new candies   │             │
│    │  • swapCandies()        → Swap adjacent     │             │
│    │  • hasValidMoves()      → Check game state  │             │
│    │                                              │             │
│    └──────────────────────────────────────────────┘             │
│                                  │ Uses                           │
│                                  ▼                                │
├─────────────────────────────────┬────────────────────────────────┤
│                      Data Models Layer                           │
├─────────────────────────────────┬────────────────────────────────┤
│                                  │                                │
│            Candy                CandyType         GameUtils       │
│    ┌──────────────────┐    ┌──────────────┐    ┌────────────┐   │
│    │ • type           │    │ • RED        │    │ Difficulties   │
│    │ • row, col       │    │ • GREEN      │    │ • EASY         │
│    │ • isMatched      │    │ • BLUE       │    │ • MEDIUM       │
│    └──────────────────┘    │ • YELLOW     │    │ • HARD         │
│                            │ • PURPLE     │    │ • EXPERT       │
│                            │ • ORANGE     │    │                │
│                            │ • EMPTY      │    │ Utils:         │
│                            └──────────────┘    │ • Scoring      │
│                                                │ • Bonuses      │
│                                                └────────────────┘│
│                                                                   │
└─────────────────────────────────────────────────────────────────┘
```

## 🔄 Game Flow Diagram

```
START
  │
  ▼
┌─────────────────────┐
│  MainActivity       │
│  (Menu Screen)      │
│  [Play Game Button] │
└──────────┬──────────┘
           │
           ▼
┌─────────────────────────────────────────────┐
│  GameActivity Created                       │
│  └─→ GameViewModel.initializeGame()        │
│  └─→ GameBoard initialized (8×8 grid)      │
│  └─→ Random candies placed                  │
└──────────┬──────────────────────────────────┘
           │
           ▼
    ┌─────────────────────────────────────────┐
    │  Game Loop - Waiting for User Input     │
    │                                         │
    │  User taps candy #1 (Selected)          │
    │  User taps candy #2 (Adjacent)          │
    │                                         │
    │  ▼                                      │
    │  GameViewModel.onCandyClicked()         │
    │  └─→ GameBoard.swapCandies()            │
    │  └─→ GameBoard.findMatches()            │
    │                                         │
    │  IF matches found:                      │
    │  ├─→ GameBoard.removeMatches()          │
    │  ├─→ GameBoard.applyGravity()           │
    │  ├─→ GameBoard.fillEmptySpaces()        │
    │  ├─→ Check for cascade (repeat)         │
    │  ├─→ Update score                       │
    │  ├─→ _board.value = updated             │
    │  ├─→ CandyView.onDraw() updates         │
    │  │                                      │
    │  ELSE (no match):                       │
    │  └─→ Swap back after delay (300ms)      │
    │                                         │
    │  ▼                                      │
    │  Decrement moves: moves--                │
    │                                         │
    │  Check win/lose:                        │
    │  ├─ IF score >= 1000: _gameWon = true   │
    │  ├─ IF moves == 0 & score < 1000:       │
    │  │  _gameOver = true                    │
    │  └─ ELSE: Continue loop                 │
    │                                         │
    └─────────────────────────────────────────┘
           │
           ▼
    ┌─────────────────────────────────────────┐
    │  Check Game Status                      │
    └──────┬──────────────────────────────────┘
           │
           ├─────────────────────┐
           │                     │
           ▼                     ▼
        WIN             ┌──────────────┐
    Score >= 1000       │    LOSE      │
    Display             │ Moves = 0 &  │
    "You Won!"          │ Score < 1000 │
           │            │              │
           │            │ Display      │
           │            │ "Game Over!" │
           │            └──────┬───────┘
           │                   │
           ├───────────┬───────┤
           │           │       │
           ▼           ▼       │
    ┌──────────────────────────────────┐
    │  User clicks [New Game] button   │
    │  └─→ GameViewModel.resetGame()   │
    │  └─→ GameBoard.reset()           │
    │  └─→ Return to Game Loop         │
    └──────────────────────────────────┘
```

## 🎯 Match Detection Algorithm

```
findMatches() {
  matches = Set<Pair<Int, Int>>()
  
  // HORIZONTAL PASS
  For each row (0 to 7):
    For each position (0 to 5):
      IF board[row][col] == board[row][col+1] == board[row][col+2]:
        Add (row, col), (row, col+1), (row, col+2) to matches
        Continue checking for 4+ in a row...
  
  // VERTICAL PASS
  For each column (0 to 7):
    For each position (0 to 5):
      IF board[row][col] == board[row+1][col] == board[row+2][col]:
        Add (row, col), (row+1, col), (row+2, col) to matches
        Continue checking for 4+ in a row...
  
  Return matches
}
```

## 📊 State Management Flow

```
User Action (Touch)
        │
        ▼
onCandyClicked(row, col)
        │
        ├─→ if (selectedCandy == null)
        │   └─→ selectedCandy = Pair(row, col)
        │       (Display highlight)
        │
        └─→ else if (adjacent to selectedCandy)
            ├─→ swapCandies()
            ├─→ findMatches()
            │
            ├─ If matches:
            │  └─→ _board.value = updated
            │  └─→ CandyView.invalidate()
            │  └─→ processMatches()
            │      ├─→ delay(300ms)
            │      ├─→ removeMatches()
            │      ├─→ applyGravity()
            │      ├─→ fillEmptySpaces()
            │      └─→ Check for cascade
            │
            └─ Else:
               └─→ Swap back after delay
                   └─→ selectedCandy = null
```

## 🎨 View Hierarchy

```
GameActivity (FrameLayout/LinearLayout)
│
├─ TextView (Title: "Candy Crush")
│
├─ LinearLayout (Header Info)
│  ├─ TextView (Score: 0)
│  └─ TextView (Moves: 30)
│
├─ TextView (Game Status: "")
│
├─ CandyView (8×8 Game Board)
│  ├─ Custom Canvas Drawing
│  ├─ Colored Circles (Candies)
│  ├─ Selection Highlights
│  └─ Touch Event Handling
│
└─ Button (New Game)
```

## ⏱️ Timing Sequence

```
User swaps two candies:
├─ T+0ms    : Swap animation starts
├─ T+200ms  : Swap completes
├─ T+300ms  : Match detection and removal
├─ T+300ms  : Candies start falling (gravity)
├─ T+600ms  : New candies appear
├─ T+600ms  : Check for cascade matches
├─ T+900ms  : Remove cascade matches (if any)
├─ T+1200ms: New candies for cascade
└─ T+1500ms: Game ready for next move
```

## 🔀 Cascade Effect Example

```
Initial Board:          After Swap:           After Remove:
┌─────────┐           ┌─────────┐           ┌─────────┐
│ R G B   │           │ R G B   │           │ R G     │
│ G R B   │ ─────→    │ B G B   │  ─────→   │ B G B   │
│ B G B   │ Swap      │ G R B   │           │ G R B   │
└─────────┘           └─────────┘           └─────────┘
                                            (B-B-B removed)

After Gravity:        After Fill:
┌─────────┐          ┌─────────┐
│         │          │ O P     │
│ R G     │  ─────→  │ R G     │
│ B G B   │          │ B G B   │
│ G R B   │          │ G R B   │
└─────────┘          └─────────┘
(Candies fall)       (New candies added)
```

## 🔧 Coroutine-Based Cascade

```
processMatches() {
  while (true) {
    delay(300ms)  // Visual delay before removal
    
    matches = findMatches()
    if (matches.isEmpty()) break
    
    removeMatches(matches)
    _board.value = updated
    
    delay(300ms)  // Visual delay for removal animation
    
    applyGravity()
    _board.value = updated
    
    delay(300ms)  // Visual delay for falling
    
    fillEmptySpaces()
    _board.value = updated
  }
}
```

---

## 📝 Summary

The architecture follows clean MVVM principles with:
- **Reactive UI**: LiveData automatically updates the view
- **Separation of Concerns**: Logic in GameBoard, UI in GameActivity/CandyView
- **Async Operations**: Coroutines for smooth animations
- **Efficient Rendering**: Custom canvas view for performance

This design makes the code maintainable, testable, and easy to extend with new features!

