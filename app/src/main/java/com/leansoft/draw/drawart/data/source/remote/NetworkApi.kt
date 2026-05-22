package com.leansoft.draw.drawart.data.source.remote

import com.leansoft.draw.drawart.data.source.remote.dto.CategoriesDto
import com.leansoft.draw.drawart.data.source.remote.response.ApiDataResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface NetworkApi {
    @GET
    suspend fun getCategories(
        @Url url: String
    ): Response<ApiDataResponse<CategoriesDto>>
}