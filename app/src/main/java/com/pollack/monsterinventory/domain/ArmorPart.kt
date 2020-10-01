package com.pollack.monsterinventory.domain

import kotlinx.serialization.Serializable

@Serializable
data class ArmorPart(
    val id: Int,
    val name: String,
    val rank: String,
    val defense: Defense,
    val slots: List<Slot>,
    val type: String
) {
    @Serializable
    class Defense(val base: Int, val max: Int, val augmented: Int)

    @Serializable
    class Slot(val rank: Int)

    val numSlots get() = slots.size
}
