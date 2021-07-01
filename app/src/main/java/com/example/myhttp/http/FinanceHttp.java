package com.example.myhttp.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.example.myhttp.models.Currencies;
import com.example.myhttp.models.Finance;
import com.example.myhttp.models.Stock;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class FinanceHttp {
    public static Finance finance = new Finance();
    public static String URL = "https://api.hgbrasil.com/finance/quotations";

    private static HttpURLConnection conectar(String urlWeb) {
        final int SEGUNDOS = 10000;
        try {
            java.net.URL url = new URL(urlWeb);
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
            conexao.setReadTimeout(10 * 10000);
            conexao.setConnectTimeout(15 * 10000);
            conexao.setRequestMethod("GET");
            conexao.setDoInput(true);
            conexao.setDoOutput(false);
            conexao.connect();
            return conexao;
        } catch (Exception e) {
            Log.d("URL ERROR", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public static boolean hasConexao(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }

    public static Finance loadFinacne() {
        boolean res = load();
        if (res) {
            return finance;
        }
        return null;
    }

    public static boolean load() {
        try {
            HttpURLConnection connection = conectar(URL);
            if (connection != null) {


                int response = connection.getResponseCode();
                if (response == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = new BufferedInputStream(connection.getInputStream());
                    //       InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                    JSONObject jsonObject = new JSONObject(bytesPraString(inputStream));
                    //chegou JSON
                    finance.setCurrencies(readJsonCurrencie(jsonObject));
                    finance.setStocks(readJsonStock(jsonObject));
                    return true;
                }
            }
        } catch (IOException | JSONException e) {

            Log.d("JSON", e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public static ArrayList<Stock> readJsonStock(JSONObject json) {
        ArrayList<Stock> array = new ArrayList<>();
        try {
            JSONObject results = json.getJSONObject("results");
            JSONObject jsonStocks = results.getJSONObject("stocks");
            JSONObject IBOVESPA = jsonStocks.getJSONObject("IBOVESPA");
            JSONObject NASDAQ = jsonStocks.getJSONObject("NASDAQ");
            JSONObject CAC = jsonStocks.getJSONObject("CAC");
            array.add(getStockFromJson(IBOVESPA));
            array.add(getStockFromJson(NASDAQ));
            array.add(getStockFromJson(CAC));
        } catch (JSONException e) {
            Log.d("JSON", e.getMessage());
        }
        return array;
    }

    public static Stock getStockFromJson(JSONObject json) {
        String name = "";
        String location = "";
        double points = 0.0;
        double variation = 0.0;

        Stock stock = null;

        try {
            if (!json.isNull("name")) {
                name = json.getString("name");
            }
            if (!json.isNull("location")) {
                location = json.getString("location");
            }
            if (!json.isNull("points")) {
                points = json.getDouble("points");
            }
            if (!json.isNull("variation")) {
                variation = json.getDouble("variation");
            }
            stock = new Stock(name, location, points, variation);
        } catch (JSONException e) {
            Log.e("Log", e.getMessage());
        }
        return stock;

    }

    public static ArrayList<Currencies> readJsonCurrencie(JSONObject json) {
        ArrayList<Currencies> array = new ArrayList<>();
        try {
            JSONObject results = json.getJSONObject("results");
            JSONObject jsonCurrencies = results.getJSONObject("currencies");
            JSONObject btc = jsonCurrencies.getJSONObject("BTC");
            JSONObject eur = jsonCurrencies.getJSONObject("EUR");
            JSONObject usd = jsonCurrencies.getJSONObject("USD");
            array.add(getCurrenciesFromJson(usd));
            array.add(getCurrenciesFromJson(eur));
            array.add(getCurrenciesFromJson(btc));


        } catch (JSONException e) {
            Log.d("JSON", e.getMessage());
        }
        return array;
    }


    public static Currencies getCurrenciesFromJson(JSONObject json) {
        String name;
        Double buy;
        Double sell;
        double variation;
        Currencies coin = null;
        try {
            name = json.getString("name");
            buy = json.getDouble("buy");
            sell = json.getDouble("sell");
            variation = json.getDouble("variation");
            coin = new Currencies(name, buy, sell, variation);
        } catch (JSONException e) {
            Log.e("Log", e.getMessage());
        }
        return coin;

    }

    private static String bytesPraString(InputStream inputStream) {
        byte[] buffer = new byte[1024];
        ByteArrayOutputStream dados = new ByteArrayOutputStream();
        int bytesLidos;
        try {
            while ((bytesLidos = inputStream.read(buffer)) != -1) {
                dados.write(buffer, 0, bytesLidos);
            }
            return new String(dados.toByteArray(), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
