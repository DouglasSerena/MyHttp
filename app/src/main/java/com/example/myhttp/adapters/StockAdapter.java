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
import com.example.myhttp.models.Stock;

import java.util.List;

public class StockAdapter extends ArrayAdapter<Stock> {

    public StockAdapter(@NonNull Context context, @NonNull List<Stock> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Stock stock = getItem(position);
        View listaCurrencies=convertView;
        if(convertView==null){
            listaCurrencies= LayoutInflater.from(getContext()).inflate(R.layout.stock_item,null);
        }
        TextView name = listaCurrencies.findViewById(R.id.textName);
        TextView textLocation = listaCurrencies.findViewById(R.id.textLocation);
        TextView textPoints = listaCurrencies.findViewById(R.id.textPoints);
        TextView textVariable = listaCurrencies.findViewById(R.id.textVariable);
        name.setText(stock.getName());
        textLocation.setText(stock.getLocation());
        textVariable.setText(stock.getVariation());


        return listaCurrencies;
    }
}
