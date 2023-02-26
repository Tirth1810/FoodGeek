package com.example.foofgeek.Model

class SearchResultItem(private val recipeId: Int, private val ingrCount: Int) {
    fun get_ingrCount(): Int {
        return ingrCount
    }

    fun get_recipeId(): Int {
        return recipeId
    }
}