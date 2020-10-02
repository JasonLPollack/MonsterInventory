package com.pollack.monsterinventory.domain

import android.util.Log
import com.pollack.util.TAG
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable(with=ArmorType.ArmorTypeSerializer::class)
enum class ArmorType {
    UNKNOWN,
    HEAD,
    CHEST,
    GLOVES,
    LEGS,
    WAIST;

    @Serializer(forClass = ArmorType::class)
    object ArmorTypeSerializer {
        override val descriptor: SerialDescriptor
            get() = PrimitiveSerialDescriptor("ArmorTypeSerializer", PrimitiveKind.STRING)

        override fun deserialize(decoder: Decoder): ArmorType {
            val type = decoder.decodeString()
            return when (type) {
                "head" -> HEAD
                "chest" -> CHEST
                "gloves" -> GLOVES
                "legs" -> LEGS
                "waist" -> WAIST
                else -> {
                    Log.v(TAG, "Found unknown armor type: $type")
                    UNKNOWN
                }
            }
        }

        override fun serialize(encoder: Encoder, value: ArmorType) {
            //We don't need this
        }

    }

}
