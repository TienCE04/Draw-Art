package com.leansoft.draw.drawart.data.source.remote.response

data class ApiDataResponse<T>(val success: Boolean, val data: T?, val message: String?) {
}