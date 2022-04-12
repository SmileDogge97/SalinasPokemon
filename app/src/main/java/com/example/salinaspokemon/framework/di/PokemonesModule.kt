package com.example.salinaspokemon.framework.di

import androidx.core.content.pm.PermissionInfoCompat
import com.example.salinaspokemon.data.datasource.PokemonesDataSource
import com.example.salinaspokemon.data.repository.PokemonesRepositoryImpl
import com.example.salinaspokemon.domain.repository.PokemonesRepository
import com.example.salinaspokemon.domain.usecase.PokemonesUseCase
import com.example.salinaspokemon.framework.data.datasource.PokemonEndPoint
import com.example.salinaspokemon.framework.data.datasource.PokemonesDataSourceImpl
import com.example.salinaspokemon.framework.data.model.ResponsePokemones
import com.example.salinaspokemon.utils.UseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers.IO
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@InstallIn(SingletonComponent::class)
@Module
object PokemonesModule {
    const val BASE_URL = "https://pokeapi.co"

    @Provides
    fun providesRetrofit(): Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Provides
    fun providesPokemonesEndPoint(
        retrofit: Retrofit
    ): PokemonEndPoint =
        retrofit.create(PokemonEndPoint::class.java)

    @Provides
    fun providesPokemonesDatasource(
        endPoint: PokemonEndPoint
    ): PokemonesDataSource =
        PokemonesDataSourceImpl(endPoint, IO)

    @Provides
    fun providesPokemonesRepository(
        datasource: PokemonesDataSource
    ): PokemonesRepository =
        PokemonesRepositoryImpl(datasource)

    @Provides
    fun providesPokemonesUseCaseProvider(
        pokemonesRepository: PokemonesRepository
    ): UseCase<PokemonesUseCase.Params, Response<ResponsePokemones>> =
        PokemonesUseCase(pokemonesRepository)
}