package com.leansoft.draw.drawart.presentation.ui.custom_view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PorterDuff
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.graphics.createBitmap
import kotlin.math.hypot
import androidx.core.graphics.withSave

class DrawCustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private lateinit var baseBitmap: Bitmap


    private val actions = mutableListOf<DrawAction>()
    private val redoStack = mutableListOf<DrawAction>()

    var currentTool = ToolType.PEN

    //state

    private val currentPath = Path()

    private var startX = 0f
    private var startY = 0f

    private var currentColor = Color.RED
    private var currentStroke = 10f

    //zoom, pan ,rotate
    private val drawMatrix = Matrix()
    private val inverseMatrix = Matrix()

    private var scaleFactor = 1f
    private var rotationDegrees = 0f

    //cache bitmap
    private lateinit var cacheBitmap: Bitmap
    private lateinit var cacheCanvas: Canvas


    private fun createPaint(): Paint {
        return Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = currentColor
            strokeWidth = currentStroke
            style = Paint.Style.STROKE
            strokeJoin = Paint.Join.ROUND
            strokeCap = Paint.Cap.ROUND
        }
    }

    //init
    fun setup(base: Bitmap) {

        baseBitmap = base

        cacheBitmap = createBitmap(base.width, base.height)

        cacheCanvas = Canvas(cacheBitmap)

        actions.clear()
        redoStack.clear()
    }
    //draw


    override fun onDraw(canvas: Canvas) {

        if (!::baseBitmap.isInitialized) return

        canvas.withSave {

            drawMatrix.reset()

            drawMatrix.postScale(scaleFactor, scaleFactor, width / 2f, height / 2f)

            drawMatrix.postRotate(rotationDegrees, width / 2f, height / 2f)

            concat(drawMatrix)

            drawBitmap(baseBitmap, 0f, 0f, null)
            drawBitmap(cacheBitmap, 0f, 0f, null)

            if (currentTool == ToolType.PEN || currentTool == ToolType.ERASER) {
                drawPath(currentPath, createPaint())
            }

        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {

        val mapped = mapTouch(event.x, event.y)

        val x = mapped.first
        val y = mapped.second

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                startX = x
                startY = y

                when (currentTool) {
                    ToolType.PEN, ToolType.ERASER -> {
                        currentPath.reset()
                        currentPath.moveTo(x, y)
                    }

                    else -> {}
                }
            }

            MotionEvent.ACTION_MOVE -> {
                when (currentTool) {
                    ToolType.PEN, ToolType.ERASER -> {
                        currentPath.lineTo(x, y)
                    }

                    else -> {}
                }
                invalidate()
            }

            MotionEvent.ACTION_UP -> {
                when (currentTool) {
                    ToolType.PEN -> {
                        val paint = createPaint()
                        val finalPath = Path(currentPath)

                        cacheCanvas.drawPath(finalPath, paint)

                        actions.add(
                            DrawAction.PathAction(finalPath, Paint(paint))
                        )
                    }

                    ToolType.ERASER -> {
                        val eraserPaint = Paint().apply {
                            isAntiAlias = true
                            style = Paint.Style.STROKE
                            strokeCap = Paint.Cap.ROUND
                            strokeJoin = Paint.Join.ROUND
                            strokeWidth = currentStroke
                            xfermode = android.graphics.PorterDuffXfermode(
                                android.graphics.PorterDuff.Mode.CLEAR
                            )
                        }
                        val finalPath = Path(currentPath)
                        cacheCanvas.drawPath(finalPath, eraserPaint)

                        actions.add(
                            DrawAction.PathAction(
                                finalPath, eraserPaint
                            )
                        )
                    }

                    ToolType.LINE -> {
                        val paint = createPaint()

                        cacheCanvas.drawLine(
                            startX,
                            startY,
                            x,
                            y, paint
                        )
                        actions.add(
                            DrawAction.LineAction(
                                startX,
                                startY,
                                x,
                                y,
                                Paint(paint)
                            )
                        )
                    }

                    ToolType.RECT -> {
                        val paint = createPaint()

                        val rect = RectF(
                            startX,
                            startY,
                            x, y
                        )

                        cacheCanvas.drawRect(rect, paint)

                        actions.add(
                            DrawAction.RectAction(
                                rect,
                                Paint(paint)
                            )
                        )
                    }

                    ToolType.CIRCLE -> {
                        val paint = createPaint()

                        val radius = hypot(
                            (x - startX),
                            (y - startY)
                        )

                        cacheCanvas.drawCircle(
                            startX,
                            startY,
                            radius, paint
                        )

                        actions.add(
                            DrawAction.CircleAction(
                                startX,
                                startY,
                                radius,
                                Paint(paint)
                            )
                        )
                    }

                    else -> {}
                }

                redoStack.clear()
                currentPath.reset()
                invalidate()
            }
        }
        return true
    }

    //de ve dung cho với ban đầu
    private fun mapTouch(x: Float, y: Float): Pair<Float, Float> {
        drawMatrix.invert(inverseMatrix)

        val pts = floatArrayOf(x, y)
        inverseMatrix.mapPoints(pts)

        return pts[0] to pts[1]
    }

    override fun performClick(): Boolean {
        super.performClick()
        return true
    }

    fun undo() {
        if (actions.isEmpty()) return

        //xoa cai cuoi cung trong actions va cho vao undoStack
        val last = actions.removeAt(actions.lastIndex)
        redoStack.add(last)

        drawAction(last)
    }


    fun redo() {

        if (redoStack.isEmpty()) return

        //lay ra va xoa khoi redoStack cho tro lai action
        val last = redoStack.removeAt(redoStack.lastIndex)
        actions.add(last)

        drawAction(last)
    }

    private fun drawAction(action: DrawAction) {
        when (action) {
            is DrawAction.PathAction ->
                cacheCanvas.drawPath(action.path, action.paint)

            is DrawAction.LineAction ->
                cacheCanvas.drawLine(
                    action.startX,
                    action.startY,
                    action.endX,
                    action.endY,
                    action.paint
                )

            is DrawAction.RectAction ->
                cacheCanvas.drawRect(action.rect, action.paint)

            is DrawAction.CircleAction ->
                cacheCanvas.drawCircle(
                    action.centerX,
                    action.centerY,
                    action.radius,
                    action.paint
                )

            is DrawAction.TextAction ->
                cacheCanvas.drawText(
                    action.text,
                    action.x,
                    action.y,
                    action.paint
                )
        }
    }

    private fun redrawAll() {
        cacheCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)

        actions.forEach { action ->

            when (action) {

                is DrawAction.PathAction -> {

                    cacheCanvas.drawPath(
                        action.path,
                        action.paint
                    )
                }

                is DrawAction.LineAction -> {

                    cacheCanvas.drawLine(
                        action.startX,
                        action.startY,
                        action.endX,
                        action.endY,
                        action.paint
                    )
                }

                is DrawAction.RectAction -> {

                    cacheCanvas.drawRect(
                        action.rect,
                        action.paint
                    )
                }

                is DrawAction.CircleAction -> {

                    cacheCanvas.drawCircle(
                        action.centerX,
                        action.centerY,
                        action.radius,
                        action.paint
                    )
                }

                is DrawAction.TextAction -> {

                    cacheCanvas.drawText(
                        action.text,
                        action.x,
                        action.y,
                        action.paint
                    )
                }
            }
        }
    }
}

enum class ToolType {
    PEN,
    LINE,
    RECT,
    CIRCLE,
    TEXT,
    ERASER
}