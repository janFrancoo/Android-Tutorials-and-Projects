package com.janfranco.cryptomvp.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.janfranco.cryptomvp.R;
import com.janfranco.cryptomvp.model.Coin;
import com.janfranco.cryptomvp.model.Data;

import java.util.ArrayList;
import java.util.List;

public class CoinListRVAdapter extends RecyclerView.Adapter<CoinListRVAdapter.ViewHolder> {

    private final List<Coin> mCoins;

    public CoinListRVAdapter() {
        mCoins = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.coin_list_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Coin coin = mCoins.get(position);
        holder.coinName.setText(coin.name);
        holder.coinPrice.setText(coin.price);
    }

    @Override
    public int getItemCount() {
        return mCoins.size();
    }

    public void setCoins(List<Coin> coins) {
        mCoins.addAll(coins);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView coinName, coinPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            coinName = itemView.findViewById(R.id.coin_list_row_coin_name);
            coinPrice = itemView.findViewById(R.id.coin_list_row_coin_price);
        }
    }

}
