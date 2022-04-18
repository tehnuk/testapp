package com.zufr.testapp.characters

import android.view.View
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagedListEpoxyController
import com.squareup.picasso.Picasso
import com.zufr.testapp.R
import com.zufr.testapp.databinding.ModelCharacterListItemBinding
import com.zufr.testapp.databinding.ModelCharacterListTitleBinding
import com.zufr.testapp.epoxy.LoadingEpoxyModel
import com.zufr.testapp.epoxy.ViewBindingKotlinModel
import com.zufr.testapp.network.GetCharacterByIdResponse
import java.util.*

class CharacterListPagingEpoxyController(
    val onCharacterSelected: (Int) -> Unit
) : PagedListEpoxyController<GetCharacterByIdResponse>() {

    override fun buildItemModel(
        currentPosition: Int,
        item: GetCharacterByIdResponse?
    ): EpoxyModel<*> {
        return CharacterGridItemEpoxyModel(
            characterId = item!!.id,
            imageUrl = item.image,
            name = item.name,
            onCharacterSelected = onCharacterSelected
        ).id(item.id)
    }
    override fun addModels(models: List<EpoxyModel<*>>) {

        if (models.isEmpty()) {
            LoadingEpoxyModel().id("loading").addTo(this)
            return
        }

        CharacterGridTitleEpoxyModel("Main Family")
            .id("main_family_header")
            .addTo(this)

        super.addModels(models.subList(0, 5))

        (models.subList(5, models.size) as List<CharacterGridItemEpoxyModel>).groupBy {
            it.name[0].toUpperCase()
        }.forEach { mapEntry ->
            val character = mapEntry.key.toString().toUpperCase(Locale.US)
            CharacterGridTitleEpoxyModel(title = character)
                .id(character)
                .addTo(this)

            super.addModels(mapEntry.value)
        }
    }

    // region Data Classes

    data class CharacterGridItemEpoxyModel(
        val characterId: Int,
        val imageUrl: String,
        val name: String,
        val onCharacterSelected: (Int) -> Unit
    ): ViewBindingKotlinModel<ModelCharacterListItemBinding>(R.layout.model_character_list_item) {

        override fun ModelCharacterListItemBinding.bind() {
            Picasso.get().load(imageUrl).into(characterImageView)
            characterNameTextView.text = name

            root.setOnClickListener {
                onCharacterSelected(characterId)
            }
        }
    }
    data class CharacterGridTitleEpoxyModel(
        val title: String
    ): ViewBindingKotlinModel<ModelCharacterListTitleBinding>(R.layout.model_character_list_title) {

        override fun ModelCharacterListTitleBinding.bind() {
            textView.text = title
        }

        override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
            return totalSpanCount
        }
    }
    // endregion Data Classes
}