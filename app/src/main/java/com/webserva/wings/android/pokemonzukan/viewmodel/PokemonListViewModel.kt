package com.webserva.wings.android.pokemonzukan.viewmodel

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonParser
import com.webserva.wings.android.pokemonzukan.models.Pokemon
import com.webserva.wings.android.pokemonzukan.repository.PokemonZukanRepository
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

    private val pokemonZukanRepository = PokemonZukanRepository()

    init{
        viewModelScope.launch{

            _isLoading.postValue(true);
            val pokemonList: List<Pokemon> = pokemonZukanRepository.getPokemonList();
            _pokemonList.postValue(pokemonList);
            _isLoading.postValue(false)
        }
    }
}