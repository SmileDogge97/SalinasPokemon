package com.example.salinaspokemon.framework.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salinaspokemon.domain.usecase.PokemonLineEvoUseCase
import com.example.salinaspokemon.framework.data.model.lineaevolutiva.ResponseLineaEvolutiva
import com.example.salinaspokemon.framework.data.model.lineaevolutiva.Species
import com.example.salinaspokemon.framework.presentation.viewstate.PokemonLineEvoViewState
import com.example.salinaspokemon.framework.usecase.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class PokemonEvoLinViewModel @Inject constructor(
    private val pokemonUseCase: UseCase<PokemonLineEvoUseCase.Params, Response<ResponseLineaEvolutiva>>
) : ViewModel() {
    private var _pokemonState = MutableLiveData<PokemonLineEvoViewState>()
    val pokemonState: LiveData<PokemonLineEvoViewState> get() = _pokemonState

    fun loadPokemonLine(url: String) {
        _pokemonState.postValue(PokemonLineEvoViewState.Loading)
        viewModelScope.launch {
            val lineResult = runCatching {
                pokemonUseCase.request(PokemonLineEvoUseCase.Params(url))
            }
            lineResult.onSuccess { line ->
                val info = line.body()
                if (!info?.equals(null)!!) {

                    _pokemonState.postValue(PokemonLineEvoViewState.Success(getEvolutions(line.body()!!)))
                } else {
                    _pokemonState.postValue(PokemonLineEvoViewState.PokemonNotFound)
                }
            }
            lineResult.onFailure {
                _pokemonState.postValue(PokemonLineEvoViewState.Error(it.localizedMessage.orEmpty()))
            }
        }
    }

    fun getEvolutions(body: ResponseLineaEvolutiva): MutableList<Species> {
        var evolutions: MutableList<Species> = mutableListOf()


        for (i: Int in 0..(body.chain.evolves_to.size - 1)) {
            for (n: Int in 0..(body.chain.evolves_to.get(i).evolves_to.size - 1)) {
                if (!body.chain.evolves_to.get(i).evolves_to.get(n).species.equals(null)) {
                    evolutions.add(Species(body.chain.evolves_to.get(i).evolves_to.get(n).species.name, body.chain.evolves_to.get(i).evolves_to.get(n).species.url))
                }
            }
            if (!body.chain.evolves_to.get(i).species.equals(null)) {
                evolutions.add(Species(body.chain.evolves_to.get(i).species.name, body.chain.evolves_to.get(i).species.url))
            }
        }
        if (!body.chain.species.equals(null)) {
            evolutions.add(Species(body.chain.species.name, body.chain.species.url))
        }

        Log.d("PokemonEvoLinViewModel/getEvolutions/evolutions", evolutions.toString())
        return evolutions
    }

}