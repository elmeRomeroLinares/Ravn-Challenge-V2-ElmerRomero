package com.example.ravnchallengev2.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ravnchallengev2.R
import com.example.ravnchallengev2.databinding.CharacterAttributesListItemBinding
import com.example.ravnchallengev2.databinding.CharactersListItemBinding
import com.example.ravnchallengev2.repository.CharacterAttributesRepository

class AttributesRecyclerViewAdapter(
        private val attributesList: List<CharacterAttributesRepository.CharacterAttribute>
) : RecyclerView.Adapter<AttributesRecyclerViewAdapter.AttributeViewHolder>() {

    class AttributeViewHolder(
            val binding: CharacterAttributesListItemBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int {
        return attributesList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttributeViewHolder {
        return AttributeViewHolder(CharacterAttributesListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
        ))
    }

    override fun onBindViewHolder(holder: AttributeViewHolder, position: Int) {
        val unknownString = holder.itemView.context.getString(R.string.unknown)
        val attribute = attributesList[position]
        holder.binding.fixedAttributeTextView.text = attribute.fixedAttribute
        if (attribute.dynamicAttributeValue == null) {
            holder.binding.dynamicAttributeTextView.text = unknownString
        } else {
            val attributeValueToUpper = attribute.dynamicAttributeValue.toString().capitalize()
            holder.binding.dynamicAttributeTextView.text = attributeValueToUpper
        }
    }
}