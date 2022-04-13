package com.example.salinaspokemon.framework.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.salinaspokemon.R
import com.example.salinaspokemon.databinding.FragmentPokemonesBinding
import com.example.salinaspokemon.framework.data.model.pokemones.Result
import com.example.salinaspokemon.framework.presentation.PokemonesViewModel
import com.example.salinaspokemon.framework.presentation.viewstate.PokemonesViewState
import com.example.salinaspokemon.framework.ui.adapter.PokemonesListAdapter
import com.example.salinaspokemon.utils.ValidarR
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonesFragment : Fragment() {

    private val pokemonesViewModel: PokemonesViewModel by viewModels()
    private var _binding: FragmentPokemonesBinding? = null
    private val binding get() = _binding!!
    private lateinit var pokemonesAdapter: PokemonesListAdapter
    private lateinit var contexto: Context
    lateinit var vista:View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPokemonesBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contexto = requireContext().applicationContext
        vista=view

        attachObservers()
        bindViews()
    }

    private fun attachObservers() {
        pokemonesViewModel.pokemonesState.observe(viewLifecycleOwner, {
            renderPokemones(it)
        })
    }

    private fun bindViews(): Unit = with(binding) {
        if (ValidarR.hayRed(contexto)) {
            pokemonesViewModel.loadPokemones()
        } else {
            pokemonesViewModel.loadPokemonesBD()
        }

        pokemonesAdapter = PokemonesListAdapter(onPokemonesSelected = { result ->
            val bundle = bundleOf("name" to result.name)
            vista.let {
                it.findNavController().navigate(R.id.action_pokemonesFragment_to_pokemonInfoFragment, bundle)
            }
        })

        binding.list.apply {
            adapter = pokemonesAdapter
            layoutManager = LinearLayoutManager(contexto, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun showLoader() {
        with(binding) {
            txtMessage.visibility = View.GONE
            list.visibility = View.GONE
            pCargar.visibility = View.VISIBLE
        }
    }

    private fun renderPokemones(it: PokemonesViewState?) {
        when (it) {
            is PokemonesViewState.Loadig -> showLoader()
            is PokemonesViewState.Success -> {
                hideLoader()
                displayPokemones(it.list)
            }
            is PokemonesViewState.Error -> {
                hideLoader()
                showNotFoundMessage("Error al cargar datos x.x")
            }
        }
    }

    private fun displayPokemones(list: List<Result>) {
        pokemonesAdapter.updateDataSet(list.toMutableList())
    }

    private fun hideLoader() {
        with(binding) {
            list.visibility = View.VISIBLE
            pCargar.visibility = View.GONE
            txtMessage.visibility = View.GONE
        }
    }

    private fun showNotFoundMessage(message: String) {
        with(binding) {
            list.visibility = View.GONE
            pCargar.visibility = View.GONE
            txtMessage.visibility = View.VISIBLE
            txtMessage.setText(message)
        }
    }
}