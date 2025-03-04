package com.example.myhttp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myhttp.R;
import com.example.myhttp.models.Currencies;

import java.util.List;

public class CurrenciesAdapter extends ArrayAdapter<Currencies> {

    public CurrenciesAdapter(@NonNull Context context,  @NonNull List<Currencies> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Currencies coin= getItem(position);
        View listaCurrencies=convertView;
        if(convertView==null){
            listaCurrencies= LayoutInflater.from(getContext()).inflate(R.layout.currencies_item,null);
        }
        TextView name = listaCurrencies.findViewById(R.id.textName);
        TextView txtBuy = listaCurrencies.findViewById(R.id.textLocation);
        TextView txtSell = listaCurrencies.findViewById(R.id.textPoints);
        TextView variacao = listaCurrencies.findViewById(R.id.textVariable);
        name.setText(coin.getName());
            txtBuy.setText(coin.getBuy());
            txtSell.setText(coin.getSell());
            variacao.setText(coin.getVariation());


        return listaCurrencies;
    }
}
