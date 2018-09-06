package com.qgdostark.pokedex.model;

import com.qgdostark.pokedex.model.Pokedex;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by mrcs on 14/03/18.
 */

public interface PokemonService {


    @GET("pokemon")
    Observable<Pokedex> getListaPokemon(@Query("limit")int limit, @Query("offset")int offset);

    @GET("pokemon/{id}")
    Call<Pokemon> getPokemon(@Path("id") int id);
}
