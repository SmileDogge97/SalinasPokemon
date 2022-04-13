package com.example.salinaspokemon.framework.presentation.viewstate

import com.example.salinaspokemon.framework.data.model.pokemoninfo.ResponsePokemonInfo

sealed class PokemonInfoViewState{
    object Loading:PokemonInfoViewState()
    data class Success(val response: ResponsePokemonInfo):PokemonInfoViewState()
    data class Error(val error: String): PokemonInfoViewState()
    object PokemonNotFound: PokemonInfoViewState()
}
