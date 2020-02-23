package com.example.paybacktask.api_endpoint

import com.example.paybacktask.model.SearchResult
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {

    @GET("api/")
    fun getResults(
        @Query("key") key: String,
        @Query("image_type") image_type: String,
        @Query("q") q: String

    ): Single<SearchResult>
}