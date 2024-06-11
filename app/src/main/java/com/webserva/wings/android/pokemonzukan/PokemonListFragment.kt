package com.webserva.wings.android.pokemonzukan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide


class PokemonListFragment : Fragment() {

    companion object {


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //リストのデータ用意処理

        return inflater.inflate(R.layout.fragment_pokemon_list, container, false)
    }


    private fun getPokemonList(): Unit {

//
//        val imageView: ImageView = view.findViewById(R.id.imageView)
//        val imageUrl = "https://img.wallpaper.sc/ipad/images/2448x2448/ipad-2448x2448-wallpaper_01438.jpg"
//
//        Glide.with(this)
//            .load(imageUrl)
//            .into(imageView)
    }
}