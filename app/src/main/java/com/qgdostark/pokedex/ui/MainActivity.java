package com.qgdostark.pokedex.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.qgdostark.pokedex.R;
import com.qgdostark.pokedex.controller.PokemonClient;
import com.qgdostark.pokedex.model.adapter.PokemonAdapter;
import com.qgdostark.pokedex.model.Pokedex;
import com.qgdostark.pokedex.model.Pokemon;
import com.qgdostark.pokedex.model.util.Utils;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "POKEDEX";

    private RecyclerView recyclerView;
    private PokemonAdapter pokemonAdapter;
    private List<Pokemon> listaPokemon;

    private boolean isLoad;

    private PokemonClient pokemonRest;

    private ProgressDialog progressDialog;

    private int offset;
    private List<Pokemon> listaPokemonDAO;
    private int qnt_pokemon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv);
        pokemonAdapter = new PokemonAdapter(this);
        recyclerView.setAdapter(pokemonAdapter);
        recyclerView.setHasFixedSize(true);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);

//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
//        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));

        //Barra de rolagem
        if (getStateNetwork()) {
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    if (dy > 0) {
                        int visibleItemCount = gridLayoutManager.getChildCount();
                        int totalItemCount = gridLayoutManager.getItemCount();
                        int pastVisibleItems = gridLayoutManager.findFirstVisibleItemPosition();


                        if (isLoad) {
                            if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                                Log.i(TAG, "Chegou ao final");

                                isLoad = false;
                                offset += 20;
                                getListPokemons(offset);
                            }
                        }
                    }
                }
            });
        }

        pokemonRest = new PokemonClient();
        //carregarAPI();
        loadData();
        isLoad = true;
    }

    public boolean getStateNetwork() {
        return Utils.isOnline(getContext());
    }

    private void loadData(){

        offset = 0;

        if (getStateNetwork()) {

            // Inicia o progresso antes da chamada
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Downloading....");
            progressDialog.setTitle("Pokedex");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
            progressDialog.setCancelable(false);
            getListPokemons(offset);

        } else {
            //pokemonAdapter.adicionarPokemons(listaPokemon);
            //getPokemonsFromDB();
            Toast.makeText(this, "Sem Conexão!", Toast.LENGTH_SHORT).show();
        }
    }


    private void getListPokemons(final int offset){
        Observable<Pokedex> observable = pokemonRest.getPokemonService()
                .getListaPokemon(20,offset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        observable.subscribe(new Observer<Pokedex>() {
            @Override
            public void onSubscribe(Disposable d) {}

            @Override
            public void onNext(Pokedex pokedex) {
                //isLoad = true;
                listaPokemon = pokedex.getResults();

//                    for (int i = 0; i < listaPokemon.size(); i++) {
//
//                        Pokemon poke = listaPokemon.get(i);
//                        Log.i(TAG, "Pokemon: " + poke.getName());
//
//                        if (offset >= qnt_pokemon) {
//
//                            //Toast.makeText(MainActivity.this, "Baixando novos pokemons", Toast.LENGTH_SHORT).show();
////                            SaveIntoDatabase task = new SaveIntoDatabase();
////                            task.execute(poke);
//                        }
//                        //pokemonAdapter.adicionarPokemons(listaPokemon);
//                    }
                    pokemonAdapter.adicionarPokemons(listaPokemon);
            }

            @Override
            public void onError(Throwable e) {
//                Log.d(TAG, e.getMessage());
                isLoad = true;
                progressDialog.dismiss();
            }

            @Override
            public void onComplete() {
                isLoad = true;
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }

    public Context getContext() {
        return this;
    }

}

