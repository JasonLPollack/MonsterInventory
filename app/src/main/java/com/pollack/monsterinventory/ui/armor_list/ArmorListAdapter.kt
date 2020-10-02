package com.pollack.monsterinventory.ui.armor_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.pollack.monsterinventory.R
import com.pollack.monsterinventory.domain.*
import kotlinx.android.synthetic.main.armor_item_view.view.*

typealias ArmorPartSelectedCallback = (ArmorPart)->Unit

//Adapter takes a list of items to display, and a callback function that executes
//when an item is selected.
class ArmorListAdapter(
    private val items: List<ArmorPart>,
    private val selectedCallback: ArmorPartSelectedCallback
): RecyclerView.Adapter<ArmorListAdapter.ArmorViewHolder>() {

    class ArmorViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        companion object {
            val slotIds = arrayOf(R.id.slot_1, R.id.slot_2, R.id.slot_3, R.id.slot_4)
            val slotImageResIds = arrayOf(R.drawable.ic_twotone_looks_1_24, R.drawable.ic_twotone_looks_2_24,
                R.drawable.ic_twotone_looks_3_24, R.drawable.ic_twotone_looks_4_24)

            private fun getImageResIdForSlotIndex(slotRanks: List<Int>, index: Int) : Int? {
                val rank = slotRanks.getOrNull(index) ?: return null

                //Ranks are given in 1-4. Need to subtract one to get an index into the array
                val slotIndex = rank - 1
                return slotImageResIds.getOrNull(slotIndex)
            }
        }

        fun bindTo(item: ArmorPart) {
            view.item_name.text = item.name
            view.type_image.setImageResource(item.imageResource)
            view.item_rank.text = item.getRankText(view.context)
            view.item_min_defense.text = item.minDefenseText

            //Determine whether each slot image should be visible, and if so, which rank icon to display
            val slotRanks = item.slotRanks
            slotIds.forEachIndexed {index, slotid ->
                val slotImageView = view.findViewById<ImageView>(slotid) ?: return
                getImageResIdForSlotIndex(slotRanks, index)?.let {imageResId ->
                    slotImageView.visibility = View.VISIBLE
                    slotImageView.setImageResource(imageResId)
                } ?: run {
                    slotImageView.visibility = View.GONE
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArmorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.armor_item_view, parent, false)
        return ArmorViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArmorViewHolder, position: Int) {
        val part = items[position]
        holder.bindTo(part)
        holder.itemView.setOnClickListener {
            selectedCallback(part)
        }
    }

    override fun getItemCount() = items.size
}