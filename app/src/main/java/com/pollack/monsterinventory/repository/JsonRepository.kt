package com.pollack.monsterinventory.repository

import android.util.Log
import com.pollack.util.TAG
import java.net.URL

class JsonRepository {

    fun loadJsonAtURL(jsonURL: URL) : String? {
        try {
            val json = jsonURL.readText()
            Log.v(TAG, "Read JSON length ${json.length} from repository at $jsonURL")
            return json
        } catch (t: Throwable) {
            Log.v(TAG, "Error reading from $jsonURL", t)
            return null
        }
    }

}