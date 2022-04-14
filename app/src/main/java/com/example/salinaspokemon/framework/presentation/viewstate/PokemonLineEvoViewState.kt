package com.example.salinaspokemon.framework.presentation.viewstate

import com.example.salinaspokemon.framework.data.model.lineaevolutiva.ResponseLineaEvolutiva
import com.example.salinaspokemon.framework.data.model.lineaevolutiva.Species
import com.example.salinaspokemon.framework.data.model.pokemoninfo.ResponsePokemonInfo

sealed class PokemonLineEvoViewState{
    object Loading:PokemonLineEvoViewState()
    data class Success(val response: MutableList<Species>):PokemonLineEvoViewState()
    data class Error(val error: String): PokemonLineEvoViewState()
    object PokemonNotFound: PokemonLineEvoViewState()
}