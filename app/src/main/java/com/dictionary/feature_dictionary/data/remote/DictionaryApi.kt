package com.dictionary.feature_dictionary.data.remote

import com.dictionary.feature_dictionary.data.remote.dto.WordInfoDto
import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryApi {

    @GET("/api/vs/entries/en/{search}")
    suspend fun getDefinition(
        @Path("search") search: String
    ): List<WordInfoDto>

}