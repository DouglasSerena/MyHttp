package com.example.myhttp.models;

import java.io.Serializable;

public class Currencies implements Serializable {


    private String name;
    private Double buy;
    private Double sell;
    private Double variation;

    public Currencies(String name, double buy, double sell, double variation) {
        this.name = name;
        this.buy = buy;
        this.sell = sell;
        this.variation = variation;
    }

    public String getName() {
        return name;
    }

    public String getBuy() {
        return "Comprar R$ " + buy.toString();
    }

    public String getSell() {
        return "R$ " + sell + " Vender";

    }

    public String getVariation() {
        return variation.toString();
    }
}
