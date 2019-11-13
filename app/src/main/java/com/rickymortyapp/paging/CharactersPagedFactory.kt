package com.rickymortyapp.paging

import androidx.paging.DataSource
import com.rickmortyapp.domain.model.CharacterDetail
import com.rickmortyapp.domain.repository.CharacterRepository

class CharactersPagedFactory(private val characterRepository: CharacterRepository) :
        DataSource.Factory<Int, CharacterDetail>() {

    override fun create(): DataSource<Int, CharacterDetail> {
        return PagedCharactersListDatasource(characterRepository)
    }

}