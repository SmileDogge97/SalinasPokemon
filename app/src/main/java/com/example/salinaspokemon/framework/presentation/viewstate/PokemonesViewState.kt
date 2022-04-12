package com.example.salinaspokemon.framework.presentation.viewstate

import com.example.salinaspokemon.framework.data.model.Result

sealed class PokemonesViewState{
    object Loadig:PokemonesViewState()
    data class Success(val list: List<Result>):PokemonesViewState()
    data class Error(val error: String): PokemonesViewState()
    object PokemonesNotFound: PokemonesViewState()

}
