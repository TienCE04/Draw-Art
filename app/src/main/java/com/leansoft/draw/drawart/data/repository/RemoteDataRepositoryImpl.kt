package com.leansoft.draw.drawart.data.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.leansoft.draw.drawart.data.mapper.toDomain
import com.leansoft.draw.drawart.data.source.remote.FirebaseMgr
import com.leansoft.draw.drawart.data.source.remote.config.ConfigUtils
import com.leansoft.draw.drawart.data.source.remote.dto.CategoriesDto
import com.leansoft.draw.drawart.data.source.remote.dto.FrameDto
import com.leansoft.draw.drawart.domain.model.AnimationModel
import com.leansoft.draw.drawart.domain.model.CategoryGroupModel
import com.leansoft.draw.drawart.domain.model.FrameModel
import com.leansoft.draw.drawart.domain.repository.RemoteDataRepository
import com.leansoft.draw.drawart.utils.Either
import com.leansoft.draw.drawart.utils.Failure
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class RemoteDataRepositoryImpl @Inject constructor(
    private val firebaseMgr: FirebaseMgr,
    @ApplicationContext context: Context
) : RemoteDataRepository {

    //load from firebase | backend
    private var categories: List<CategoryGroupModel> = emptyList()

    override suspend fun getCategoryData(): Either<Failure, List<CategoryGroupModel>> {
        if (!categories.isEmpty()) {
            return Either.Right(categories)
        }
        return try {
            val messageJson =
                firebaseMgr.remoteConfig.getString(ConfigUtils.KEY_CONFIG_CATE_DATA_TYPE)
            //convert
            val type = object : TypeToken<List<CategoriesDto>>() {}.type
            val cloudList = Gson().fromJson<List<CategoriesDto>>(messageJson, type)
            categories = cloudList.map { it.toDomain() }
            Either.Right(categories)

        } catch (e: Exception) {
            Either.Left(Failure.ServerError(message = e.message))
        }
    }


    override suspend fun getListFrameTemp(): Either<Failure, List<FrameModel>> {
        if (categories.isEmpty()) {
            return Either.Left(Failure.ServerError(message = "Categories is empty"))
        }
        val data = categories[0].data[0].listFrame
        return Either.Right(data)
    }
}