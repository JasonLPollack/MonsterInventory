package com.pollack.monsterinventory.ui.armor_detail

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.pollack.monsterinventory.R
import com.pollack.monsterinventory.domain.ArmorPart
import com.pollack.monsterinventory.domain.rankText
import com.pollack.monsterinventory.repository.ImageRepository
import com.pollack.monsterinventory.ui.ItemsListModel
import com.pollack.util.TAG
import com.pollack.util.showBackButton
import kotlinx.android.synthetic.main.fragment_armor_detail.*
import kotlinx.coroutines.*
import java.net.URL

class ArmorDetailFragment : Fragment(R.layout.fragment_armor_detail),
    CoroutineScope by CoroutineScope(Job() + Dispatchers.IO)
{
    //Normally, dependencies like this would be injected
    private val imageRepository = ImageRepository()

    private val args: ArmorDetailFragmentArgs by navArgs()
    var item: ArmorPart? = null
    private val model: ItemsListModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.v(TAG, "Show item ${args.armorItemId}")
        item = model.getItemById(args.armorItemId)
        item?.let {item ->
            displayDetailForItem(item)
        }

        showBackButton()
    }

    private fun displayDetailForItem(item: ArmorPart) {
        item_name.text = item.name
        item_rank.text = item.rankText

        item.skills.getOrNull(0)?.let {skill ->
            skill_1_name.text = skill.skillName
            skill_1_description.text = skill.description
        } ?: run {
            skill_1_name.text = resources.getString(R.string.skills_none)
            skill_1_description.visibility = View.GONE
        }

        item.skills.getOrNull(1)?.let {skill ->
            skill_2_name.text = skill.skillName
            skill_2_description.text = skill.description
        } ?: run {
            skill_2_name.visibility = View.GONE
            skill_2_description.visibility = View.GONE
        }

        item.assets?.imageMale?.let {imageSrc ->
            loadImageForView(imageSrc, img_male)
        }

        item.assets?.imageFemale?.let {imageSrc ->
            loadImageForView(imageSrc, img_female)
        }

    }

    private fun loadImageForView(imageSrc: String, image_view: ImageView) {
        val imageURL = URL(imageSrc)

        launch {
            val image = imageRepository.loadImage(imageURL)
            if (image != null) {
                withContext(Dispatchers.Main) {
                    image_view.setImageBitmap(image)
                }
            }
        }

    }

}