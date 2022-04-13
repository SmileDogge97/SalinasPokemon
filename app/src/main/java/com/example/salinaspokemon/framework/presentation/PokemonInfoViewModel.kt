package com.example.salinaspokemon.framework.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salinaspokemon.domain.usecase.PokemonInfoUseCase
import com.example.salinaspokemon.framework.data.model.pokemoninfo.ResponsePokemonInfo
import com.example.salinaspokemon.framework.presentation.viewstate.PokemonInfoViewState
import com.example.salinaspokemon.framework.usecase.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class PokemonInfoViewModel @Inject constructor(
    private val pokemonesUseCase: UseCase<PokemonInfoUseCase.Params, Response<ResponsePokemonInfo>>
) : ViewModel() {
    private var _pokemonState = MutableLiveData<PokemonInfoViewState>()
    val pokemonState: LiveData<PokemonInfoViewState> get() = _pokemonState

    fun loadPokemonInfo(pokemon: String) {
        _pokemonState.postValue(PokemonInfoViewState.Loading)
        viewModelScope.launch {
            val pokemonResult = runCatching {
                pokemonesUseCase.request(PokemonInfoUseCase.Params(pokemon))
            }

            pokemonResult.onSuccess { pokemon ->
                val info = pokemon.body()
                if (!info?.equals(null)!!) {
                    _pokemonState.postValue(PokemonInfoViewState.Success(pokemon.body()!!))
                } else {
                    _pokemonState.postValue(PokemonInfoViewState.PokemonNotFound)
                }
            }
            pokemonResult.onFailure {
                _pokemonState.postValue(PokemonInfoViewState.Error(it.localizedMessage.orEmpty()))
            }
        }
    }
}