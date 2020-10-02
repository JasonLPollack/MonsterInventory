package com.pollack.monsterinventory.ui.armor_list

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.PopupMenu
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.pollack.monsterinventory.R
import com.pollack.monsterinventory.domain.ArmorPart
import com.pollack.monsterinventory.ui.*
import com.pollack.util.TAG
import com.pollack.util.hideBackButton
import kotlinx.android.synthetic.main.fragment_armor_list.*

class ArmorListFragment : Fragment(R.layout.fragment_armor_list) {

    private val model: ItemsListModel by activityViewModels()

    private var armorAdapter: ArmorListAdapter? = null
    private var allArmor = listOf<ArmorPart>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideBackButton()

        armor_list.layoutManager = LinearLayoutManager(requireContext())

        model.armorDataState.observe(viewLifecycleOwner) { state ->
            onUpdatedState(state)
        }

        btn_try_again.setOnClickListener {
            model.reset()
        }

        model.filterBy.observe(viewLifecycleOwner) { _ ->
            updateDisplayedList(model.getFilteredAndSortedList())
        }

        model.sortBy.observe(viewLifecycleOwner) { _ ->
            updateDisplayedList(model.getFilteredAndSortedList())
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

        sort_direction.setOnClickListener {
            val menu = PopupMenu(requireContext(), sort_direction)
            menu.inflate(R.menu.sort_menu)
            val selectedItemId = when (model.sortBy.value) {
                ArmorSortBy.DEFENSE -> R.id.sort_by_defense
                ArmorSortBy.RANK -> R.id.sort_by_rank
                else -> R.id.sort_by_name
            }
            menu.menu.findItem(selectedItemId)?.let {
                it.setChecked(true)
            }

            menu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.sort_by_name -> model.sortBy.postValue(ArmorSortBy.NAME)
                    R.id.sort_by_defense -> model.sortBy.postValue(ArmorSortBy.DEFENSE)
                    R.id.sort_by_rank -> model.sortBy.postValue(ArmorSortBy.RANK)
                }
                true
            }
            menu.show()
        }

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

    protected fun onUpdatedState(state: ArmorDataState) {
        var loadingCardVisibility = View.GONE
        var errorCardVisibility = View.GONE
        var listVisibility = View.GONE

        when (state) {
            is ArmorDataPopulated -> {
                listVisibility = View.VISIBLE
                onHaveParts(state.items)
            }
            is ArmorDataError -> {
                errorCardVisibility = View.VISIBLE
            }
            is ArmorDataUninitialized -> {
                loadingCardVisibility = View.VISIBLE
                onUninitialized()
            }
        }

        loading_card.visibility = loadingCardVisibility
        error_card.visibility = errorCardVisibility
        main_list_layout.visibility = listVisibility
    }

    protected fun onHaveParts(parts: List<ArmorPart>) {
        allArmor = parts.sortedBy { it.name }
        updateDisplayedList(allArmor)
    }

    protected fun onUninitialized() {
        model.loadItems()
    }
}