package com.pollack.monsterinventory.domain

import android.content.res.Resources
import kotlinx.serialization.PrimitiveDescriptor
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.internal.StringDescriptor

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
            return when (decoder.decodeString()) {
                "low" -> LOW
                "high" -> HIGH
                "master" -> MASTER
                else -> UNKNOWN
            }
        }

        override fun serialize(encoder: Encoder, value: ArmorRank) {
            //We don't need this
        }

    }
}

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
            return when (decoder.decodeString()) {
                "head" -> HEAD
                "chest" -> CHEST
                "gloves" -> GLOVES
                "legs" -> LEGS
                "waist" -> WAIST
                else -> UNKNOWN
            }
        }

        override fun serialize(encoder: Encoder, value: ArmorType) {
            //We don't need this
        }

    }

}

@Serializable
data class ArmorPart(
    val id: Int,
    val name: String,
    val rank: ArmorRank,
    val defense: Defense,
    val slots: List<Slot>,
    val type: ArmorType, //String,
    val skills: List<Skill>,
    val assets: Assets?
) {
    @Serializable
    class Defense(val base: Int, val max: Int, val augmented: Int)

    @Serializable
    class Slot(val rank: Int)

    @Serializable
    class Skill(val skillName: String, val description: String)

    @Serializable
    class Assets(val imageMale: String?, val imageFemale: String?)
}
