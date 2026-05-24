package com.leansoft.draw.drawart.utils

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.leansoft.draw.drawart.App
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.suspendCancellableCoroutine

object GlideUtils {
    suspend fun loadBitmap(
        path: Any
    ): Bitmap? {
        val deferred = CompletableDeferred<Bitmap?>()
        var bitmap: Bitmap? = null
        Glide.with(App.instance)
            .asBitmap()  // Yêu cầu trả về Bitmap
            .downsample(DownsampleStrategy.AT_MOST)
            .load(path)
            .override(1920, 1920)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    bitmap = resource
                    deferred.complete(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {}

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    deferred.complete(null)
                }
            })
        deferred.await()
        return bitmap
    }
}