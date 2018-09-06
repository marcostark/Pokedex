package com.qgdostark.pokedex.model.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.qgdostark.pokedex.R;
import com.qgdostark.pokedex.model.Pokemon;

import java.util.ArrayList;
import java.util.List;

import static com.qgdostark.pokedex.model.util.Utils.adicionaZeros;

/**
 * Created by Marcos on 14/03/18.
 */

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.ViewHolder> {

    private ArrayList<Pokemon> pokemons;
    private Context context;

    public PokemonAdapter(Context context) {
        this.context = context;
        pokemons = new ArrayList<>();
    }

    @Override
    public PokemonAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_pokemon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PokemonAdapter.ViewHolder holder, int position) {
        Pokemon p = pokemons.get(position);
        String upperString = p.getName().substring(0,1).toUpperCase() + p.getName().substring(1);
        holder.namePokemon.setText(upperString);

        String number = String.valueOf(adicionaZeros(p.getNumber()));
        holder.numberPokemon.setText("#" + number);

        Glide.with(context)
                .load("http://pokeapi.co/media/sprites/pokemon/" + p.getNumber() + ".png")
                .apply(new RequestOptions()
                        .placeholder(R.drawable.pokeball))
                .into(holder.thumbPokemon);
    }

    public void setFilter(List<Pokemon> listPokemons){
        pokemons = new ArrayList<>();
        pokemons.addAll(pokemons);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return pokemons.size();
    }

    public void adicionarPokemons(List<Pokemon> listaPokemon) {
        pokemons.addAll(listaPokemon);
        notifyDataSetChanged();
    }

    public void reset() {
        pokemons.clear();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView thumbPokemon;
        private TextView numberPokemon;
        private TextView namePokemon;

        public ViewHolder(View itemView) {
            super(itemView);

            thumbPokemon = itemView.findViewById(R.id.thumbPokemon);
            numberPokemon = itemView.findViewById(R.id.numberPokemon);
            namePokemon = itemView.findViewById(R.id.namePokemon);

        }
    }
}

