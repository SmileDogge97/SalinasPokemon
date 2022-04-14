package com.example.salinaspokemon.framework.presentation.viewstate

import com.example.salinaspokemon.framework.data.model.habilidades.Ability
import com.example.salinaspokemon.framework.data.model.lineaevolutiva.Species

sealed class PokemonHabilidadesViewState {
    object Loading:PokemonHabilidadesViewState()
    data class Success(val response: MutableList<Ability>):PokemonHabilidadesViewState()
    data class Error(val error: String): PokemonHabilidadesViewState()
    object PokemonNotFound: PokemonHabilidadesViewState()
}
