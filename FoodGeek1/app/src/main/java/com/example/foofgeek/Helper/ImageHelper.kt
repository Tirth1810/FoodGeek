package com.example.foofgeek.Helper

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import java.io.ByteArrayOutputStream

class ImageHelper {
    fun getByteArrayFromDrawable(d: Drawable): ByteArray {
        val bitmap = (d as BitmapDrawable).bitmap
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }

    fun getByteArrayFromBitmap(d: Bitmap?): ByteArray {

        // convert bitmap to byte
        val stream = ByteArrayOutputStream()
        d!!.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }

    fun getBitmapFromByteArray(b: ByteArray?): Bitmap {
        return BitmapFactory.decodeByteArray(b, 0, b!!.size)
    }

    fun resizeImage(pBitmap: Bitmap?): Bitmap {
        val height = pBitmap!!.height
        val width = pBitmap.width
        return Bitmap.createScaledBitmap(pBitmap, 350, 200, true)
    }

    fun getThubmail(pBitmap: Bitmap): Bitmap {
        val height = pBitmap.height
        val width = pBitmap.width
        return Bitmap.createScaledBitmap(pBitmap, width * 90 / 90, 90, true)
    }
}