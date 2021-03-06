package com.example.salinaspokemon.framework.di

import android.content.Context
import com.example.salinaspokemon.data.datasource.PokemonesDataSource
import com.example.salinaspokemon.data.datasource.db.PokemonDao
import com.example.salinaspokemon.data.datasource.db.PokemonDatabase
import com.example.salinaspokemon.data.datasource.db.PokemonesDataSourceBD
import com.example.salinaspokemon.data.datasource.db.PokemonesDataSourceBDImpl
import com.example.salinaspokemon.data.repository.PokemonesRepositoryDBImpl
import com.example.salinaspokemon.data.repository.PokemonesRepositoryImpl
import com.example.salinaspokemon.domain.repository.PokemonesRepository
import com.example.salinaspokemon.domain.repository.PokemonesRepositoryDB
import com.example.salinaspokemon.domain.usecase.*
import com.example.salinaspokemon.framework.data.datasource.PokemonEndPoint
import com.example.salinaspokemon.framework.data.datasource.PokemonesDataSourceImpl
import com.example.salinaspokemon.framework.data.model.habilidades.ResponseHabilidades
import com.example.salinaspokemon.framework.data.model.lineaevolutiva.ResponseLineaEvolutiva
import com.example.salinaspokemon.framework.data.model.pokemones.ResponsePokemones
import com.example.salinaspokemon.framework.data.model.pokemoninfo.ResponsePokemonInfo
import com.example.salinaspokemon.framework.usecase.UseCase
import com.example.salinaspokemon.framework.usecase.UseCaseDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers.IO
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

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
    @Singleton
    fun providesPokemonesEndPoint(
        retrofit: Retrofit
    ): PokemonEndPoint =
        retrofit.create(PokemonEndPoint::class.java)

    @Provides
    fun providesPokemonesDatabase(@ApplicationContext context: Context): PokemonDatabase =
        PokemonDatabase.create(context)

    @Provides
    @Singleton
    fun providesPokemonesDao(db: PokemonDatabase): PokemonDao = db.pokemonDao()

    @Provides
    @Singleton
    fun providesPokemonesDatasourceBD(
        pokemonDao: PokemonDao
    ): PokemonesDataSourceBD = PokemonesDataSourceBDImpl(pokemonDao)

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
    fun providesPokemonesRepositoryDB(
        datasourceDB: PokemonesDataSourceBD
    ): PokemonesRepositoryDB = PokemonesRepositoryDBImpl(datasourceDB)

    @Provides
    fun providesPokemonesUseCaseDBProvider(
        pokemonesRepositoryDB: PokemonesRepositoryDB
    ) : UseCaseDB = PokemonesUseCaseDBImpl(pokemonesRepositoryDB)

    @Provides
    fun providesPokemonesUseCaseProvider(
        pokemonesRepository: PokemonesRepository
    ): UseCase<PokemonesUseCase.Params, Response<ResponsePokemones>> =
        PokemonesUseCase(pokemonesRepository)

    @Provides
    fun providesPokemonInfoUseCaseProvider(
        pokemonesRepository: PokemonesRepository
    ): UseCase<PokemonInfoUseCase.Params, Response<ResponsePokemonInfo>> =
        PokemonInfoUseCase(pokemonesRepository)

    @Provides
    fun providesPokemonEvoLinUseCaseProvider(
        pokemonesRepository: PokemonesRepository
    ): UseCase<PokemonLineEvoUseCase.Params, Response<ResponseLineaEvolutiva>> =
        PokemonLineEvoUseCase(pokemonesRepository)

    @Provides
    fun providesPokemonHabilidadesUseCaseProvider(
        pokemonRepository: PokemonesRepository
    ): UseCase<PokemonHabilidadesUseCase.Params, Response<ResponseHabilidades>> =
        PokemonHabilidadesUseCase(pokemonRepository)
}