package com.rickymortyapp.paging

import android.annotation.SuppressLint
import androidx.paging.PageKeyedDataSource
import com.rickmortyapp.domain.model.CharacterDetail
import com.rickmortyapp.domain.repository.CharacterRepository

class PagedCharactersListDatasource(private val characterRepository: CharacterRepository) :
        PageKeyedDataSource<Int, CharacterDetail>() {

    @SuppressLint("CheckResult")
    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, CharacterDetail>) {
        characterRepository.getCharacterList(1)
            .subscribe(
                    { callback.onResult(it.results!!, null, 2) },
                    { it.printStackTrace() }
            )
    }

    @SuppressLint("CheckResult")
    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, CharacterDetail>) {
        characterRepository.getCharacterList(params.key)
                .subscribe(
                        { callback.onResult(it.results!!, params.key + 1) },
                        { it.printStackTrace() }
                )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, CharacterDetail>) {}
}