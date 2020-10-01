package com.pollack.monsterinventory.ui.armor_list

import android.app.ActionBar
import android.content.res.Resources
import android.text.Layout
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pollack.monsterinventory.R
import com.pollack.monsterinventory.domain.*
import kotlinx.android.synthetic.main.armor_item_view.view.*

class ArmorListAdapter(private val items: List<ArmorPart>): RecyclerView.Adapter<ArmorListAdapter.ArmorViewHolder>() {

    class ArmorViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bindTo(item: ArmorPart) {
            view.item_name.text = item.name
            view.type_image.setImageResource(item.imageResource)
            view.item_rank.text = item.rankText
            view.item_min_defense.text = item.minDefenseText
            view.item_slots.removeAllViews()

            item.slotNumbers.forEach {slot ->
                val slotImage = LayoutInflater.from(view.context).inflate(R.layout.slot_number_view, null, false).apply {
                    val slotNumberField = findViewById<TextView>(R.id.slot_number)
                    slotNumberField.text = slot.toString()
                }
                view.item_slots.addView(slotImage)
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
    }


    override fun getItemCount() = items.size
}