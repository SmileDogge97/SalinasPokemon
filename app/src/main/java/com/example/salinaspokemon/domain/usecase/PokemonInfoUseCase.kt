package com.example.salinaspokemon.domain.usecase

import com.example.salinaspokemon.domain.repository.PokemonesRepository
import com.example.salinaspokemon.framework.data.model.pokemones.ResponsePokemones
import com.example.salinaspokemon.framework.data.model.pokemoninfo.ResponsePokemonInfo
import com.example.salinaspokemon.framework.usecase.UseCase
import retrofit2.Response
import javax.inject.Inject

class PokemonInfoUseCase @Inject constructor(private val pokemonesRepository: PokemonesRepository) :
    UseCase<PokemonInfoUseCase.Params, Response<ResponsePokemonInfo>>() {

    override suspend fun request(params: Params): Response<ResponsePokemonInfo> {
        return params.let {
            pokemonesRepository.attemptLoadPokemonInfo(params.pokemon)
        }
    }

    data class Params(
        val pokemon: String
    )
}