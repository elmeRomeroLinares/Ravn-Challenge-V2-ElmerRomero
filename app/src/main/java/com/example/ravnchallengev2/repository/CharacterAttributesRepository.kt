package com.example.ravnchallengev2.repository

import android.content.Context
import com.example.ravnchallengev2.R
import com.example.starwarsserver.CharacterDetailsQuery

class CharacterAttributesRepository {

    data class CharacterAttribute(
        val fixedAttribute: String,
        var dynamicAttributeValue: String?
    )

    fun getCharacterFixedAttributes(characterAttribute: CharacterDetailsQuery.Person, context: Context) : List<CharacterAttribute> {
        val mutableList = mutableListOf<CharacterAttribute>()

        mutableList.add(
                CharacterAttribute(
                        fixedAttribute = context.getString(R.string.eye_color),
                        dynamicAttributeValue = characterAttribute.eyeColor
                )
        )

        mutableList.add(
                CharacterAttribute(
                        fixedAttribute = context.getString(R.string.hair_color),
                        dynamicAttributeValue = characterAttribute.hairColor
                )
        )

        mutableList.add(
                CharacterAttribute(
                        fixedAttribute = context.getString(R.string.skin_color),
                        dynamicAttributeValue = characterAttribute.skinColor
                )
        )

        mutableList.add(
                CharacterAttribute(
                        fixedAttribute = context.getString(R.string.birth_year),
                        dynamicAttributeValue = characterAttribute.birthYear
                )
        )

        return mutableList.toList()
    }
}