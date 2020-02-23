package com.example.paybacktask.service


import com.example.paybacktask.api_endpoint.SearchApi
import com.example.paybacktask.di.DaggerApiComponent
import com.example.paybacktask.model.SearchResult
import io.reactivex.Single
import javax.inject.Inject

class NetworkService {

    @Inject
    lateinit var mSearchResultApi: SearchApi

    init {
        DaggerApiComponent.create().inject(this)
    }

    fun fetchData(query: String): Single<SearchResult> {
        return mSearchResultApi.getResults(KEY, IMAGE_TYPE, query)
    }

    companion object {
        const val BASE_URL = "https://pixabay.com/"
        const val KEY = "15208179-8904704ef87acecba755bb044"
        const val IMAGE_TYPE = "photo"
    }
}