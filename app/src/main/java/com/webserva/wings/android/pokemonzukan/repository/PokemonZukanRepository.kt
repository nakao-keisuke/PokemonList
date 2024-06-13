package com.webserva.wings.android.pokemonzukan.repository

import androidx.annotation.WorkerThread
import com.google.gson.JsonParser
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.withContext
import java.net.URL

class PokemonZukanRepository {
    companion object{

        private const val POKEMON_NAME_URL = "https://pokeapi.co/api/v2/pokemon/?offset=0&limit=151";
    }
    @WorkerThread
    private suspend fun getPokemonList(): MutableList<MutableMap<String, String>> {

        val customDispatcher = newFixedThreadPoolContext(12, "CustomPool")

        val result = withContext(customDispatcher){

            val pokemonList: MutableList<MutableMap<String, String>> = mutableListOf()

            val url = URL(POKEMON_NAME_URL)

            val json = url.readText();
            val jsonObject = JsonParser.parseString(json).asJsonObject
            val results = jsonObject.getAsJsonArray("results") ;

            for((index, result) in results.withIndex()){

                val pokemonInfo = result.asJsonObject

                val id = (index + 1).toString()
                val name = pokemonInfo.getAsJsonPrimitive("name").asString

                val frontImgPath = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"

                var menu = mutableMapOf("id" to id, "imagePath" to frontImgPath, "name" to name)
                pokemonList.add(menu)
            }

            pokemonList;
        }

        return result;
    }
}