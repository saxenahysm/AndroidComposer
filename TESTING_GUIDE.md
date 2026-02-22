# Testing Guide - Candy Crush Game

## 🧪 Comprehensive Testing Checklist

### Smoke Tests (Basic Functionality)

#### ✅ App Launch
- [ ] App starts without crashing
- [ ] Main menu appears with "Play Game" button
- [ ] Text is readable and properly aligned

#### ✅ Game Initialization
- [ ] Tapping "Play Game" launches GameActivity
- [ ] Game board (8×8 grid) appears
- [ ] Candies are displayed with different colors
- [ ] Score shows "Score: 0"
- [ ] Moves shows "Moves: 30"
- [ ] "New Game" button is visible

### Game Mechanics Tests

#### ✅ Candy Selection
- [ ] Tapping a candy highlights it (white circle)
- [ ] Tapping the same candy again deselects it
- [ ] Only one candy can be selected at a time
- [ ] Selection highlight is clearly visible

#### ✅ Swapping
- [ ] Can swap candy with adjacent candy (up, down, left, right)
- [ ] Cannot swap with non-adjacent candies
- [ ] Swapped candies change positions immediately
- [ ] Swap animation is smooth

#### ✅ Match Detection - Horizontal
- [ ] 3 candies in a row are detected
- [ ] 4 candies in a row are detected
- [ ] 5+ candies in a row are detected
- [ ] Non-matching swaps are reversed

**Test Cases:**
```
Initial:  R R R B    →   After swap:  R R R B
Result:   Match detected, R's removed
```

#### ✅ Match Detection - Vertical
- [ ] 3 candies in column are detected
- [ ] 4 candies in column are detected  
- [ ] 5+ candies in column are detected

**Test Cases:**
```
Initial:  R       After swap:  B
          R    →               R
          R                    R
Result:   Match detected, R's removed
```

#### ✅ Match Detection - Both Directions
- [ ] Matches in both directions counted correctly
- [ ] No double-counting of candies

#### ✅ Gravity Effect
- [ ] Candies fall after removal
- [ ] Candies fall to bottom of column
- [ ] Candies fall in correct column
- [ ] Multiple candies fall in sequence
- [ ] No candies disappear

**Test Case:**
```
Before:   B G        After:    _ _
          _ G   →             B G
          R G                  R G
```

#### ✅ Filling Empty Spaces
- [ ] New candies appear in empty spaces
- [ ] New candies are random types
- [ ] No candy type dominates
- [ ] Filling doesn't cause stack overflow

#### ✅ Cascade Effect
- [ ] Multiple cascades are handled
- [ ] Score increases for all cascaded matches
- [ ] Timing between cascades is correct
- [ ] Cascades complete before next move

### Scoring Tests

#### ✅ Score Updates
- [ ] Score increases by 10 per matched candy
- [ ] Score persists correctly between moves
- [ ] Score never decreases
- [ ] Score display updates in real-time

**Test Cases:**
```
3 candies matched: Score += 30
4 candies matched: Score += 40
5 candies matched: Score += 50
```

#### ✅ Move Tracking
- [ ] Moves start at 30
- [ ] Moves decrease by 1 per valid swap
- [ ] Moves don't decrease if no match
- [ ] Moves display updates correctly
- [ ] Cannot exceed 30 moves after reset

### Win Condition Tests

#### ✅ Victory Scenario
- [ ] "You Won!" message appears when score >= 1000
- [ ] Message is displayed in green
- [ ] Game state freezes after win
- [ ] Cannot make moves after winning
- [ ] Score display shows final score

**Test Case:**
```
Score: 999  → User makes match of 10+ candies → Score: 1009
Display: "You Won!"
```

### Lose Condition Tests

#### ✅ Loss Scenario
- [ ] "Game Over!" message appears when moves = 0 and score < 1000
- [ ] Message is displayed in red
- [ ] Game state freezes after loss
- [ ] Cannot make moves after losing
- [ ] Final score is displayed

**Test Case:**
```
Moves: 1, Score: 500 → User makes match → Moves: 0
Display: "Game Over!" (Score still < 1000)
```

### Reset Tests

#### ✅ New Game Button
- [ ] "New Game" button is accessible at any time
- [ ] Clicking resets score to 0
- [ ] Clicking resets moves to 30
- [ ] Board is repopulated with new candies
- [ ] Game status message clears
- [ ] Game returns to playable state

**Test Case:**
```
Before: Score: 500, Moves: 15, Status: "Game Over!"
Click [New Game]
After:  Score: 0, Moves: 30, Status: ""
```

### Edge Cases & Boundary Tests

#### ✅ Board Edges
- [ ] Can select candies at edges (row 0, 7, col 0, 7)
- [ ] Can swap candies at edges
- [ ] No index out of bounds errors
- [ ] Edge candies behave like normal candies

#### ✅ Multiple Matches
- [ ] Multiple matches in same move are all detected
- [ ] All matched candies are removed in one action
- [ ] Score correct for multiple simultaneous matches

**Test Case:**
```
Before:  R R R G    After removal:  _ _ _ G
         G G G B    and cascade...  R G G B
Result:  Both horizontal and vertical matches counted
```

#### ✅ Full Board Matches
- [ ] If entire board matches (rare), all candies cleared
- [ ] Game continues normally
- [ ] New candies fill empty spaces
- [ ] Score calculated correctly

#### ✅ No Valid Moves
- [ ] Game checks for valid moves
- [ ] If no moves exist, game ends appropriately
- [ ] Score/moves display correctly

