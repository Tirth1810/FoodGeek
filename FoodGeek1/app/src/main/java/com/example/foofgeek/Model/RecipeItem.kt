package com.example.foofgeek.Model

class RecipeItem(
    private val id: Int,
    private val category: String,
    private val recipeName: String,
    private val author: String,
    private val uploadDate: String,
    private val howTo: String,
    private val description: String,
    private val thumbnail: ByteArray,
    private val mainImg: ByteArray,
    private val likeCount: Int
) {
    fun get_id(): Int {
        return id
    }

    fun get_category(): String {
        return category
    }

    fun get_recipeName(): String {
        return recipeName
    }

    fun get_author(): String {
        return author
    }

    fun get_uploadDate(): String {
        return uploadDate
    }

    fun get_howTo(): String {
        return howTo
    }

    fun get_Description(): String {
        return description
    }

    fun get_thumbnail(): ByteArray {
        return thumbnail
    }

    fun get_mainImg(): ByteArray {
        return mainImg
    }

    fun get_likeCount(): Int {
        return likeCount
    }
}