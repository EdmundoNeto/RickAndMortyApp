package com.rickymortyapp.paging

import androidx.paging.DataSource
import com.rickmortyapp.domain.model.CharacterDetail
import com.rickmortyapp.domain.repository.CharacterRepository

class CharacterQueryPagedFactory(private val characterRepository: CharacterRepository, private val query : String) :
        DataSource.Factory<Int, CharacterDetail>() {

    override fun create(): DataSource<Int, CharacterDetail> {
        return PagedQueryListDatasource(characterRepository, query)
    }

}