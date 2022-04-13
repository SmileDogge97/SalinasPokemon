package com.example.salinaspokemon.framework.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.salinaspokemon.R
import com.example.salinaspokemon.databinding.FragmentPokemonInfoBinding
import com.example.salinaspokemon.databinding.FragmentPokemonesBinding

class PokemonInfoFragment : Fragment() {

    private var _binding: FragmentPokemonInfoBinding ?= null
    private val binding get() = _binding

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

        binding?.txtMessage?.text= arguments?.getString("name")
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}