package com.webserva.wings.android.pokemonzukan.view.pokemonList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.WorkerThread
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.bumptech.glide.Glide
import com.google.gson.JsonParser
import com.webserva.wings.android.pokemonzukan.R
import com.webserva.wings.android.pokemonzukan.models.Pokemon
import com.webserva.wings.android.pokemonzukan.view.pokemonDetail.PokemonDetailFragment
import com.webserva.wings.android.pokemonzukan.viewmodel.PokemonListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.URL
import java.nio.charset.StandardCharsets


class PokemonListFragment : Fragment() {

    private val viewModel: PokemonListViewModel by viewModels()
    private lateinit var _adapter: RecyclerAdapter;
    private lateinit var recyclerView: RecyclerView;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_pokemon_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _adapter = RecyclerAdapter(this@PokemonListFragment);
        recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView);

        val layoutManager = GridLayoutManager(
            requireContext(),
            2,
            RecyclerView.VERTICAL,
            false);

        recyclerView.layoutManager = layoutManager;
        recyclerView.adapter = _adapter;

        viewModel.pokemonList.observe(viewLifecycleOwner) { newPokemonList ->
            _adapter.submitList(newPokemonList)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            changeLoadingState(it)
        }
    }

    private fun changeLoadingState(state: Boolean){

        val pb = view?.findViewById<ProgressBar>(R.id.progressBar)
        val tvLoading = view?.findViewById<TextView>(R.id.tvLoading)

        if(state){
            pb?.visibility = View.VISIBLE
            tvLoading?.visibility = View.VISIBLE
        }
        else{
            pb?.visibility = View.GONE
            tvLoading?.visibility = View.GONE
        }
    }
}