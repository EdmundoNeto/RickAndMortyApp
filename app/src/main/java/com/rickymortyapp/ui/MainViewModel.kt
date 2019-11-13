package com.rickymortyapp.ui

import android.widget.SearchView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.rickmortyapp.domain.model.CharacterDetail
import com.rickmortyapp.domain.model.LocationDetail
import com.rickmortyapp.domain.repository.CharacterRepository
import com.rickymortyapp.paging.CharacterQueryPagedFactory
import com.rickymortyapp.paging.CharactersPagedFactory
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class MainViewModel : ViewModel(),
        SearchView.OnQueryTextListener {

    private val textChangeSubject: PublishSubject<String> = PublishSubject.create<String>()
    var listLiveData: LiveData<PagedList<CharacterDetail>>? = null
    var queryLiveData : LiveData<PagedList<CharacterDetail>>? = null
    var isQueryLiveData : MutableLiveData<Boolean> = MutableLiveData()
    var locationType: MutableLiveData<String> = MutableLiveData()

    private val config : PagedList.Config = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setPageSize(20)
            .setInitialLoadSizeHint(20)
            .setPrefetchDistance(5)
            .build()

    init {
        listLiveData = LivePagedListBuilder<Int, CharacterDetail>(CharactersPagedFactory(CharacterRepository), config).build()
        queryChangeObserver().subscribe(consumeString())
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let {
            textChangeSubject.onNext(query)
            isQueryLiveData.postValue(true)
            return true
        }
        textChangeSubject.onNext("")
        isQueryLiveData.postValue(false)
        return false

    }

    override fun onQueryTextChange(newText: String?): Boolean {
        newText?.let {
            textChangeSubject.onNext(newText)
            isQueryLiveData.postValue(true)
            return true
        }
        textChangeSubject.onNext("")
        isQueryLiveData.postValue(false)
        return false
    }

    private fun queryChangeObserver() : Observable<String> = textChangeSubject.debounce(200, TimeUnit.MILLISECONDS).distinctUntilChanged()

    private fun consumeString () : Consumer<String> = Consumer {
        queryLiveData = LivePagedListBuilder<Int, CharacterDetail>(CharacterQueryPagedFactory(CharacterRepository, it), config).build()
    }

    fun getLocation(id: Int) {
        val disposableObserver = object: DisposableObserver<LocationDetail>() {
            override fun onComplete() { }

            override fun onNext(locationDetail: LocationDetail) {
                locationDetail.type?.let {
                    locationType.postValue(it)
                }
            }

            override fun onError(e: Throwable) { }
        }

        CharacterRepository.getLocationType(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver)
    }

}