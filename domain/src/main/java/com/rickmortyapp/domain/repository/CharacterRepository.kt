package com.rickmortyapp.domain.repository

import com.rickmortyapp.domain.remote.characterRetrofit

object CharacterRepository {

    fun getCharacterList(page: Int) =
            characterRetrofit.getCharactersList(page)

    fun getCharacterByName(page: Int, query: String) =
            characterRetrofit.getCharacterByName(page, query)

    fun getLocationType(id: Int) =
            characterRetrofit.getLocationType(id)

}