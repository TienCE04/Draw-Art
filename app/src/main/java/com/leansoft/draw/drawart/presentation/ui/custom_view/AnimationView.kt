package com.leansoft.draw.drawart.presentation.ui.custom_view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View

class AnimationView(context: Context) : View(context) {
    var bitmap: Bitmap? = null

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        bitmap?.let {
            canvas.drawBitmap(it, 0f, 0f, null)
        }
    }
}