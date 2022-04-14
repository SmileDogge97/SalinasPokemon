package com.example.salinaspokemon.framework.data.datasource

import com.example.salinaspokemon.data.datasource.PokemonesDataSource
import com.example.salinaspokemon.framework.data.model.lineaevolutiva.ResponseLineaEvolutiva
import com.example.salinaspokemon.framework.data.model.pokemones.ResponsePokemones
import com.example.salinaspokemon.framework.data.model.pokemoninfo.ResponsePokemonInfo
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

    override suspend fun getPokemonInfo(pokemon:String): Response<ResponsePokemonInfo> =
        withContext(coroutineContext){
            val response = endPoint.getPokemonInfo(pokemon)
            return@withContext response
        }

    override suspend fun getLineaEvolutiva(url: String): Response<ResponseLineaEvolutiva> =
        withContext(coroutineContext){
            val response = endPoint.getLineaEvolutiva(url)
            return@withContext response
        }
}