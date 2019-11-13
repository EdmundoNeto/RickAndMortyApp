package com.rickmortyapp.domain.datasource

import com.rickmortyapp.domain.model.Characters
import com.rickmortyapp.domain.model.LocationDetail
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterDatasource {
    @GET("/api/character/")
    fun getCharactersList(@Query("page") pageNum: Int): Observable<Characters>

    @GET("/api/character/")
    fun getCharacterByName(@Query("page") pageNum: Int, @Query("name") query: String): Observable<Characters>

    @GET("/api/location/{id}")
    fun getLocationType(@Path("id") id: Int): Observable<LocationDetail>

}