package com.example.salinaspokemon.domain.usecase

import com.example.salinaspokemon.data.datasource.db.Pokemon
import com.example.salinaspokemon.domain.repository.PokemonesRepository
import com.example.salinaspokemon.framework.data.model.ResponsePokemones
import com.example.salinaspokemon.utils.UseCase
import retrofit2.Response
import javax.inject.Inject

class PokemonesUseCase @Inject constructor(private val pokemonesRepository: PokemonesRepository) :
    UseCase<PokemonesUseCase.Params, Response<ResponsePokemones>>() {

    override suspend fun request(params: Params): Response<ResponsePokemones> {
        params.let {
            return pokemonesRepository.attemptLoadPokemones(params.limit)
        }
    }

    override suspend fun getAllBD(): List<Pokemon> {
        return pokemonesRepository.getAllBDPokemones()
    }

    override suspend fun loadAllByIdsBD(id:IntArray): List<Pokemon> {
            return pokemonesRepository.loadAllByIdBDPokemones(id)
    }

    override suspend fun findByNameBD(name: String): Pokemon{
            return pokemonesRepository.findByNameBDPokemones(name)
    }

    override suspend fun countPokemonBD(): Int{
        return pokemonesRepository.countPokemonBD()
    }

    override suspend fun insertAllBD(pokemon: Pokemon){
            return pokemonesRepository.insertAllBD(pokemon)
    }

    override suspend fun deleteBD(pokemon: Pokemon){
        return pokemonesRepository.deleteBD(pokemon)
    }

    data class Params(
        val limit: Int,
    )
}