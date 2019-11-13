package com.rickymortyapp.paging

import android.annotation.SuppressLint
import androidx.paging.PageKeyedDataSource
import com.rickmortyapp.domain.model.CharacterDetail
import com.rickmortyapp.domain.repository.CharacterRepository

class PagedQueryListDatasource(private val characterRepository: CharacterRepository, private val query: String) :
        PageKeyedDataSource<Int, CharacterDetail>() {


    @SuppressLint("CheckResult")
    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, CharacterDetail>) {
        characterRepository
                .getCharacterByName(1, query)
                .subscribe(
                        { callback.onResult(it.results!!, null, 2) },
                        { it.printStackTrace() }
                )
    }

    @SuppressLint("CheckResult")
    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, CharacterDetail>) {
        characterRepository
                .getCharacterByName(params.key, query)
                .subscribe(
                        { callback.onResult(it.results!!, params.key + 1) },
                        { it.printStackTrace() }
                )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, CharacterDetail>) {}
}