# 🍬 Candy Crush Game - Quick Reference Card

## 🎮 HOW TO PLAY

| Action | Description |
|--------|-------------|
| **Tap Candy** | Select it (white circle) |
| **Tap Adjacent** | Swap with selected candy |
| **Match 3+** | Remove same-colored candies |
| **Score Points** | 10 points per candy matched |
| **Cascade** | Candies fall, new ones fill gaps |

## 🎯 WIN/LOSE

| Condition | Result |
|-----------|--------|
| **Score ≥ 1000** before moves end | ✅ YOU WIN! |
| **Score < 1000** when moves = 0 | ❌ GAME OVER |
| **Moves = 30** at start | Count down each move |

## 🚀 QUICK START

```bash
1. Open Android Studio
2. Click Run (Shift+F10)
3. Tap "Play Game"
4. Match candies!
```

## 📁 KEY FILES

### Game Logic
```
GameBoard.kt       ← Core game engine
  ├── findMatches()       (detects matches)
  ├── removeMatches()     (removes matched candies)
  ├── applyGravity()      (candies fall)
  └── fillEmptySpaces()   (new candies appear)
```

### UI & State
```
GameViewModel.kt   ← MVVM state management
  ├── onCandyClicked()    (user interaction)
  ├── board (LiveData)    (reactive board state)
  ├── score (LiveData)    (reactive score)
  └── moves (LiveData)    (reactive moves)

CandyView.kt       ← Rendering
  ├── onDraw()            (draw board)
  └── onTouchEvent()      (handle touches)

GameActivity.kt    ← UI layout
  └── Displays board + score + moves
```

### Models
```
Candy.kt           ← Individual candy
CandyType.kt       ← 6 color types
GameUtils.kt       ← Helper functions
```

## 🎨 GAME BOARD COLORS

| Color | Type |
|-------|------|
| 🔴 | RED |
| 🟢 | GREEN |
| 🔵 | BLUE |
| 🟡 | YELLOW |
| 🟣 | PURPLE |
| 🟠 | ORANGE |

## 📊 SCORING

| Match | Points |
|-------|--------|
| 3 candies | 30 |
| 4 candies | 40 |
| 5 candies | 50 |
| 6+ candies | 60+ |

## ⚙️ CONFIGURATIONS

### Board Size (default 8×8)
```kotlin
// In GameBoard.kt, line 8:
class GameBoard(val rows: Int = 8, val cols: Int = 8)
```

### Starting Moves (default 30)
```kotlin
// In GameBoard.kt:
var moves = 30
```

### Win Score (default 1000)
```kotlin
// In GameActivity.kt:
if (gameBoard.score >= 1000) _gameWon.value = true
```

### Initial Candies
Random from: RED, GREEN, BLUE, YELLOW, PURPLE, ORANGE

## 🧪 TESTING QUICK CHECKLIST

- [ ] App launches without crashing
- [ ] Can select candies
- [ ] Can swap adjacent candies
- [ ] Matches are detected correctly
- [ ] Candies fall (gravity)
- [ ] New candies fill gaps
- [ ] Score increases correctly
- [ ] Moves decrease per valid swap
- [ ] Win message at 1000+ points
- [ ] Lose message at 0 moves

## 🔍 ALGORITHM OVERVIEW

### Match Detection
```
Check each 3-candy sequence horizontally
Check each 3-candy sequence vertically
Return all matched positions
```
Time: O(n²) - Optimal for 8×8

### Gravity
```
For each column:
  Drop candies to bottom
  Preserve order
```
Time: O(n) - Very efficient

### Cascade
```
While matches exist:
  Remove matches
  Apply gravity
  Fill empty spaces
  Recheck matches
```

## 📚 DOCUMENTATION QUICK LINKS

