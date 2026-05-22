package com.leansoft.draw.drawart.utils.ext

import android.view.View


fun View.safeOnClickListener(
    interval: Long = 3000L,
    onClick: (View?) -> Unit
) {
    setOnClickListener(SafeOnClickListener(onClick = onClick, interval))
}

class SafeOnClickListener(
    private var onClick: (View?) -> Unit,
    private var interval: Long = 300
) : View.OnClickListener {
    private var lastTime = 0L

    override fun onClick(v: View?) {
        if (System.currentTimeMillis() - lastTime < interval) return
        lastTime = System.currentTimeMillis()
        onClick.invoke(v)
    }

}