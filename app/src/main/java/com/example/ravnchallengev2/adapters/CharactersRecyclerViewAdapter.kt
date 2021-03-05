package com.example.ravnchallengev2.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ravnchallengev2.R
import com.example.ravnchallengev2.databinding.CharactersListItemBinding
import com.example.starwarsserver.StarWarsCharactersQuery

class CharactersRecyclerViewAdapter(
    private val charactersList: List<StarWarsCharactersQuery.Person>
) : RecyclerView.Adapter<CharactersRecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(val binding: CharactersListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int {
        return charactersList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CharactersListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val originUnknownString = holder.itemView.context.getString(R.string.character_origin_unknown)
        val originKnownString = holder.itemView.context.getString(R.string.character_origin)

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
}