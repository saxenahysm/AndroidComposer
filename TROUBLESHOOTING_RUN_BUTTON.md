# 🔧 TROUBLESHOOTING GUIDE - Run Button Disabled

## Problem: Run Button is Disabled in Android Studio

### Root Cause
The issue was in how the CandyView was observing the ViewModel's LiveData. The original implementation used `observeForever()` which can cause lifecycle issues and crashes.

### What We Fixed

#### 1. **CandyView.kt** 
**Problem**: Was using `observeForever()` to observe board changes directly
**Solution**: Changed to store board in a local property and let GameActivity handle observation

#### 2. **GameViewModel.kt**
**Problem**: Selected candy wasn't exposed as observable LiveData
**Solution**: Added `_selectedCandy` as MutableLiveData to make it properly observable

#### 3. **GameActivity.kt**
**Problem**: Not properly observing and updating the view
**Solution**: Added explicit observation of both board and selected candy LiveData

---

## Changes Made

### GameViewModel.kt
```kotlin
// ADDED: New LiveData for selected candy
private val _selectedCandy = MutableLiveData<Pair<Int, Int>?>()
val selectedCandyLiveData: LiveData<Pair<Int, Int>?> = _selectedCandy

// UPDATED: onCandyClicked to update _selectedCandy
fun onCandyClicked(row: Int, col: Int) {
    // ...
    _selectedCandy.value = selectedCandy  // Update when selection changes
    // ...
}

// UPDATED: resetGame to reset selected candy
fun resetGame() {
    // ...
    _selectedCandy.value = null
    // ...
}
```

### CandyView.kt
```kotlin
// CHANGED: Removed observeForever()
// ADDED: Local property to store current board
private var currentBoard: Array<Array<Candy>>? = null

// ADDED: New methods for updates
fun updateBoard(newBoard: Array<Array<Candy>>) {
    currentBoard = newBoard
    invalidate()
}

fun updateSelectedCandy(candy: Pair<Int, Int>?) {
    selectedCandy = candy
    invalidate()
}
```

### GameActivity.kt
```kotlin
// ADDED: Board observation
gameViewModel.board.observe(this) { board ->
    candyView.updateBoard(board)
}

// ADDED: Selected candy observation
gameViewModel.selectedCandyLiveData.observe(this) { candy ->
    candyView.updateSelectedCandy(candy)
}
```

---

## How to Verify the Fix

### Step 1: Rebuild Project
In Android Studio:
```
Build → Clean Project
Build → Rebuild Project
```

### Step 2: Check for Build Errors
- Look for error messages in the Logcat window
- The Run button should become enabled
- If still disabled, scroll down in the Build window to see error details

### Step 3: Run the App
1. Click the green Run button (Shift+F10)
2. Select your emulator or device
3. App should launch without crashes

### Step 4: Verify Game Works
- App launches to MainActivity menu
- "Play Game" button works
- GameActivity displays with board visible
- Can tap candies
- Can swap candies
- Matches are removed
- Score updates

---

## If Run Button Still Disabled

### Check These:
1. **Java/JDK Issue**
   ```
   File → Project Structure → SDK Location
   Check that SDK is properly set
   ```

2. **Gradle Sync Issues**
   ```
   File → Sync Now
   Wait for it to complete
   ```

3. **Build Cache Issues**
   ```
   Build → Clean Project
   File → Invalidate Caches... → Invalidate and Restart
   ```

4. **Check Build Messages**
   - View → Tool Windows → Build
   - Look for error messages
   - Search for "error:" in the output

5. **Logcat Errors**
   - View → Tool Windows → Logcat
   - Filter by package: `kotlinpractice2024`
   - Look for red error lines

---

## Common Android Studio Issues

### Issue: "Gradle sync failed"
**Solution**: 
- File → Invalidate Caches → Invalidate and Restart
- File → Sync Now
- gradle/wrapper/gradle-wrapper.properties - check gradle version

### Issue: "Unable to resolve symbol 'ViewModelProvider'"
**Solution**: 
- Check that androidx.lifecycle dependencies are in build.gradle.kts
- Run: File → Sync Now

### Issue: "Cannot find symbol 'R'"
**Solution**:
- Build → Clean Project
- Build → Rebuild Project
- File → Invalidate Caches and Restart

### Issue: Emulator Won't Start
**Solution**:
- Tools → AVD Manager
- Click play icon next to an emulator
- Wait for it to fully boot (can take 1-2 minutes)

---

## Terminal Build Method

If Android Studio Run button is still problematic, build from terminal:

```powershell
# Navigate to project
cd D:\AndroidStudioProjects\KotlinPractice2024

# Build APK
.\gradlew.bat assembleDebug

# Check if successful
# APK will be at: app\build\outputs\apk\debug\app-debug.apk

# Install on connected device
adb install app\build\outputs\apk\debug\app-debug.apk

# Launch app
adb shell am start -n com.androisandro.kotlinpractice2024/.MainActivity
```

---

## Expected Behavior After Fix

✅ **Android Studio**
- Run button is enabled (green)
- Build completes successfully
- No red errors in Build window

✅ **App Launch**
- App loads without crashes
- MainActivity menu appears
- "Play Game" button works

✅ **Game**
- GameActivity displays
- 8×8 board visible with colored candies
- Score shows: "Score: 0"
- Moves shows: "Moves: 30"
- Can interact with candies
- Game functions properly

---

## Quick Fix Checklist

- [ ] Run: Build → Clean Project
- [ ] Run: Build → Rebuild Project
- [ ] Check: View → Tool Windows → Build (look for errors)
- [ ] Check: File → Project Structure → SDK Location
- [ ] Try: File → Invalidate Caches... → Invalidate and Restart
- [ ] Try: File → Sync Now
- [ ] Click: Green Run button (Shift+F10)
- [ ] Select: Emulator or device
- [ ] Wait: App to launch and load

---

## Getting More Help

If you're still stuck:

1. **Check Build Output**
   - Scroll to the end of Build window
   - Copy any error messages
   - Search error online

2. **Check Gradle Wrapper**
   - Verify gradle-wrapper.properties version matches your Gradle
   - Try updating: gradle/wrapper/gradle-wrapper.properties

3. **Restart Android Studio**
   - Close Android Studio completely
   - Restart your computer (if needed)
   - Open project again
   - Run Build → Rebuild Project

4. **Nuclear Option**
   ```
   Delete: .gradle folder
   Delete: .idea folder
   File → Invalidate Caches → Invalidate and Restart
   ```

---

## Summary of What Was Fixed

The crash was caused by improper observation of LiveData in a custom View. By:

1. ✅ Removing problematic `observeForever()`
2. ✅ Adding proper LiveData for selected candy
3. ✅ Having the Activity observe and update the View properly
4. ✅ Keeping View state local and refreshing on updates

The game now properly manages state and won't crash on launch! 🎮

---

**Try rebuilding now - it should work!**

