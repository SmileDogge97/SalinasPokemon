package com.example.salinaspokemon.framework.data.model.lineaevolutiva

data class Chain(
    val evolution_details: List<Any>,
    val evolves_to: List<EvolvesTo>,
    val is_baby: Boolean,
    val species: SpeciesXX
)