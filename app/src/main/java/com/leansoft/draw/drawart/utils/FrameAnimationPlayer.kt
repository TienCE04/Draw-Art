package com.leansoft.draw.drawart.utils

import android.animation.ValueAnimator
import android.graphics.Bitmap
import android.os.SystemClock
import android.view.animation.LinearInterpolator
import com.leansoft.draw.drawart.presentation.ui.custom_view.AnimationView

class FrameAnimationPlayer {
    private var animator: ValueAnimator? = null

    fun startAnimation(
        imageView: AnimationView,
        frames: List<Bitmap>,
        fps: Int = 24
    ) {
        if (frames.isEmpty()) return

        val frameDuration = 1000L / fps

        val startTime = SystemClock.uptimeMillis()

        animator?.cancel()


        animator = ValueAnimator.ofFloat(0f, 1f).apply {
            duration = Long.MAX_VALUE

            repeatCount = ValueAnimator.INFINITE

            interpolator = LinearInterpolator()

            addUpdateListener {
                val elapsed = SystemClock.uptimeMillis() - startTime

                val index = ((elapsed / frameDuration) % frames.size).toInt()

                imageView.bitmap = frames[index]
                imageView.invalidate()
            }

            start()
        }
    }

    fun stop() {
        animator?.cancel()
    }
}