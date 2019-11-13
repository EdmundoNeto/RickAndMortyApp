package com.rickmortyapp.domain

import com.rickmortyapp.domain.repository.CharacterRepository
import org.junit.Test
import retrofit2.adapter.rxjava2.HttpException

class DomainRepositoryTest {

    @Test
    fun checkResponse() {
        CharacterRepository.getCharacterList(1)
                .test()
                .assertNoTimeout()
                .assertValue { response -> response.results?.size == 20 }
    }

    @Test
    fun checkNegativeEntry() {
        val negativeEntry = -1
        CharacterRepository.getCharacterList(negativeEntry)
                .test()
                .assertNoTimeout()
                .assertValue { response -> response.info?.count!! > 0 }
    }

    @Test
    fun checkZeroEntry() {
        val zeroEntry = 0
        CharacterRepository.getCharacterList(zeroEntry)
                .test()
                .assertNoTimeout()
                .assertValue { response -> response.info?.count!! > 0 }
    }

    @Test
    fun checkPageOutOfRangeEntry() {
        val outOfRangeEntry = 1000
        CharacterRepository.getCharacterList(outOfRangeEntry)
                .test()
                .assertNoTimeout()
                .assertError {
                    it is (HttpException)
                }
    }

    @Test
    fun checkInvalidNameEntryError() {
        val page = 1
        val invalidName = "xy"
        CharacterRepository.getCharacterByName(page, invalidName)
                .test()
                .assertNoTimeout()
                .assertError {
                    it is (HttpException)
                }
    }

    @Test
    fun checkInvalidPageValidNameEntryError() {
        val invalidPage = 100
        val validName = "Rick"
        CharacterRepository.getCharacterByName(invalidPage, validName)
                .test()
                .assertNoTimeout()
                .assertError {
                    it is (HttpException)
                }
    }

    @Test
    fun checkValidPageValidNameEntryError() {
        val page = 1
        val validName = "Rick"
        CharacterRepository.getCharacterByName(page, validName)
                .test()
                .assertNoTimeout()
                .assertValue { response ->
                    val result = response.results?.map { it.name?.contains("Rick") }
                    result?.size!! > 0
                }
    }

}