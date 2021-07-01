package com.example.myhttp.models;

public class Stock {
    String name;
    String location;
    double points;
    double variation;

    public Stock(
            String name,
            String location,
            double points,
            double variation
    ) {
        this.name = name;
        this.location = location;
        this.points = points;
        this.variation = variation;
    }

    public String getName() {
        return name;
    }

    public String getPoints() {
        return String.valueOf(points);
    }

    public String getVariation() {
        return String.valueOf(variation);
    }

    public String getLocation() {
        return location;
    }
}
