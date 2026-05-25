package com.leansoft.draw.drawart.utils.ext

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat


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
fun View.cancelTransition() {
    transitionName = null
}

fun View.isVisible() = this.visibility == View.VISIBLE

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.visibleOrGone(isShow: Boolean) {
    this.visibility = if (isShow) View.VISIBLE else View.GONE
}

fun View.visibleOrInvisible(isShow: Boolean) {
    this.visibility = if (isShow) View.VISIBLE else View.INVISIBLE
}

fun TextView.setTextColorCompat(resId: Int) {
    setTextColor(ContextCompat.getColor(context, resId))
}

fun View.setPaddingDp(all: Int) {
    val px = (all * resources.displayMetrics.density).toInt()
    setPadding(px, px, px, px)
}