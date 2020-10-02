package com.pollack.monsterinventory.domain

import com.pollack.monsterinventory.R

val ArmorPart.imageResource get() = when (type) {
    "head" -> R.drawable.ic_head
    "legs" -> R.drawable.ic_legs
    "chest" -> R.drawable.ic_chest
    "gloves" -> R.drawable.ic_gloves
    "waist" -> R.drawable.ic_waist
    else -> R.drawable.ic_baseline_device_unknown_32 //Placeholder for unknown part type
}

val ArmorPart.rankText get() = rank.toUpperCase()
val ArmorPart.minDefenseText get() = "${defense.base}+"
val ArmorPart.slotRanks get() = slots.map {it.rank}
