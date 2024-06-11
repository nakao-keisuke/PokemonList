package com.webserva.wings.android.pokemonzukan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class PokemonDetailFragment : Fragment() {

    companion object {


    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pokemon_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.getString("id");
        val name = arguments?.getString("name");

        val tvId = view.findViewById<TextView>(R.id.tvId);
        val tvName = view.findViewById<TextView>(R.id.tvName);

        tvId?.text = id;
        tvName?.text = name;
    }
}