package com.janfranco.rxjavaretrofitexample;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CoinListRecyclerViewAdapter extends RecyclerView.Adapter<CoinListRecyclerViewAdapter.ViewHolder> {

    private final List<Crypto.Market> marketList;

    public CoinListRecyclerViewAdapter() {
        marketList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.coin_row_for_coin_list, parent, false);
        return new CoinListRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Crypto.Market market = marketList.get(position);

        holder.coinName.setText(market.coinName);
        holder.coinMarket.setText(market.market);
        holder.coinPrice.setText(market.price);
    }

    @Override
    public int getItemCount() {
        return marketList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView coinName, coinMarket, coinPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            coinName = itemView.findViewById(R.id.coin_row_coin_name);
            coinMarket = itemView.findViewById(R.id.coin_row_coin_market);
            coinPrice = itemView.findViewById(R.id.coin_row_coin_price);
        }

    }

    public void setData(List<Crypto.Market> marketList) {
        this.marketList.addAll(marketList);
        notifyDataSetChanged();
    }

}
