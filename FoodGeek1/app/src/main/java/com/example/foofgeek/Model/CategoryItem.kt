package com.example.foofgeek.Model

class CategoryItem(private val category: String, private val mainImg: ByteArray) {
    fun get_category(): String {
        return category
    }

    fun get_mainImg(): ByteArray {
        return mainImg
    }
}