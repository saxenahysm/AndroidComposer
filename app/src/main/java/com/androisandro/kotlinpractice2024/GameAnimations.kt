package com.androisandro.kotlinpractice2024

import android.content.Context
import android.media.MediaPlayer
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.ScaleAnimation

/**
 * Helper class for sound effects and animations
 */
class GameSoundManager(val context: Context) {
    private var matchSound: MediaPlayer? = null
    private var swapSound: MediaPlayer? = null
    private var gameOverSound: MediaPlayer? = null

    /**
     * Play sound when candies match
     */
    fun playMatchSound() {
        try {
            // In a real app, you would load actual sound files
            // For now, we'll just prepare the infrastructure
            // matchSound?.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Play sound when candies swap
     */
    fun playSwapSound() {
        try {
            // swapSound?.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Play sound when game ends
     */
    fun playGameOverSound() {
        try {
            // gameOverSound?.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun release() {
        matchSound?.release()
        swapSound?.release()
        gameOverSound?.release()
    }
}

/**
 * Helper class for creating animations
 */
object AnimationHelper {
    /**
     * Create a fade out animation
     */
    fun createFadeOutAnimation(duration: Long = 300): Animation {
        val animation = AlphaAnimation(1f, 0f)
        animation.duration = duration
        return animation
    }

    /**
     * Create a pop animation (scale up then scale down)
     */
    fun createPopAnimation(duration: Long = 300): Animation {
        val animationSet = AnimationSet(true)
        animationSet.duration = duration

        val scaleUp = ScaleAnimation(1f, 1.2f, 1f, 1.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        scaleUp.duration = duration / 2

        val scaleDown = ScaleAnimation(1.2f, 0.8f, 1.2f, 0.8f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        scaleDown.startOffset = duration / 2
        scaleDown.duration = duration / 2

        animationSet.addAnimation(scaleUp)
        animationSet.addAnimation(scaleDown)

        return animationSet
    }

    /**
     * Create a fall animation (drop animation)
     */
    fun createFallAnimation(distance: Float, duration: Long = 300): Animation {
        return android.view.animation.TranslateAnimation(0f, 0f, 0f, distance).apply {
            this.duration = duration
        }
    }
}

