package com.leansoft.draw.drawart.utils

import com.leansoft.draw.drawart.presentation.ui.home.ListCateAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

object AnimationTicker {

    private val listeners = mutableSetOf<(Int) -> Unit>()

    private var job: Job? = null

    private var frame = 0

    private const val FPS = 12L

    private const val FRAME_DELAY = 1000L / FPS

    fun register(listener: (Int) -> Unit) {
        listeners.add(listener)

        if (job == null) start()
    }

    fun unregister(listener: (Int) -> Unit) {
        listeners.remove(listener)

        if (listeners.isEmpty()) stop()
    }

    private fun start() {
        job = CoroutineScope(Dispatchers.Main).launch {
            while (isActive) {
                listeners.forEach { it(frame) }
                frame++
                delay(FRAME_DELAY)
            }
        }
    }

    fun clear(){
        listeners.clear()
        stop()
    }

    private fun stop() {
        job?.cancel()
        job = null
        frame = 0
    }
}