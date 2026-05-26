package com.leansoft.draw.drawart.presentation.viewmodel

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import coil.request.ImageRequest
import com.leansoft.draw.drawart.base.BaseViewModel
import com.leansoft.draw.drawart.data.source.local.database.dao.MyProjectAnimationDao
import com.leansoft.draw.drawart.data.source.local.database.entity.FrameImageEntity
import com.leansoft.draw.drawart.data.source.local.database.entity.ProjectEntity
import com.leansoft.draw.drawart.data.source.local.pref.PreferenceHelper
import com.leansoft.draw.drawart.domain.model.AnimationDetail
import com.leansoft.draw.drawart.domain.model.AnimationModel
import com.leansoft.draw.drawart.domain.model.AnimationPack
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

    private var _itemAnimSelected = MutableLiveData<AnimationDetail?>()
    val itemAnimSelected = _itemAnimSelected

    private val _animations = MutableLiveData<List<AnimationPack>>()
    val animations = _animations


    private val imageLoader by lazy { ImageLoader(context) }

    init {
        loadAnimations()
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

    fun setItemAnimSelected(item: AnimationDetail) {
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

    fun loadAnimations() {

        viewModelScope.launch(Dispatchers.IO) {

            val result = mutableListOf<AnimationPack>()

            val levels = context.assets.list("")
                ?.filter { !it.contains(".") }
                ?: return@launch

            levels.forEach { level ->

                val categories = context.assets.list(level)
                    ?.filter { !it.contains(".") }
                    ?: return@forEach

                categories.forEach { category ->

                    val animDetails = mutableListOf<AnimationDetail>()

                    val anims = context.assets.list("$level/$category")
                        ?.filter { !it.contains(".") }
                        ?: return@forEach

                    anims.forEach { anim ->

                        val framePaths =
                            context.assets.list("$level/$category/$anim")
                                ?.sorted()
                                ?.map { "$level/$category/$anim/$it" }
                                ?: emptyList()

                        animDetails.add(
                            AnimationDetail(
                                nameAnim = anim,
                                listFrame = framePaths
                            )
                        )
                    }

                    result.add(
                        AnimationPack(
                            level = level,
                            category = category,
                            animation = animDetails
                        )
                    )
                }
            }

            withContext(Dispatchers.Main) {
                _animations.value = result
            }
        }
    }
}