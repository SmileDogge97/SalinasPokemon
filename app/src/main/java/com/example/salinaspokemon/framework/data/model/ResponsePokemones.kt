package com.example.salinaspokemon.framework.data.model

data class ResponsePokemones(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<Result>
)