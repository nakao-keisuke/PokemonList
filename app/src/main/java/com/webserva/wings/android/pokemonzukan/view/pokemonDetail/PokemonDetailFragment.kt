package com.webserva.wings.android.pokemonzukan.view.pokemonDetail

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.webserva.wings.android.pokemonzukan.R
import com.webserva.wings.android.pokemonzukan.models.Pokemon
import com.webserva.wings.android.pokemonzukan.view.activity.MainActivity

class PokemonDetailFragment : Fragment() {

    companion object {


    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.pokemon_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                // 戻るボタンがクリックされたときの処理
                parentFragmentManager.popBackStack();

                val activity = requireActivity() as MainActivity
                activity.setupBackButton(false)

                true
            }
            else -> false
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_pokemon_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity() as MainActivity
        activity.setupBackButton(true)

        val pokemon = arguments?.getSerializable("pokemon") as Pokemon;
        val id = pokemon.id;
        val name = pokemon.name;
        val imagePath = pokemon.imagePath;

        val tvId = view.findViewById<TextView>(R.id.tvId);
        val tvName = view.findViewById<TextView>(R.id.tvName);
        val imageView = view.findViewById<ImageView>(R.id.imageView);

        tvId?.text = id;
        tvName?.text = name;
        Glide.with(this)
            .load(imagePath)
            .into(imageView)
    }
}