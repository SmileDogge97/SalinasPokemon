package com.example.salinaspokemon.utils

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import com.example.salinaspokemon.databinding.FragmentPokemonesBinding
import com.example.salinaspokemon.framework.ui.PokemonesFragment


class ValidarR {
    companion object{
        fun hayRed(activity: Context):Boolean{
            val connectivityManager = activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }
    }
}