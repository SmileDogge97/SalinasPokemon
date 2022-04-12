package com.example.salinaspokemon.framework.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.salinaspokemon.R
import com.example.salinaspokemon.databinding.ItemPokemonesListBinding
import com.example.salinaspokemon.framework.data.model.Result

class PokemonesListAdapter(
    private val pokemones: MutableList<Result> = mutableListOf(),
    private val onPokemonesSelected: (Result) -> Unit
) : RecyclerView.Adapter<PokemonesListAdapter.PokemonesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonesViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_pokemones_list, parent, false)
        return PokemonesViewHolder(view)
    }

    override fun getItemCount(): Int = pokemones.size

    override fun onBindViewHolder(holder: PokemonesViewHolder, position: Int) =
        holder.bindView(pokemones[position], onPokemonesSelected)

    fun updateDataSet(pokemones: MutableList<Result>){
        this.pokemones.clear()
        this.pokemones.addAll(pokemones)
        notifyDataSetChanged()
    }

    class PokemonesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemPokemonesListBinding.bind(itemView)

        fun bindView(pokemones: Result, onPokemonesSelected: (Result) -> Unit){
            with(pokemones){
                if (name.isNotEmpty()){
                    binding.txtNombre.text= name
                    binding.root.setOnClickListener{
                        onPokemonesSelected(this)
                    }
                }
            }
        }
    }
}
