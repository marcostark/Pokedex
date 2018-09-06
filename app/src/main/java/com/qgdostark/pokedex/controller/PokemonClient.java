package com.qgdostark.pokedex.controller;

import com.qgdostark.pokedex.model.PokemonService;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mrcs on 19/03/18.
 */

public class PokemonClient {

    private PokemonService mPokemonService;
    private final String BASE_URL = "http://pokeapi.co/api/v2/";

    public PokemonService getPokemonService() {
        if (mPokemonService == null) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL )
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            mPokemonService = retrofit.create(PokemonService.class);
        }
        return mPokemonService;
    }
}
