package com.example.paybacktask.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Data(
    val id: Int? = null,
    val pageURL: String? = null,
    val type: String? = null,
    val tags: String? = null,
    val previewURL: String? = null,
    val previewWidth: Int? = null,
    val previewHeight: Int? = null,
    val webformatURL: String? = null,
    val webformatWidth: Int? = null,
    val webformatHeight: Int? = null,
    val largeImageURL: String? = null,
    val imageWidth: Int? = null,
    val imageHeight: Int? = null,
    val imageSize: Int? = null,
    val views: Int? = null,
    val downloads: Int? = null,
    val favorites: Int? = null,
    val likes: Int? = null,
    val comments: Int? = null,
    val userId: Int? = null,
    val user: String? = null,
    val userImageURL: String? = null
): Parcelable