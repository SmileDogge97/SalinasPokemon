package com.example.salinaspokemon.framework.usecase

import com.example.salinaspokemon.domain.usecase.PokemonInfoUseCase
import com.example.salinaspokemon.domain.usecase.PokemonesUseCase
import com.example.salinaspokemon.framework.data.model.pokemoninfo.ResponsePokemonInfo
import retrofit2.Response

abstract class UseCase<Params, ReturnValue> {
    abstract suspend fun request(params: Params): ReturnValue
}