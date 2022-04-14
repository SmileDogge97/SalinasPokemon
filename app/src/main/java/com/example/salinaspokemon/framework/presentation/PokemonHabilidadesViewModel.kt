package com.example.salinaspokemon.framework.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salinaspokemon.domain.usecase.PokemonHabilidadesUseCase
import com.example.salinaspokemon.domain.usecase.PokemonInfoUseCase
import com.example.salinaspokemon.domain.usecase.PokemonLineEvoUseCase
import com.example.salinaspokemon.framework.data.model.habilidades.ResponseHabilidades
import com.example.salinaspokemon.framework.data.model.pokemoninfo.ResponsePokemonInfo
import com.example.salinaspokemon.framework.presentation.viewstate.PokemonHabilidadesViewState
import com.example.salinaspokemon.framework.presentation.viewstate.PokemonLineEvoViewState
import com.example.salinaspokemon.framework.usecase.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class PokemonHabilidadesViewModel @Inject constructor(
    private val pokemonesUseCase: UseCase<PokemonHabilidadesUseCase.Params, Response<ResponseHabilidades>>
) : ViewModel() {
    private var _pokemonState = MutableLiveData<PokemonHabilidadesViewState>()
    val pokemonState: LiveData<PokemonHabilidadesViewState> get() = _pokemonState

    fun loadHabilidades(pokemon: String) {
        Log.d("PokemonHabilidadesViewModel/loadHabilidades/pokemon", pokemon)
        _pokemonState.postValue(PokemonHabilidadesViewState.Loading)
        viewModelScope.launch {
            val lineResult = runCatching {
                pokemonesUseCase.request(PokemonHabilidadesUseCase.Params(pokemon))
            }
            lineResult.onSuccess { response ->
                val info = response.body()?.abilities
                if (!info?.equals(null)!!) {
                    _pokemonState.postValue(PokemonHabilidadesViewState.Success(response.body()!!.abilities.toMutableList()))
                } else {
                    _pokemonState.postValue(PokemonHabilidadesViewState.PokemonNotFound)
                }
            }
            lineResult.onFailure {
                _pokemonState.postValue(PokemonHabilidadesViewState.Error(it.localizedMessage.orEmpty()))
            }
        }
    }
}