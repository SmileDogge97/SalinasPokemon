package com.example.salinaspokemon.framework.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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
                hideLoader()
                diplayPokemon(it.response)
            }
            is PokemonInfoViewState.Error -> {
                hideLoader()
                showNotFoundMessage("Error al cargar los datos x.x")
            }
        }
    }

    private fun diplayPokemon(response: ResponsePokemonInfo) {
        binding?.txtColor?.append(response.color.name)
        binding?.txtFelicidad?.append(response.base_happiness.toString())
        displayEggsGroup(response)
        binding?.txtTasaCaptura?.append(response.capture_rate.toString())
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
        binding?.txtGrupoHuevos?.append(huevos)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}