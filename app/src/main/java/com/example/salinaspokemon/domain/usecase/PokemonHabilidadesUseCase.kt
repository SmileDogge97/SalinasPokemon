package com.example.salinaspokemon.domain.usecase

import com.example.salinaspokemon.domain.repository.PokemonesRepository
import com.example.salinaspokemon.framework.data.model.habilidades.ResponseHabilidades
import com.example.salinaspokemon.framework.data.model.lineaevolutiva.ResponseLineaEvolutiva
import com.example.salinaspokemon.framework.usecase.UseCase
import retrofit2.Response
import javax.inject.Inject

class PokemonHabilidadesUseCase @Inject constructor(private val pokemonesRepository: PokemonesRepository) :
    UseCase<PokemonHabilidadesUseCase.Params, Response<ResponseHabilidades>>() {

    override suspend fun request(params: Params): Response<ResponseHabilidades> {
        return params.let {
            pokemonesRepository.attemptLoadHabilidades(params.pokemon)
        }
    }

    data class Params(
        val pokemon: String
    )
}