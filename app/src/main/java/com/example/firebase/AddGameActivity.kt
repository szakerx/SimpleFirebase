package com.example.firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.firebase.models.Game
import kotlinx.android.synthetic.main.activity_add_game.*
import java.util.*

class AddGameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_game)
        val extras = intent.extras
        val email: String = extras?.get("email").toString()

        val vm = MainViewModel(email)

        add_button.setOnClickListener {
            val name = name_editText.text.toString()
            val genre = genre_editText.text.toString()
            val year = year_editText.text.toString()
            val game = Game(name, genre, year, false)
            Thread {
                vm.addGame(game)
            }.start()
            finish()
        }
    }

}
