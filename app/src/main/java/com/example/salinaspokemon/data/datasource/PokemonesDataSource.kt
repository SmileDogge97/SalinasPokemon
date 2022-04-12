package com.example.salinaspokemon.data.datasource

import com.example.salinaspokemon.data.datasource.db.Pokemon
import com.example.salinaspokemon.framework.data.model.ResponsePokemones
import retrofit2.Response
import javax.inject.Inject

interface PokemonesDataSource {
    suspend fun getPokemones(limit: Int): Response<ResponsePokemones>
}