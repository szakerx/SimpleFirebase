package com.example.firebase.models

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Game(val name: String, val genre: String, val releaseYear: String, val played: Boolean) {

    // This little boy is needed for casting FireBase Database to objects
    constructor() : this("", "", "", false)

    @Exclude
    fun getOppositeState() = Game(this.name, this.genre, this.releaseYear, !this.played)
}