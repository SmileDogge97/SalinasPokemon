package com.example.salinaspokemon.framework.data.datasource

import com.example.salinaspokemon.data.datasource.PokemonesDataSource
import com.example.salinaspokemon.data.datasource.db.Pokemon
import com.example.salinaspokemon.data.datasource.db.PokemonDao
import com.example.salinaspokemon.framework.data.model.ResponsePokemones
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class PokemonesDataSourceImpl @Inject constructor(
    private val endPoint: PokemonEndPoint,
    private val coroutineContext: CoroutineContext
) : PokemonesDataSource {
    override suspend fun getPokemones(limit: Int): Response<ResponsePokemones> =
        withContext(coroutineContext) {
            val response = endPoint.getPokemones(limit)
            return@withContext response
        }
}