package com.androisandro.kotlinpractice2024

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.DecelerateInterpolator

/**
 * Custom view for rendering the candy game board
 */
class CandyView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {
    private var gameViewModel: GameViewModel? = null
    private var candySize = 0f
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var selectedCandy: Pair<Int, Int>? = null
    private var currentBoard: Array<Array<Candy>>? = null

    // Animation state
    private val animatedPositions = Array(8) { Array(8) { Pair(0f, 0f) } }
    private val animatedAlpha = Array(8) { Array(8) { 1f } }
    private var isAnimating = false

    private val candyColors = mapOf(
        CandyType.RED to Color.RED,
        CandyType.GREEN to Color.GREEN,
        CandyType.BLUE to Color.BLUE,
        CandyType.YELLOW to Color.YELLOW,
        CandyType.PURPLE to 0xFF9C27B0.toInt(),
        CandyType.ORANGE to Color.rgb(255, 165, 0),
        CandyType.EMPTY to Color.TRANSPARENT
    )

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        candySize = (w / 8f)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val board = currentBoard ?: return
        if (board.size != 8 || board[0].size != 8) return // Defensive check

        for (row in 0..7) {
            for (col in 0..7) {
                val candy = board[row][col]
                val x = animatedPositions[row][col].first.takeIf { isAnimating } ?: col * candySize
                val y = animatedPositions[row][col].second.takeIf { isAnimating } ?: row * candySize

                // Draw background
                paint.color = Color.LTGRAY
                canvas.drawRect(x, y, x + candySize, y + candySize, paint)

                // Draw candy
                paint.color = candyColors[candy.type] ?: Color.GRAY
                paint.alpha = (animatedAlpha[row][col] * 255).toInt()
                canvas.drawCircle(x + candySize / 2, y + candySize / 2, candySize / 2 - 4, paint)
                paint.alpha = 255

                // Draw selection highlight
                if (selectedCandy == Pair(row, col)) {
                    paint.color = Color.WHITE
                    paint.style = Paint.Style.STROKE
                    paint.strokeWidth = 4f
                    canvas.drawCircle(
                        x + candySize / 2,
                        y + candySize / 2,
                        candySize / 2 - 2,
                        paint
                    )
                    paint.style = Paint.Style.FILL
                }
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val col = (event.x / candySize).toInt()
            val row = (event.y / candySize).toInt()

            if (row in 0..7 && col in 0..7 && currentBoard != null) {
                gameViewModel?.onCandyClicked(row, col)
                selectedCandy = gameViewModel?.getSelectedCandy()
                invalidate()
                performClick() // Accessibility
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }

    fun setViewModel(viewModel: GameViewModel) {
        gameViewModel = viewModel
        // Store initial board state
        currentBoard = viewModel.board.value
        invalidate()
    }

    fun updateBoard(newBoard: Array<Array<Candy>>) {
        currentBoard = newBoard
        // Reset animation state
        for (row in 0..7) for (col in 0..7) {
            animatedPositions[row][col] = Pair(col * candySize, row * candySize)
            animatedAlpha[row][col] = 1f
        }
        invalidate()
    }

    fun updateSelectedCandy(candy: Pair<Int, Int>?) {
        selectedCandy = candy
        invalidate()
    }

    fun animateSwap(from: Pair<Int, Int>, to: Pair<Int, Int>, onEnd: () -> Unit) {
        isAnimating = true
        val (row1, col1) = from
        val (row2, col2) = to
        val startX1 = col1 * candySize
        val startY1 = row1 * candySize
        val endX1 = col2 * candySize
        val endY1 = row2 * candySize
        val startX2 = col2 * candySize
        val startY2 = row2 * candySize
        val endX2 = col1 * candySize
        val endY2 = row1 * candySize
        val anim1 = ValueAnimator.ofFloat(0f, 1f)
        anim1.duration = 250
        anim1.interpolator = DecelerateInterpolator()
        anim1.addUpdateListener {
            val t = it.animatedValue as Float
            animatedPositions[row1][col1] = Pair(startX1 + (endX1 - startX1) * t, startY1 + (endY1 - startY1) * t)
            animatedPositions[row2][col2] = Pair(startX2 + (endX2 - startX2) * t, startY2 + (endY2 - startY2) * t)
            invalidate()
        }
        anim1.addListener(object : Animator.AnimatorListener {
            override fun onAnimationEnd(animation: Animator?) {
                animatedPositions[row1][col1] = Pair(endX1, endY1)
                animatedPositions[row2][col2] = Pair(endX2, endY2)
                isAnimating = false
                invalidate()
                onEnd()
            }
            override fun onAnimationStart(animation: Animator?) {}
            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationRepeat(animation: Animator?) {}
        })
        anim1.start()
    }

    fun animateMatchFade(matches: Set<Pair<Int, Int>>, onEnd: () -> Unit) {
        isAnimating = true
        val anim = ValueAnimator.ofFloat(1f, 0f)
        anim.duration = 300
        anim.addUpdateListener {
            val alpha = it.animatedValue as Float
            for ((row, col) in matches) {
                animatedAlpha[row][col] = alpha
            }
            invalidate()
        }
        anim.addListener(object : Animator.AnimatorListener {
            override fun onAnimationEnd(animation: Animator?) {
                for ((row, col) in matches) {
                    animatedAlpha[row][col] = 1f
                }
                isAnimating = false
                invalidate()
                onEnd()
            }
            override fun onAnimationStart(animation: Animator?) {}
            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationRepeat(animation: Animator?) {}
        })
        anim.start()
    }

    fun animateDrop(drops: List<Pair<Pair<Int, Int>, Pair<Int, Int>>>, onEnd: () -> Unit) {
        isAnimating = true
        val anim = ValueAnimator.ofFloat(0f, 1f)
        anim.duration = 300
        anim.addUpdateListener {
            val t = it.animatedValue as Float
            for ((from, to) in drops) {
                val (row1, col1) = from
                val (row2, col2) = to
                val startY = row1 * candySize
                val endY = row2 * candySize
                animatedPositions[row2][col2] = Pair(col2 * candySize, startY + (endY - startY) * t)
            }
            invalidate()
        }
        anim.addListener(object : Animator.AnimatorListener {
            override fun onAnimationEnd(animation: Animator?) {
                for ((_, to) in drops) {
                    val (row, col) = to
                    animatedPositions[row][col] = Pair(col * candySize, row * candySize)
                }
                isAnimating = false
                invalidate()
                onEnd()
            }
            override fun onAnimationStart(animation: Animator?) {}
            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationRepeat(animation: Animator?) {}
        })
        anim.start()
    }
}
