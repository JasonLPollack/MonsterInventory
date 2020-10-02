package com.pollack.monsterinventory.ui.armor_list

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.pollack.monsterinventory.R
import com.pollack.monsterinventory.domain.ArmorPart
import com.pollack.monsterinventory.ui.*
import com.pollack.util.TAG
import kotlinx.android.synthetic.main.fragment_armor_list.*

class ArmorListFragment : Fragment(R.layout.fragment_armor_list) {

    private val model: ItemsListModel by activityViewModels()

    private var armorAdapter: ArmorListAdapter? = null

    private var allArmor = listOf<ArmorPart>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        armor_list.layoutManager = LinearLayoutManager(requireContext())

        model.armorDataState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ArmorDataPopulated -> onHaveParts(state.items)
                is ArmorDataError -> onRetrievalError()
                is ArmorDataUninitialized -> model.loadItems()
            }
        }

        model.filterBy.observe(viewLifecycleOwner) { filterBy ->
            val filteredListOfParts = allArmor.filter { part ->
                part.name.contains(filterBy, ignoreCase = true)
            }
            updateDisplayedList(filteredListOfParts)
        }

        filter.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(text: String): Boolean {
                return true
            }

            override fun onQueryTextChange(text: String): Boolean {
                model.filterBy.postValue(text)
                return true
            }

        })
    }

    protected fun onHaveParts(parts: List<ArmorPart>) {
        allArmor = parts.sortedBy { it.name }
        updateDisplayedList(allArmor)
    }

    protected fun updateDisplayedList(partsToDisplay: List<ArmorPart>) {
        Log.v(TAG, "About to display ${partsToDisplay.count()} items in the list")
        armorAdapter = ArmorListAdapter(partsToDisplay) { selectedItem ->
            val action = ArmorListFragmentDirections.actionArmorListToArmorDetail(armorItemId = selectedItem.id)
            findNavController().navigate(action)
            Log.v(TAG, "Clicked on ${selectedItem.name}")
        }
        armor_list.adapter = armorAdapter
    }

    protected fun onRetrievalError() {
        Log.v(TAG, "Oh noes!")
    }
}