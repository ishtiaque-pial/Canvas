package com.example.canvas.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.Rect
import android.os.Build
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat

fun Bitmap.rotateBitmap(angle: Float): Bitmap {
    val matrix = Matrix()
    matrix.postRotate(angle)
    return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
}

fun getBitmapFromVectorDrawable(context: Context, drawableId: Int): Bitmap {
    var drawable = ContextCompat.getDrawable(context, drawableId)
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
        drawable = DrawableCompat.wrap(drawable!!).mutate()
    }
    val bitmap = Bitmap.createBitmap(
        drawable!!.intrinsicWidth,
        drawable.intrinsicHeight, Bitmap.Config.ARGB_8888
    )
    val canvas = android.graphics.Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)
    return bitmap
}

fun calculateSmoothStop(progress: Float, repetitions: Int): Double {
    var result = 1.0
    repeat(repetitions) {
        result *= (1 - progress)
    }
    return 1 - result
}


fun drawImageInCanvas(
    canvas: Canvas,
    tmpAngle: Float,
    bitmap: Bitmap,
    mRadius: Int,
    mCenter: Int,
    listSize: Int
) {

    var imgWidth = mRadius / listSize
    imgWidth = (imgWidth / 1.2).toInt()
    val angle = ((tmpAngle + 360f / listSize / 2) * Math.PI / 180).toFloat()
    val x = (mCenter + mRadius / 1.3 / 2 * Math.cos(angle.toDouble())).toInt()
    val y = (mCenter + mRadius / 1.3 / 2 * Math.sin(angle.toDouble())).toInt()
    val rect = Rect(
        x - imgWidth, y - imgWidth,
        x + imgWidth, y + imgWidth
    )

    canvas.nativeCanvas.drawBitmap(bitmap, null, rect, null)
}