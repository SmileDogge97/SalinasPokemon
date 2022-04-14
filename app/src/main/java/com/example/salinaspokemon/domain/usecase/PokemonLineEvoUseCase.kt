package com.example.salinaspokemon.domain.usecase

import com.example.salinaspokemon.domain.repository.PokemonesRepository
import com.example.salinaspokemon.framework.data.model.lineaevolutiva.ResponseLineaEvolutiva
import com.example.salinaspokemon.framework.usecase.UseCase
import retrofit2.Response
import javax.inject.Inject

class PokemonLineEvoUseCase @Inject constructor(private val pokemonesRepository: PokemonesRepository) :
    UseCase<PokemonLineEvoUseCase.Params, Response<ResponseLineaEvolutiva>>() {

    override suspend fun request(params: Params): Response<ResponseLineaEvolutiva> {
        return params.let {
            pokemonesRepository.attemptLoadPokemonLineEvo(params.url)
        }
    }

    data class Params(
        val url: String
    )
}