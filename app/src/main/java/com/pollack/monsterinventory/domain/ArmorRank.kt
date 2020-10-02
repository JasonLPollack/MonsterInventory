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

@Serializable(with=ArmorRank.ArmorRankSerializer::class)
enum class ArmorRank {
    UNKNOWN,
    LOW,
    HIGH,
    MASTER;


    @Serializer(forClass = ArmorRank::class)
    object ArmorRankSerializer {
        override val descriptor: SerialDescriptor
            get() = PrimitiveSerialDescriptor("ArmorRankSerializer", PrimitiveKind.STRING)

        override fun deserialize(decoder: Decoder): ArmorRank {
            val rank = decoder.decodeString()

            return when (rank) {
                "low" -> LOW
                "high" -> HIGH
                "master" -> MASTER
                else -> {
                    Log.v(TAG, "Found unknown armor rank: $rank")
                    UNKNOWN
                }
            }
        }

        override fun serialize(encoder: Encoder, value: ArmorRank) {
            //We don't need this
        }

    }
}
