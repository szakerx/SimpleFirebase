package com.example.firebase

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.firebase.models.Adapter
import com.example.firebase.models.Game
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.collections.ArrayList
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    private lateinit var vm: MainViewModel
    private val RC_SIGN_IN: Int = 1
    private lateinit var user: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val providers = arrayListOf(
            AuthUI.IdpConfig.GoogleBuilder().build(),
            AuthUI.IdpConfig.EmailBuilder().build()
        )

        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(),
            RC_SIGN_IN)

        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = GridLayoutManager(applicationContext, 1)

        fab.setOnClickListener{
            val intent = Intent(this, AddGameActivity::class.java).putExtra("email", user.uid)
            startActivity(intent)
        }

        fab_logout.setOnClickListener(){
            AuthUI.getInstance().signOut(this)
            startActivityForResult(
                AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .build(),
                RC_SIGN_IN)
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)
            if(resultCode == Activity.RESULT_OK) {
                user = FirebaseAuth.getInstance().currentUser!!
                Log.v("DUPXO", response.toString())
                vm = MainViewModel(user.uid)
                vm.allGames().observe(this, Observer {
                        games -> setupAdapter(games)
                })
            } else {
                moveTaskToBack(true)
                exitProcess(2115)
            }
        }
    }

    private fun setupAdapter(games: List<Game>) {
        recyclerView.adapter = Adapter(this, games, vm)
    }
}
