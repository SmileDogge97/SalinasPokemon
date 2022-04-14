package com.example.salinaspokemon.framework.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.salinaspokemon.R
import com.example.salinaspokemon.databinding.FragmentPokemonInfoBinding
import com.example.salinaspokemon.framework.data.model.pokemoninfo.ResponsePokemonInfo
import com.example.salinaspokemon.framework.presentation.PokemonInfoViewModel
import com.example.salinaspokemon.framework.presentation.viewstate.PokemonInfoViewState
import com.example.salinaspokemon.utils.ValidarR
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonInfoFragment : Fragment() {

    private val pokemonViewModel: PokemonInfoViewModel by viewModels()
    private var _binding: FragmentPokemonInfoBinding? = null
    private val binding get() = _binding
    private lateinit var contexto: Context
    lateinit var vista: View
    private lateinit var response: ResponsePokemonInfo
    var nombrePokemon=""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPokemonInfoBinding.inflate(inflater, container, false)
        val view = binding?.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contexto = requireContext().applicationContext
        vista = view

        var pokemon = arguments?.getString("name")
        nombrePokemon = pokemon.toString()
        binding?.txtPokemon?.setText(pokemon)
        attachObservers()
        bindViews(pokemon!!)
    }

    private fun attachObservers() {
        pokemonViewModel.pokemonState.observe(viewLifecycleOwner, {
            renderPokemones(it)
        })
    }

    private fun bindViews(pokemon: String): Unit = with(binding) {
        if (ValidarR.hayRed(contexto)) {
            pokemonViewModel.loadPokemonInfo(pokemon)
        } else {
            displayError("No hay internet banda x.x")
        }

        binding?.btnHabilidades?.setOnClickListener{

            vista.let {
                val bundle = bundleOf("pokemon" to nombrePokemon)
                it.findNavController().navigate(R.id.action_pokemonInfoFragment_to_habilidadesFragment, bundle)
            }
        }

        binding?.btnLineaEvolutiva?.setOnClickListener{
            val bundle = bundleOf("lineaevolutiva" to response.evolution_chain.url)
            vista.let {
                it.findNavController().navigate(R.id.action_pokemonInfoFragment_to_lineaEvolutiva, bundle)
            }
        }
    }

    private fun showLoader() {
        with(binding) {
            this?.txtMessagePokeInfo?.visibility = View.GONE
            this?.txtColor?.visibility = View.GONE
            this?.txtFelicidad?.visibility = View.GONE
            this?.txtGrupoHuevos?.visibility = View.GONE
            this?.txtTasaCaptura?.visibility = View.GONE
            this?.pCargar?.visibility = View.VISIBLE
        }
    }

    private fun renderPokemones(it: PokemonInfoViewState?) {
        when (it) {
            is PokemonInfoViewState.Loading -> showLoader()
            is PokemonInfoViewState.Success -> {
                response= it.response
                hideLoader()
                diplayPokemon(it.response)
            }
            is PokemonInfoViewState.Error -> {
                hideLoader()
                showNotFoundMessage("Error al cargar los datos x.x")
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun diplayPokemon(response: ResponsePokemonInfo) {
        binding?.txtColor?.setText("Color: "+ response.color.name)
        binding?.txtFelicidad?.setText("Felicidad Base: "+response.base_happiness.toString())
        displayEggsGroup(response)
        binding?.txtTasaCaptura?.setText("Tasa de captura: "+response.capture_rate.toString())
    }

    private fun hideLoader() {
        with(binding){
            this?.txtMessagePokeInfo?.visibility = View.GONE
            this?.txtColor?.visibility = View.VISIBLE
            this?.txtFelicidad?.visibility = View.VISIBLE
            this?.txtGrupoHuevos?.visibility = View.VISIBLE
            this?.txtTasaCaptura?.visibility = View.VISIBLE
            this?.pCargar?.visibility = View.GONE
        }
    }

    private fun displayError(message: String){
        with(binding){
            this?.txtMessagePokeInfo?.visibility = View.VISIBLE
            this?.txtMessagePokeInfo?.setText(message)
            this?.txtFelicidad?.visibility = View.GONE
            this?.txtTasaCaptura?.visibility = View.GONE
            this?.txtColor?.visibility = View.GONE
            this?.txtGrupoHuevos?.visibility = View.GONE
            this?.pCargar?.visibility = View.GONE
        }
    }

    private fun showNotFoundMessage(s: String) {
        with(binding){
            this?.txtMessagePokeInfo?.visibility = View.VISIBLE
            this?.txtMessagePokeInfo?.setText(s)
            this?.txtColor?.visibility = View.GONE
            this?.txtFelicidad?.visibility = View.GONE
            this?.txtGrupoHuevos?.visibility = View.GONE
            this?.txtTasaCaptura?.visibility = View.GONE
            this?.pCargar?.visibility = View.GONE
        }
    }

    @SuppressLint("SetTextI18n")
    private fun displayEggsGroup(response: ResponsePokemonInfo) {
        var huevos = ""
        for (i: Int in 0..response.egg_groups.size-1) {
            Log.d("PokemonInfoFragment/displayEggsGroup/position, huevo", "$i, ${response.egg_groups.get(i).name}")
            if(i<(response.egg_groups.size-1)){
                huevos += response.egg_groups.get(i).name + ", "
            } else {
                huevos += response.egg_groups.get(i).name
            }
            Log.d("PokemonInfoFragment/displayEggsGroup/huevos", huevos)
        }
        binding?.txtGrupoHuevos?.setText("Grupo de huevos: "+huevos)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}