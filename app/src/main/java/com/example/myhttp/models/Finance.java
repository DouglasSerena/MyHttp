package com.example.myhttp.models;

import java.util.ArrayList;

public class Finance {
    ArrayList<Currencies> currencies = new ArrayList<>();
    ArrayList<Stock> stocks = new ArrayList<>();

    public void setCurrencies(ArrayList<Currencies> currencies) {
        this.currencies = currencies;
    }

    public void setStocks(ArrayList<Stock> stocks) {
        this.stocks = stocks;
    }

    public ArrayList<Currencies> getCurrencies() {
        return currencies;
    }

    public ArrayList<Stock> getStocks() {
        return stocks;
    }
}
