package com.leansoft.draw.drawart.presentation.ui.preview

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
    private var _bitmapCache = MutableLiveData<List<Bitmap>>()
    val bitmapCache = _bitmapCache

    private fun getBitmap(paths: List<String>) {
        viewModelScope.launch(Dispatchers.IO) {
            val options = BitmapFactory.Options().apply {
                inPreferredConfig = Bitmap.Config.RGB_565
            }

            val bitmaps = paths.mapNotNull { path ->
                try {
                    context.assets.open(path).use {
                        BitmapFactory.decodeStream(it, null, options)
                    }
                } catch (e: Exception) {
                    null
                }
            }
            withContext(Dispatchers.Main) {
                _bitmapCache.value = bitmaps
            }
        }
    }
}