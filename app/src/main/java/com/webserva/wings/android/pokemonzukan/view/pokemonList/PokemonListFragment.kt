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

    private val viewModel: PokemonListViewModel = PokemonListViewModel()//後でDI方式に修正
    private var _adapter: RecyclerAdapter? = null;
    private lateinit var recyclerView: RecyclerView;

    companion object {

        private const val POKEMON_NAME_URL = "https://pokeapi.co/api/v2/pokemon/?offset=0&limit=151";
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_pokemon_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val _rv = view.findViewById<RecyclerView>(R.id.recyclerView);
        val pb = view.findViewById<ProgressBar>(R.id.progressBar)
        val tvLoading = view.findViewById<TextView>(R.id.tvLoading)

        val layoutManager = GridLayoutManager(
            requireContext(),
            2,
            RecyclerView.VERTICAL,
            false);
        _rv?.layoutManager = layoutManager;

        CoroutineScope(Dispatchers.Main).launch{

            if(_adapter == null){

                pb.visibility = View.VISIBLE
                tvLoading.visibility = View.VISIBLE
                val pokemonList = getPokemonList();
                pb.visibility = View.GONE
                tvLoading.visibility = View.GONE

                val adapter = RecyclerAdapter(this@PokemonListFragment, pokemonList);

                _rv.adapter = adapter;
                _adapter = adapter;
            }
            else{
                _rv.adapter = _adapter;
            }
        }
    }

    @WorkerThread
    private suspend fun getPokemonList(): List<Pokemon> {

        val customDispatcher = newFixedThreadPoolContext(12, "CustomPool")

        val result = withContext(customDispatcher){

            val pokemonList: MutableList<Pokemon> = mutableListOf()

            val url = URL(POKEMON_NAME_URL)

            val json = url.readText();
            val jsonObject = JsonParser.parseString(json).asJsonObject
            val results = jsonObject.getAsJsonArray("results") ;

            for((index, result) in results.withIndex()){

                val pokemonInfo = result.asJsonObject

                val id = (index + 1).toString()
                val name = pokemonInfo.getAsJsonPrimitive("name").asString

                val imagePath = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"

                var pokemon = Pokemon(id, name, imagePath)
                pokemonList.add(pokemon)
            }

            pokemonList.toList();
        }

        return result;
    }
}