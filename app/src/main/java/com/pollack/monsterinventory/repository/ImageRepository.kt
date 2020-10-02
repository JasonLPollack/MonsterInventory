package com.pollack.monsterinventory.repository

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.pollack.util.TAG
import java.net.URL

class ImageRepository {

    fun loadImage(imageURL: URL) : Bitmap? {
        try {
            val imageData = imageURL.readBytes()
            val image = BitmapFactory.decodeByteArray(imageData, 0, imageData.size)
            return image
        } catch (t: Throwable) {
            Log.v(TAG, "Failed to load image at $imageURL")
            return null
        }
    }

}