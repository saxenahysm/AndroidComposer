# Quick Start Guide - Candy Crush Game

## 5-Minute Setup

### Step 1: Open the Project
1. Open Android Studio
2. Open the project: `D:\AndroidStudioProjects\KotlinPractice2024`
3. Wait for Gradle sync to complete

### Step 2: Check Dependencies
The `build.gradle.kts` file already includes all necessary dependencies:
```gradle
implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
```

### Step 3: Run the App
1. Select an emulator or connect a physical device
2. Click Run (or press Shift+F10)
3. App will launch with the main menu

## Game Controls

| Action | Effect |
|--------|--------|
| Tap a candy | Select it (highlighted with white circle) |
| Tap adjacent candy | Swap with selected candy |
| Match 3+ candies | Candies removed, score increases |
| Candies cascade | Fill effect with gravity simulation |

## File Quick Reference

### Core Game Files
```
CandyTypes.kt       - Enum for candy colors (RED, GREEN, BLUE, YELLOW, PURPLE, ORANGE)
Candy.kt            - Data class for individual candies
GameBoard.kt        - Game logic and board management
```

### UI Files  
```
GameViewModel.kt    - MVVM ViewModel for state management
CandyView.kt        - Custom view that renders the game board
GameActivity.kt     - Main game activity
MainActivity.kt     - Menu/launch activity
```

### Utility Files
```
GameAnimations.kt   - Animation helpers
GameUtils.kt        - Game utilities and difficulty configs
```

### Layout Files
```
activity_main.xml   - Menu screen layout
activity_game.xml   - Game screen layout
```

## Game Rules at a Glance

- **Board**: 8×8 grid of candies
- **Goal**: Reach 1000+ points
- **Moves**: 30 moves available
- **Match**: 3+ same-colored candies in a row (horizontal/vertical)
- **Points**: 10 points per matched candy
- **Win**: Score 1000+ before running out of moves
- **Lose**: Run out of moves with score < 1000

## What Makes This Special

✅ **MVVM Architecture** - Clean separation of concerns  
✅ **Reactive UI** - LiveData for automatic updates  
✅ **Smooth Animations** - Coroutine-based cascade effects  
✅ **Custom Rendering** - Canvas-based drawing for performance  
✅ **Scalable Design** - Easy to add features like difficulty levels  

## Common Modifications

### Change Board Size
In `GameBoard.kt`, modify the initialization:
```kotlin
class GameBoard(val rows: Int = 10, val cols: Int = 10)  // Was 8x8
```

### Change Score Goal
In `GameActivity.kt`, modify the game won condition:
```kotlin
if (gameBoard.score >= 500) {  // Was 1000
    _gameWon.value = true
}
```

### Change Initial Moves
In `GameBoard.kt`:
```kotlin
var moves = 50  // Was 30
```

### Add More Candy Types
1. In `CandyTypes.kt`: Add to enum
2. In `GameBoard.kt`: Add to candyTypes list
3. In `CandyView.kt`: Add color mapping

## Debugging Tips

### View Logcat for Errors
- Window → Logcat in Android Studio
- Filter by package name: `kotlinpractice2024`

### Common Issues & Fixes

**"Activity not found" error**
- Ensure GameActivity is declared in AndroidManifest.xml ✓

**Touch events not registering**
- Check CandyView initialization in GameActivity ✓

**Game board not displaying**
- Verify activity_game.xml references CandyView correctly ✓

**Gradle sync fails**
- File → Sync Now
- Or: Build → Clean Project → Rebuild Project

## Performance Notes

- Game runs smoothly on Android 10+ (API 29+)
- Target SDK: 33 (Android 13)
- Compile SDK: 34 (Android 14)
- Min SDK: 29 (Android 10)

## Next Steps

1. **Run and Play** - Get familiar with the game mechanics
2. **Read IMPLEMENTATION_GUIDE.md** - Understand the architecture
3. **Explore the Code** - Read comments in each Kotlin file
4. **Modify and Extend** - Add your own features
5. **Enhance UI** - Add colors, animations, sound effects

## Feature Ideas to Implement

**Easy:**
- [ ] Change board colors
- [ ] Add difficulty selector screen
- [ ] Display high score

**Medium:**
- [ ] Add sound effects
- [ ] Smooth animations for cascades
- [ ] Pause/resume functionality
- [ ] Undo last move

**Hard:**
- [ ] Power-ups (bomb, dynamite)
- [ ] Special candy combinations
- [ ] Timed mode
- [ ] Persistent storage with SharedPreferences

## Testing Checklist

- [ ] Game starts properly
- [ ] Can select and swap candies
- [ ] Matches are detected correctly
- [ ] Gravity effect works
- [ ] Score updates correctly
- [ ] Moves decrement properly
- [ ] Win condition triggers at 1000 points
- [ ] Lose condition triggers at 0 moves
- [ ] New Game button resets everything
- [ ] Works on different screen sizes

## Project Statistics

| Metric | Value |
|--------|-------|
| Kotlin Files | 8 |
| Layout Files | 2 |
| Lines of Code | ~800 |
| Max Board Size | 8×8 |
| Candy Types | 6 |
| Game Duration | 5-10 min |

---

**Happy Gaming! 🎮**

For detailed information, see:
- `CANDY_CRUSH_GAME_README.md` - Feature overview
- `IMPLEMENTATION_GUIDE.md` - Architecture & deep dive