### Performance Tests

#### ✅ Responsiveness
- [ ] UI is responsive to touches
- [ ] No lag when swapping candies
- [ ] Cascades complete within reasonable time
- [ ] No ANR (Application Not Responding) errors
- [ ] Frame rate stays consistent

**Benchmarks:**
- Swap response: < 100ms
- Match detection: < 50ms
- Gravity application: < 100ms
- Cascade cycle: 1-2 seconds

#### ✅ Memory Management
- [ ] No memory leaks
- [ ] App doesn't crash after long gameplay
- [ ] Resetting game frees previous board memory

#### ✅ Battery Usage
- [ ] App doesn't drain battery excessively
- [ ] No continuous background processes
- [ ] Coroutines are properly canceled

### Visual Tests

#### ✅ Graphics & Layout
- [ ] Candies are properly centered
- [ ] Board grid is aligned correctly
- [ ] Colors are distinct and readable
- [ ] Text is properly formatted
- [ ] Layout adapts to screen size

#### ✅ Animation Quality
- [ ] Selection highlight is smooth
- [ ] Swap motion is fluid
- [ ] Candy falling is smooth
- [ ] No flickering
- [ ] No clipping

#### ✅ Color Accuracy
- [ ] RED appears red
- [ ] GREEN appears green
- [ ] BLUE appears blue
- [ ] YELLOW appears yellow
- [ ] PURPLE appears purple
- [ ] ORANGE appears orange

### Device & Screen Size Tests

#### ✅ Device Types
- [ ] Works on phones (tested on various sizes)
- [ ] Works on tablets
- [ ] Works on different aspect ratios
- [ ] Works on different resolutions

#### ✅ Screen Orientations
- [ ] Works in portrait mode
- [ ] Works in landscape mode
- [ ] State preserved on rotation
- [ ] No layout breaks on rotation

#### ✅ Android Versions
- [ ] Android 10 (API 29) - Minimum supported
- [ ] Android 11 (API 30)
- [ ] Android 12 (API 31)
- [ ] Android 13 (API 33) - Target
- [ ] Android 14 (API 34) - Compile

### Regression Tests (After Updates)

#### ✅ Core Mechanics Still Work
- [ ] Matches still detect correctly
- [ ] Gravity still works
- [ ] Cascades still function
- [ ] Scoring still accurate

#### ✅ Fixes Don't Break Features
- After any code change:
  - Run full smoke test
  - Verify all critical paths
  - Check performance metrics

## 🧪 Manual Testing Guide

### Step-by-Step Game Play Test

```
1. Launch app
   ✓ App opens to main menu

2. Click "Play Game"
   ✓ GameActivity loads
   ✓ Board is populated with candies

3. First Move - Simple Match
   ✓ Tap middle candy
   ✓ Tap right neighbor
   ✓ If creates 3-match: candies removed, score increases
   ✓ If not: swap reversed after 200ms

4. Observe Cascade
   ✓ Candies fall down
   ✓ New candies appear
   ✓ Check for matches automatically
   ✓ Repeat until no more matches

5. Continue Playing
   ✓ Score increases with each match
   ✓ Moves decrease after each valid move
   ✓ Verify score calculations

6. Win Scenario (if score reaches 1000+)
   ✓ "You Won!" message appears
   ✓ Message is green
   ✓ Cannot make more moves

7. Reset Game
   ✓ Click "New Game"
   ✓ Score resets to 0
   ✓ Moves reset to 30
   ✓ Board gets new candies

8. Lose Scenario (if moves reach 0 with score < 1000)
   ✓ "Game Over!" message appears
   ✓ Message is red
   ✓ Cannot make more moves
   ✓ Click "New Game" to restart
```

## 🔍 Debugging Tips

### Common Issues & Fixes

| Issue | Cause | Solution |
|-------|-------|----------|
| App crashes on launch | Missing activity in manifest | Add `<activity>` tag |
| Board not displaying | CandyView not initialized | Check GameActivity.onCreate() |
| Touch not responding | Touch events not handled | Verify CandyView.onTouchEvent() |
| Candies not falling | Gravity not applied | Check applyGravity() logic |
| Score not updating | LiveData not notified | Use `_score.value = ...` |
| Moves not decreasing | Move decrement missing | Verify onCandyClicked() |
| No cascades | Match detection incomplete | Check findMatches() logic |
| Screen rotation issues | State not preserved | Verify ViewModel usage |

### Logcat Debugging

```kotlin
// Add debug logs to verify game flow
Log.d("CandyGame", "User clicked: ($row, $col)")
Log.d("CandyGame", "Matches found: $matchCount")
Log.d("CandyGame", "Score: ${gameBoard.score}, Moves: ${gameBoard.moves}")
Log.d("CandyGame", "Game status: Win=$_gameWon, Over=$_gameOver")
```

## 📊 Test Coverage Metrics

Aim for:
- **Game Logic**: 100% coverage (critical for gameplay)
- **UI Interactions**: 80%+ coverage (test main paths)
- **Edge Cases**: 90%+ coverage (prevent bugs)

## ✅ Final Checklist Before Release

- [ ] All smoke tests pass
- [ ] All edge cases handled
- [ ] No crashes on any device
- [ ] Performance acceptable
- [ ] Battery usage normal
- [ ] Memory leaks checked
- [ ] Code reviewed
- [ ] Documentation complete
- [ ] Game is fun and playable!

---

**Testing is crucial for game quality! Thoroughly test before sharing with others.** 🎮✅

