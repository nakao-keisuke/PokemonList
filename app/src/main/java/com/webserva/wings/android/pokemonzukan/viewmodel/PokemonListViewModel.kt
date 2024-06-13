package com.webserva.wings.android.pokemonzukan.viewmodel

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonParser
import com.webserva.wings.android.pokemonzukan.models.Pokemon
import com.webserva.wings.android.pokemonzukan.view.pokemonList.PokemonListFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.withContext
import java.net.URL

class PokemonListViewModel: ViewModel() {

    private val _pokemonList: MutableLiveData<List<Pokemon>> = MutableLiveData()
    val pokemonList: LiveData<List<Pokemon>>
        get() = _pokemonList

    private var _isLoading: MutableLiveData<Boolean> = MutableLiveData(false);
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    companion object {

        private const val POKEMON_NAME_URL = "https://pokeapi.co/api/v2/pokemon/?offset=0&limit=151";
    }

    init{
        viewModelScope.launch{

            _isLoading.postValue(true);delay(2000)
            val pokemonList: List<Pokemon> = getPokemonList();
            _pokemonList.postValue(pokemonList);
            _isLoading.postValue(false)
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