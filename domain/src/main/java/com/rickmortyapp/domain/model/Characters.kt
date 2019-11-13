package com.rickmortyapp.domain.model

import java.io.Serializable

data class Characters(
    var info: Info? = null,
    var results: List<CharacterDetail>? = null
)

data class CharacterDetail(
    var created: String? = null,
    var episode: List<String>? = null,
    var gender: String? = null,
    var id: Int? = null,
    var image: String? = null,
    var location: Location? = null,
    var name: String? = null,
    var origin: Origin? = null,
    var species: String? = null,
    var status: String? = null,
    var type: String? = null,
    var url: String? = null
): Serializable
{
    fun getTotalEpisodes() = episode?.size
}


data class Origin(
    var name: String? = null,
    var url: String? = null
)

data class Location(
    var name: String? = null,
    var url: String? = null
) {
    fun getLocationId(): Int {
        var id  = 0
        val locationSplit = url?.split("/location/")
        if(!locationSplit.isNullOrEmpty())
            id = locationSplit.last().toInt()

        return id
    }
}

data class Info(
    var count: Int? = null,
    var next: String? = null,
    var pages: Int? = null,
    var prev: String? = null
)

data class LocationDetail(
    val created: String? = null,
    val dimension: String? = null,
    val id: Int? = null,
    val name: String? = null,
    val residents: List<String>? = null,
    val type: String? = null,
    val url: String? = null
)
