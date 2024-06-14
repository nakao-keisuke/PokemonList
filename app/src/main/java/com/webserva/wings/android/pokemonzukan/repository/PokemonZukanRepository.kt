package com.webserva.wings.android.pokemonzukan.repository

import androidx.annotation.WorkerThread
import com.google.gson.JsonParser
import com.webserva.wings.android.pokemonzukan.models.Pokemon
import com.webserva.wings.android.pokemonzukan.viewmodel.PokemonListViewModel
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.withContext
import java.net.URL

class PokemonZukanRepository {
    companion object{

        private const val POKEMON_NAME_URL = "https://pokeapi.co/api/v2/pokemon/?offset=0&limit=151";
    }
    @WorkerThread
    suspend fun getPokemonList(): List<Pokemon> {

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

                val pokemon = Pokemon(id, name, imagePath)
                pokemonList.add(pokemon)
            }

            pokemonList.toList();
        }

        return result;
    }
}