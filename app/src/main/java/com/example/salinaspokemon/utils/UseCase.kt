package com.example.salinaspokemon.utils

import com.example.salinaspokemon.data.datasource.db.Pokemon
import com.example.salinaspokemon.domain.usecase.PokemonesUseCase
import com.example.salinaspokemon.framework.data.model.ResponsePokemones
import retrofit2.Response

abstract class UseCase<Params, ReturnValue> {

    abstract suspend fun request(params: PokemonesUseCase.Params): Response<ResponsePokemones>
    abstract suspend fun getAllBD(): List<Pokemon>
    abstract suspend fun countPokemonBD(): Int
    abstract suspend fun deleteBD(pokemon: Pokemon)
    abstract suspend fun loadAllByIdsBD(id: IntArray): List<Pokemon>
    abstract suspend fun findByNameBD(name: String): Pokemon
    abstract suspend fun insertAllBD(pokemon: Pokemon)
}