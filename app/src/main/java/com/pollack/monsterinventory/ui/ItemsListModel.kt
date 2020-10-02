package com.pollack.monsterinventory.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pollack.monsterinventory.domain.ArmorPart
import com.pollack.monsterinventory.repository.JsonRepository
import com.pollack.util.TAG
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import java.net.URL


sealed class ArmorDataState
class ArmorDataUninitialized: ArmorDataState()
class ArmorDataError(): ArmorDataState()
class ArmorDataPopulated(val items: List<ArmorPart>) : ArmorDataState()

class ItemsListModel : ViewModel(), CoroutineScope by CoroutineScope(Job() + Dispatchers.IO) {
    companion object {
        val ARMOR_URL = "https://mhw-db.com/armor"
    }

    //Typically we would inject dependencies such as this one
    val jsonRepository = JsonRepository()

    val armorDataState = MutableLiveData<ArmorDataState>(ArmorDataUninitialized())
    val filterBy = MutableLiveData<String>()

    fun reset() {
        armorDataState.postValue(ArmorDataUninitialized())
    }

    fun loadItems() {
        //Sanity check. Don't load if we already have data
        if (armorDataState.value is ArmorDataPopulated) return

        val armorURL = URL(ARMOR_URL)
        launch {
            val armorJSON  = jsonRepository.loadJsonAtURL(armorURL)
            if (armorJSON != null) {
                Log.v(TAG, "Read JSON length ${armorJSON.length} from armor repository")
                val jsonPartsParser = Json {
                    ignoreUnknownKeys = true
                }
                val parts = jsonPartsParser.decodeFromString(ListSerializer(ArmorPart.serializer()), armorJSON)

                //Take a quick inspection to confirm the values we think we know about
                Log.v(TAG, "Have ${parts.size} parts")
                val ranks = parts.mapTo(HashSet()) {it.rank}
                val types = parts.mapTo(HashSet()) {it.type}
                val skills = parts.maxOf { it.skills.size }
                Log.v(TAG, "Ranks: ${ranks.joinToString(", ")}")
                Log.v(TAG, "Types: ${types.joinToString(", ")}")
                Log.v(TAG, "Max Skills: $skills")

                armorDataState.postValue(ArmorDataPopulated(parts))
            } else {
                Log.v(TAG, "Error reading from $ARMOR_URL")
                armorDataState.postValue(ArmorDataError())
            }
        }
    }

    fun getItemById(itemId: Int) : ArmorPart? {
        val currentState = armorDataState.value
        val currentList = if (currentState is ArmorDataPopulated) currentState.items else return null
        return currentList.firstOrNull { it.id == itemId }
    }
}