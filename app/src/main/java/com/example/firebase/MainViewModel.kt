package com.example.firebase

import androidx.lifecycle.ViewModel
import com.example.firebase.models.Game

class MainViewModel(uid: String): ViewModel(){
    // Add game to FireBase through remote class
    private val fb = FireBaseInstance(uid)

    fun addGame(game: Game) = fb.addGame(game)

    fun updateGame(game: Game) = fb.updateGame(game)

    fun deleteGame(game: Game) = fb.deleteGame(game)

    fun allGames() = fb.games()
}