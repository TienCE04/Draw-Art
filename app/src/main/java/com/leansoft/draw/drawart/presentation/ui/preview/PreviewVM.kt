package com.leansoft.draw.drawart.presentation.ui.preview

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.LruCache
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.leansoft.draw.drawart.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PreviewVM @Inject constructor(
    @ApplicationContext private val context: Context
) : BaseViewModel() {

    //limit 1/8 ram app duoc cap
    private val frameCache = object : LruCache<String, Bitmap>(
        (Runtime.getRuntime().maxMemory() / 1024 / 8).toInt()
    ) {
        override fun sizeOf(key: String, value: Bitmap): Int {
            return value.byteCount / 1024
        }
    }

    private var _stateLoadBm=MutableLiveData<Boolean>()
    val stateLoadBm=_stateLoadBm

    fun preloadBitmap(listFrame: List<String>) {
        viewModelScope.launch(Dispatchers.IO) {
            listFrame.forEach { path ->
                if (frameCache.get(path) != null) return@launch
                val bitmap = decodeBitmapFromAssets(path)
                if (bitmap != null) {
                    frameCache.put(path, bitmap)
                }
            }
            withContext(Dispatchers.Main){
                _stateLoadBm.value=true
            }
        }
    }

    fun getFrame(path: String): Bitmap? {
        return frameCache.get(path)
    }

    private fun decodeBitmapFromAssets(path: String): Bitmap? {
        return try {
            val options = BitmapFactory.Options().apply {
                inPreferredConfig =
                    Bitmap.Config.RGB_565
            }

            val bitmap = context.assets
                .open(path)
                .use {
                    BitmapFactory.decodeStream(
                        it,
                        null,
                        options
                    )
                }
            bitmap
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}