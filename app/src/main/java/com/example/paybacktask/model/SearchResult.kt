package com.example.paybacktask.model

data class SearchResult (
    val total: Int? = null,
    val totalHits: Int? = null,
    val hits: List<Data>? = null
)