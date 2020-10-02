package com.pollack.monsterinventory.domain

import android.content.Context
import com.pollack.monsterinventory.R

val ArmorPart.imageResource get() = when (type) {
    ArmorType.HEAD -> R.drawable.ic_head
    ArmorType.LEGS -> R.drawable.ic_legs
    ArmorType.CHEST -> R.drawable.ic_chest
    ArmorType.GLOVES -> R.drawable.ic_gloves
    ArmorType.WAIST -> R.drawable.ic_waist
    else -> R.drawable.ic_baseline_device_unknown_32 //Placeholder for unknown part type
}

fun ArmorPart.getRankText(context: Context) : String {
    return context.resources.getString(
        when (rank) {
            ArmorRank.LOW -> R.string.rank_low
            ArmorRank.HIGH -> R.string.rank_high
            ArmorRank.MASTER -> R.string.rank_master
            ArmorRank.UNKNOWN -> R.string.rank_unknown
        }
    )
}

val ArmorPart.minDefenseText get() = "${defense.base}+"
val ArmorPart.slotRanks get() = slots.map {it.rank}
