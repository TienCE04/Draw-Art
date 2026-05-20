package com.leansoft.draw.drawart.utils.ext

import android.content.Context
import android.net.ConnectivityManager
import android.net.Uri
import android.util.TypedValue
import androidx.core.content.ContextCompat
import java.io.File

val Context.connectivityManager: ConnectivityManager
    get() = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

fun Context.dpToPx(dp: Float): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp,
        resources.displayMetrics
    ).toInt()
}

fun Context.spToPx(sp: Float): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        sp,
        resources.displayMetrics
    )
}

fun Context.uriToFile(uri: Uri, fileName: String = "temp_image.jpg"): File? {
    return try {
        val inputStream = contentResolver.openInputStream(uri) ?: return null
        val tempFile = File(cacheDir, fileName)

        tempFile.outputStream().use { output ->
            inputStream.copyTo(output)
        }

        tempFile
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}