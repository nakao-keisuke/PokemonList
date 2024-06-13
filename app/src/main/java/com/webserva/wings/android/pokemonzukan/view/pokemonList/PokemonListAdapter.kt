package com.webserva.wings.android.pokemonzukan.view.pokemonList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.webserva.wings.android.pokemonzukan.R
import com.webserva.wings.android.pokemonzukan.models.Pokemon
import com.webserva.wings.android.pokemonzukan.view.pokemonDetail.PokemonDetailFragment

class RecyclerAdapter(private val fragment: PokemonListFragment, private val _listData: List<Pokemon>) : RecyclerView.Adapter<PokemonViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val item = layoutInflater.inflate(R.layout.ball, parent, false)

        return PokemonViewHolder(item)
    }
    override fun getItemCount(): Int {

        return _listData.size
    }
    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.view.let{

            val item = _listData[position]

            val id = item.id
            val name = item.name
            val imagePath = item.imagePath

            holder.id.text = "No. $id"
            holder.name.text = name
            Glide.with(fragment)
                .load(imagePath)
                .into(holder.img)
            holder.img.tag = imagePath

            it.setOnClickListener(ListItemClickListener());
        }
    }

    private inner class ListItemClickListener: View.OnClickListener {
        override fun onClick(view: View?) {

            view?.let{

                val LinearLayout = it.findViewById<LinearLayout>(R.id.LinearLayout) ?: return;

                val tvId = LinearLayout.findViewById<TextView>(R.id.tvId)
                val tvName = LinearLayout.findViewById<TextView>(R.id.tvName)
                val imageView = LinearLayout.findViewById<ImageView>(R.id.imageView)

                val bundle = Bundle()
                val pokemon = Pokemon(tvId.text.toString(), tvName.text.toString(), imageView.tag.toString())

                bundle.putSerializable("pokemon", pokemon)

                val transaction  = fragment.parentFragmentManager.beginTransaction();
                transaction.setReorderingAllowed(true)
                transaction.addToBackStack("pokemonList")
                transaction.replace(R.id.fragmentMainContainer, PokemonDetailFragment::class.java, bundle);

                transaction.commit();
            }
        }
    }
}
