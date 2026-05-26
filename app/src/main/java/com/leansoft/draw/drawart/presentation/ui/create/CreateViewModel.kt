package com.leansoft.draw.drawart.presentation.ui.create

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.leansoft.draw.drawart.base.BaseViewModel
import com.leansoft.draw.drawart.data.source.local.database.dao.MyProjectAnimationDao
import com.leansoft.draw.drawart.domain.model.FrameModel
import com.leansoft.draw.drawart.domain.repository.RemoteDataRepository
import com.leansoft.draw.drawart.utils.GlideUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateViewModel @Inject constructor(
    private val dao: MyProjectAnimationDao,
    private val repository: RemoteDataRepository
) : BaseViewModel() {

    private var _listBitmapTemplate = MutableLiveData<List<Bitmap>>()
    val listBitmapTemplate: LiveData<List<Bitmap>> = _listBitmapTemplate

    private var _listBitmapTemp = MutableLiveData<List<Bitmap>>()
    val listBitmapTemp: LiveData<List<Bitmap>> = _listBitmapTemp

    private var listFrame: List<FrameModel> = emptyList()

    fun getListFrameTemp() {
        viewModelScope.launch {
            val data = repository.getListFrameTemp()
            data.fold(
                ::handleFailure,
                {
                    listFrame = it
                }
            )
        }
    }

    fun convertBitmap() {
        if (listFrame.isEmpty()) {
            return
        }
        if (_listBitmapTemp.value != null) {
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            val listBitmap = listFrame.map {
                GlideUtils.loadBitmap(it.urlFrame)
            }
            _listBitmapTemp.postValue(listBitmap.mapNotNull { it })
        }
    }

}

data class AnimationUiState(
    val currentFrame: Int = 0,
    val isPlaying: Boolean = false,
    val fps: Int = 12
)