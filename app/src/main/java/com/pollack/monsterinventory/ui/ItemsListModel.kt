package com.pollack.monsterinventory.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pollack.monsterinventory.domain.ArmorPart
import com.pollack.util.TAG
import kotlinx.coroutines.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import java.net.URL


sealed class ArmorDataState
class ArmorDataUninitialized: ArmorDataState()
class ArmorDataError(val error: Throwable): ArmorDataState()
class ArmorDataPopulated(val items: List<ArmorPart>) : ArmorDataState()

class ItemsListModel : ViewModel(), CoroutineScope by CoroutineScope(Job() + Dispatchers.IO) {
    companion object {
        val ARMOR_URL = "https://mhw-db.com/armor"
    }

    val armorDataState = MutableLiveData<ArmorDataState>(ArmorDataUninitialized())
    val filterBy = MutableLiveData<String>()

    fun loadItems() {
        //Sanity check. Don't load if we already have data
        if (armorDataState.value is ArmorDataPopulated) return

        val url = URL(ARMOR_URL)

        launch {
            try {
                val armorJSON  = url.readText()
                Log.v(TAG, "Read JSON length ${armorJSON.length} from armor repository")

                val jsonPartsParser = Json {
                    ignoreUnknownKeys = true
                }
                val parts = jsonPartsParser.decodeFromString(ListSerializer(ArmorPart.serializer()), armorJSON)

                Log.v(TAG, "Have ${parts.size} parts")

                val ranks = parts.mapTo(HashSet()) {it.rank}
                val types = parts.mapTo(HashSet()) {it.type}
                Log.v(TAG, "Ranks: ${ranks.joinToString(", ")}")
                Log.v(TAG, "Types: ${types.joinToString(", ")}")
                val maxSlots = parts.maxOf { it.numSlots }
                Log.v(TAG, "Max Slots: $maxSlots")
                armorDataState.postValue(ArmorDataPopulated(parts))

            } catch (t: Throwable) {
                Log.v(TAG, "Error reading from $ARMOR_URL", t)
                armorDataState.postValue(ArmorDataError(t))
            }

        }
    }
}