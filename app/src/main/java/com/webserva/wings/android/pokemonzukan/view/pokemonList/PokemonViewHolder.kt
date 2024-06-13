package com.webserva.wings.android.pokemonzukan.view.pokemonList

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.webserva.wings.android.pokemonzukan.R

class PokemonViewHolder(val view: View): RecyclerView.ViewHolder(view) {

    var id: TextView
    var name: TextView
    var img: ImageView

    init {
        id = itemView.findViewById(R.id.tvId)
        name = itemView.findViewById(R.id.tvName)
        img = itemView.findViewById(R.id.imageView)
    }
}