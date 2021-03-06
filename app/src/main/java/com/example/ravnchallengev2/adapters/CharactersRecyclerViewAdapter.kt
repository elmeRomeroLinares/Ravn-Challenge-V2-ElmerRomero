package com.example.ravnchallengev2.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ravnchallengev2.R
import com.example.ravnchallengev2.databinding.CharactersListItemBinding
import com.example.ravnchallengev2.databinding.LoadingListItemBinding
import com.example.starwarsserver.StarWarsCharactersQuery

class CharactersRecyclerViewAdapter(
    private val charactersList: List<StarWarsCharactersQuery.Person>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val ITEM = 0
        private const val LOADING = 1
        var isLoaderVisible = true
    }

    fun setIsLoaderVisible(value: Boolean) {
        isLoaderVisible = value
    }

    var onEndOfListReached: (() -> Unit)? = null

    class NormalViewHolder(
            val binding: CharactersListItemBinding
            ) : RecyclerView.ViewHolder(binding.root)
    class LoadingViewHolder(
            val loadingBinding: LoadingListItemBinding
            ) : RecyclerView.ViewHolder(loadingBinding.root)

    override fun getItemCount(): Int {
        return charactersList.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == charactersList.size) {
            onEndOfListReached?.invoke()
            LOADING
        } else ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val loadingBinding = LoadingListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
        )
        val itemBinding = CharactersListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
        )
        return when (viewType) {
            LOADING -> LoadingViewHolder(loadingBinding)
            else -> NormalViewHolder(itemBinding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val originUnknownString = holder.itemView.context.getString(R.string.character_origin_unknown)
        val originKnownString = holder.itemView.context.getString(R.string.character_origin)

        when (holder) {
            is NormalViewHolder -> {
                val character = charactersList.get(position)
                holder.binding.characterNameListItemTextView.text = character.name
                if (character.species == null) {
                    holder.binding.characterOriginListItemTextView.text = String.format(
                            originUnknownString,
                            character.homeworld?.name
                    )
                } else {
                    holder.binding.characterOriginListItemTextView.text = String.format(
                            originKnownString,
                            character.species.name,
                            character.homeworld?.name
                    )
                }

            }
            is LoadingViewHolder -> {
                if (isLoaderVisible) {
                   holder.itemView.visibility = View.VISIBLE
                } else {
                    holder.itemView.visibility = View.GONE
                }
            }
        }
    }
}