| Topic | File |
|-------|------|
| How to play | QUICK_START.md |
| Game features | CANDY_CRUSH_GAME_README.md |
| Code architecture | IMPLEMENTATION_GUIDE.md |
| Visual diagrams | ARCHITECTURE_DIAGRAM.md |
| Testing guide | TESTING_GUIDE.md |
| Find anything | DOCUMENTATION_INDEX.md |
| Project overview | README.md |

## 🐛 COMMON ISSUES

| Problem | Solution |
|---------|----------|
| App won't launch | Check AndroidManifest.xml has GameActivity |
| Touch not working | Verify CandyView in activity_game.xml |
| Board not showing | Ensure GameActivity.onCreate() initializes view |
| No cascades | Check findMatches() logic |
| Score not updating | Verify LiveData.value assignments |

## 💡 PRO TIPS

1. **Create larger matches** - 4+ candies = more points
2. **Plan ahead** - Look for cascading opportunities
3. **Use edges** - Candies at edges are easier to match
4. **Watch the cascade** - Multiple matches count!
5. **React quickly** - Only 30 moves to reach 1000 points!

## 🔑 KEYBOARD SHORTCUTS

| Shortcut | Action |
|----------|--------|
| Shift+F10 | Run app |
| F5 | Build & run |
| Ctrl+Shift+A | Find action |
| Ctrl+Alt+L | Format code |
| Ctrl+/ | Toggle comment |

## 📱 DEVICE REQUIREMENTS

| Requirement | Value |
|-------------|-------|
| Min Android | 10 (API 29) |
| Target Android | 13 (API 33) |
| Compile Android | 14 (API 34) |
| Architecture | MVVM |
| Language | Kotlin |

## ✨ FEATURES AT A GLANCE

✅ 8×8 game board
✅ 6 candy types
✅ Touch-based controls
✅ Match detection
✅ Gravity simulation
✅ Cascade effects
✅ Score tracking
✅ Move management
✅ Win/Lose conditions
✅ Game reset
✅ Smooth animations
✅ Responsive design

## 🎓 LEARNING OUTCOMES

After exploring this code, you'll understand:
- ✅ MVVM architecture pattern
- ✅ LiveData reactive programming
- ✅ Kotlin Coroutines
- ✅ Custom View development
- ✅ Game algorithm implementation
- ✅ Android best practices
- ✅ State management
- ✅ Clean code principles

## 📊 PROJECT STATS

```
Code Files:        9 Kotlin files
Layout Files:      2 XML files
Documentation:     8 files
Total LOC:         ~1000 lines
Code Comments:     Extensive
Android APIs:      29-34
Difficulty:        Medium
Playtime:          5-10 minutes/game
Win Score:         1000+ points
Starting Moves:    30
Board Size:        8×8 grid
```

## 🎮 GAME FLOW

```
Start
  ↓
Random Board → User Selects Candy
  ↓
User Swaps Adjacent
  ↓
Match Detected? 
  ├─ No  → Reverse swap
  └─ Yes → Remove, Apply Gravity, Fill, Cascade
  ↓
Score Updated
  ↓
Moves Decremented
  ↓
Check Win/Lose
  ├─ Score ≥ 1000 → YOU WIN ✅
  ├─ Moves = 0 & Score < 1000 → GAME OVER ❌
  └─ Else → Continue Playing
```

## 🚀 GETTING STARTED

1. **Open**: Android Studio
2. **Project**: D:\AndroidStudioProjects\KotlinPractice2024
3. **Sync**: File → Sync Now
4. **Run**: Click green Run button
5. **Play**: Tap "Play Game"

## 💬 WHERE TO GET HELP

- **How to play?** → See "HOW TO PLAY" section above
- **How to run?** → See "GETTING STARTED" section
- **Code question?** → Check IMPLEMENTATION_GUIDE.md
- **Game feature?** → Check CANDY_CRUSH_GAME_README.md
- **Bug or issue?** → Check TESTING_GUIDE.md
- **Can't find?** → Check DOCUMENTATION_INDEX.md

---

**Print this card for quick reference while developing!** 📋

Made with ❤️ in Kotlin | 2026

