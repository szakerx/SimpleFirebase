package com.example.firebase

import androidx.lifecycle.MutableLiveData
import com.example.firebase.models.Game
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*

class FireBaseInstance(uid: String) {

    private val fireBase = FirebaseDatabase.getInstance().getReference("$uid")

    // private val gamesDatabase = fireBase.child("$uid")

    // Add game to gamesDatabase
    fun addGame(game: Game) {
        Thread {
            fireBase.child("${game.name}").setValue(game)
        }.start()
    }

    // Update record in gamesDatabase
    fun updateGame(game: Game) {
        Thread {
            fireBase.child(game.name).updateChildren(mapOf("played" to game.played))
        }.start()
    }

    fun deleteGame(game: Game) {
        Thread {
            fireBase.child(game.name).removeValue()
        }.start()
    }

    // Get all data from database
    fun games(): MutableLiveData<List<Game>> {
        val mutableGames = MutableLiveData<List<Game>>()
        fireBase.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                val listOfGames = ArrayList<Game>()
                for (row in p0.children) {
                    val newRow = row.getValue(Game::class.java)
                    listOfGames.add(newRow!!)
                }
                mutableGames.value = listOfGames
            }
        })
        return mutableGames
    }


}