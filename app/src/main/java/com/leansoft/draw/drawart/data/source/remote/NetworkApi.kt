package com.leansoft.draw.drawart.data.source.remote

import com.leansoft.draw.drawart.data.source.remote.dto.CategoriesDto
import retrofit2.http.GET

interface NetworkApi {

    @GET
    suspend fun getCategories(): List<CategoriesDto>
}