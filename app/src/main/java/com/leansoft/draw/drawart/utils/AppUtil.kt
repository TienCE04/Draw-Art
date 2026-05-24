package com.leansoft.draw.drawart.utils

import android.content.res.Resources

val Int.dp: Int
    get() = (
            this * Resources.getSystem()
                .displayMetrics.density
            ).toInt()