package com.example.foofgeek.Model

class RecipeIngr(val id: Int, val name: String, val ingredients: ArrayList<IngredientItem>) {

    override fun toString(): String {
        return "" + id + "" + name + "" + ingredients.toString()
    }
}