package com.leansoft.draw.drawart.utils

sealed class Failure {
    data object NetworkConnection : Failure()
    data class ServerError(val code: Int = -1, val message: String?) : Failure()
    data class CloudError(val code: Int = -1, val message: String?) : Failure()
    abstract class FeatureFailure : Failure()
}