package com.leansoft.draw.drawart.presentation.viewmodel

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import coil.request.ImageRequest
import com.leansoft.draw.drawart.base.BaseViewModel
import com.leansoft.draw.drawart.data.source.local.database.dao.MyProjectAnimationDao
import com.leansoft.draw.drawart.data.source.local.database.entity.FrameImageEntity
import com.leansoft.draw.drawart.data.source.local.database.entity.ProjectEntity
import com.leansoft.draw.drawart.data.source.local.pref.PreferenceHelper
import com.leansoft.draw.drawart.domain.model.AnimationModel
import com.leansoft.draw.drawart.domain.model.CategoryGroupModel
import com.leansoft.draw.drawart.domain.model.FrameModel
import com.leansoft.draw.drawart.domain.repository.RemoteDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: RemoteDataRepository,
    private val dao: MyProjectAnimationDao,
    private val preference: PreferenceHelper,
    @ApplicationContext private val context: Context
) : BaseViewModel() {

    private var _categories = MutableLiveData<List<CategoryGroupModel>>()
    val categories = _categories

    private var _itemAnimSelected = MutableLiveData<AnimationModel?>()
    val itemAnimSelected = _itemAnimSelected

    private val _preBmFrameModels = MutableLiveData<List<FrameModel>>()
    val preBmFrameModels = _preBmFrameModels

    private val imageLoader by lazy { ImageLoader(context) }

    init {
        viewModelScope.launch {
            val data = repo.getCategoryData()

            data.fold(
                ::handleFailure,
                { _categories.postValue(it) }
            )
        }
    }

    suspend fun createProject(
        name: String,
        thumb: String,
        listFrame: List<FrameModel>
    ) {
        val projectId = dao.insert(
            ProjectEntity(
                nameProject = name,
                thumbNailFirstFrame = thumb,
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis()
            )
        )

        listFrame.forEach { frame ->
            dao.insertFrame(
                FrameImageEntity(
                    projectId = projectId.toInt(),
                    imagePath = frame.urlFrame,
                    createdAt = System.currentTimeMillis()
                )
            )
        }
    }

    fun setItemAnimSelected(item: AnimationModel) {
        _itemAnimSelected.value = item
    }

    fun resetItemAnimSelected() {
        _itemAnimSelected.value = null
    }

    private suspend fun saveBitmapToFile(
        bitmap: Bitmap,
        file: File
    ) {
        withContext(Dispatchers.IO) {
            file.outputStream().use { out ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
            }
        }
    }

    private suspend fun downloadBitmap(url: String): Bitmap {
        val request = ImageRequest.Builder(context)
            .data(url)
            .allowHardware(false)
            .build()

        val result = imageLoader.execute(request)
        return (result.drawable as BitmapDrawable).bitmap
    }

    private suspend fun loadFromLocal(animName: String) = withContext(Dispatchers.IO) {

        val dir = File(context.filesDir, animName)
        val files = dir.listFiles()?.sortedBy { it.name } ?: return@withContext

        val frames = files.mapIndexed { index, file ->
            FrameModel(idFrame = index, urlFrame = file.absolutePath)
        }

        _preBmFrameModels.postValue(frames)

        if (frames.isNotEmpty()) {
            createProject(animName, frames[0].urlFrame, frames)
        }
    }

    private suspend fun cacheFromNetwork(
        animName: String,
        frames: List<FrameModel>
    ) = withContext(Dispatchers.IO) {

        val dir = File(context.filesDir, animName)
        if (!dir.exists()) dir.mkdirs()

        val result = mutableListOf<FrameModel>()

        frames.forEachIndexed { index, frame ->

            val bitmap = downloadBitmap(frame.urlFrame)

            val file = File(dir, "$index.png")

            saveBitmapToFile(bitmap, file)

            result.add(
                FrameModel(idFrame = index, urlFrame = file.absolutePath)
            )
        }

        preference.addAnim(animName)

        _preBmFrameModels.postValue(result)
    }

    fun preLoadBm() {
        viewModelScope.launch {
            val anim = itemAnimSelected.value ?: return@launch
            val animName = anim.nameAnim ?: return@launch
            val frames = anim.listFrame ?: emptyList()

            val isCached = preference.checkAnimName(animName)

            if (isCached) {
                loadFromLocal(animName)
            } else {
                cacheFromNetwork(animName, frames)
            }
        }
    }
}