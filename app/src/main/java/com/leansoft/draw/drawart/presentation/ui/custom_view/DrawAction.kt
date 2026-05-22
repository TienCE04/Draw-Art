package com.leansoft.draw.drawart.presentation.ui.custom_view

import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF

sealed class DrawAction {
    data class PathAction(
        val path: Path,
        val paint: Paint
    ): DrawAction()

    data class LineAction(
        val startX: Float,
        val startY: Float,
        val endX: Float,
        val endY: Float,
        val paint: Paint
    ): DrawAction()

    data class RectAction(
        val rect: RectF,
        val paint: Paint
    ): DrawAction()

    data class CircleAction(
        val centerX: Float,
        val centerY: Float,
        val radius: Float,
        val paint: Paint
    ): DrawAction()

    data class TextAction(
        val text: String,
        val x: Float,
        val y: Float,
        val paint: Paint
    ): DrawAction()
}