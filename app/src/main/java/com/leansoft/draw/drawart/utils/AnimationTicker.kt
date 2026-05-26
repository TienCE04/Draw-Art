package com.leansoft.draw.drawart.utils

import android.graphics.Bitmap
import com.leansoft.draw.drawart.presentation.ui.home.ListCateAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

object AnimationTicker {

    private val holders = mutableSetOf<ListCateAdapter.AnimViewHolder>()

    private var job: Job? = null

    private var frame = 0

    private const val FPS = 12L

    private const val FRAME_DELAY = 1000L / FPS

    fun register(holder: ListCateAdapter.AnimViewHolder) {

        holders.add(holder)

        if (job == null) {
            start()
        }
    }

    fun unregister(holder: ListCateAdapter.AnimViewHolder) {

        holders.remove(holder)

        if (holders.isEmpty()) {
            stop()
        }
    }

    private fun start() {

        job = CoroutineScope(Dispatchers.Main).launch {

            while (isActive) {

                holders.forEach {
                    it.render(frame)
                }

                frame++

                delay(FRAME_DELAY)
            }
        }
    }

    private fun stop() {

        job?.cancel()
        job = null
    }

    private val cache =
        mutableMapOf<String, Bitmap>()

    fun get(path: String): Bitmap? {
        return cache[path]
    }

    fun put(path: String, bitmap: Bitmap) {
        cache[path] = bitmap
    }
}