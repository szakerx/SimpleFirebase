package com.example.firebase.models

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.firebase.MainViewModel
import com.example.firebase.R

class Adapter(
    private val context: Context,
    private val gameArray: List<Game>,
    private val vm: MainViewModel
) :
    RecyclerView.Adapter<Adapter.MyViewHolder>() {
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val gameName: TextView = view.findViewById(R.id.gameNameView)
        val gameGenre: TextView = view.findViewById(R.id.gameGenreView)
        val gameYear: TextView = view.findViewById(R.id.gameYearView)
        val gameSeen: ImageView = view.findViewById(R.id.seen_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.gameview, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return gameArray.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.gameName.text = gameArray[position].name.capitalize()
        holder.gameGenre.text = gameArray[position].genre.capitalize()
        holder.gameYear.text = gameArray[position].releaseYear.capitalize()
        if (gameArray[position].played) {
            holder.gameSeen.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.played_icon
                )
            )
        } else {
            holder.gameSeen.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.toplay_icon
                )
            )
        }
        holder.gameSeen.setOnClickListener {
            Toast.makeText(context, "Clicked on image!", Toast.LENGTH_SHORT).show()
            vm.updateGame(gameArray[position].getOppositeState())
        }
        holder.itemView.setOnLongClickListener {
            val menu = PopupMenu(context, holder.itemView)
            menu.menuInflater.inflate(R.menu.delete_menu, menu.menu)

            menu.setOnMenuItemClickListener { item ->
                when(item.itemId) {
                    R.id.delete_option -> vm.deleteGame(gameArray[position])
                }
                true
            }
            menu.show()
            true
        }
    }
}