package com.example.salinaspokemon.framework.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salinaspokemon.data.datasource.db.Pokemon
import com.example.salinaspokemon.domain.usecase.PokemonesUseCase
import com.example.salinaspokemon.framework.data.model.ResponsePokemones
import com.example.salinaspokemon.framework.data.model.Result
import com.example.salinaspokemon.framework.presentation.viewstate.PokemonesViewState
import com.example.salinaspokemon.utils.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class PokemonesViewModel @Inject constructor(
    private val pokemonesUseCase: UseCase<PokemonesUseCase.Params, Response<ResponsePokemones>>
) : ViewModel() {
    private var _pokemonesState = MutableLiveData<PokemonesViewState>()
    val pokemonesState: LiveData<PokemonesViewState> get() = _pokemonesState

    fun loadPokemones() {
        _pokemonesState.postValue(PokemonesViewState.Loadig)
        viewModelScope.launch {
            val pokemonesResult = runCatching {
                pokemonesUseCase.request(PokemonesUseCase.Params(limit = 151))
            }
            pokemonesResult.onSuccess { pokemones ->
                val totalPokemones = pokemones.body()?.results.orEmpty()
                if (totalPokemones.isNotEmpty()) {
                    if (countPokemonesBD() == 0) {
                        for (i: Int in 0..150) pokemonesUseCase.insertAllBD(
                            Pokemon(
                                totalPokemones.get(
                                    i
                                ).url, totalPokemones.get(i).name
                            )
                        )
                    }
                    _pokemonesState.postValue(PokemonesViewState.Success(pokemones.body()!!.results))
                } else {
                    _pokemonesState.postValue(PokemonesViewState.PokemonesNotFound)
                }
            }
            pokemonesResult.onFailure {

                _pokemonesState.postValue(PokemonesViewState.Error(it.localizedMessage.orEmpty()))
            }
        }
    }

    fun loadPokemonesBD() {
        _pokemonesState.postValue(PokemonesViewState.Loadig)
        viewModelScope.launch {
            val lista:MutableList<Result> = mutableListOf()
            val pokemonesResult = pokemonesUseCase.getAllBD()

            for (i: Int in 0..150) lista.add(i, Result(pokemonesResult.get(i).name.toString(), pokemonesResult.get(i).url))

            _pokemonesState.postValue(
                PokemonesViewState.Success(
                    lista.toList()
                )
            )
        }
    }

    suspend fun countPokemonesBD(): Int {
        return pokemonesUseCase.countPokemonBD()
    }
}