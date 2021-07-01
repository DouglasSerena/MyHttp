package com.example.myhttp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myhttp.adapters.CurrenciesAdapter;
import com.example.myhttp.adapters.StockAdapter;
import com.example.myhttp.http.FinanceHttp;
import com.example.myhttp.models.Currencies;
import com.example.myhttp.models.Finance;

public class MainActivity extends AppCompatActivity {
    Finance mFinacne = new Finance();
    ListView mList;
    FinanceTask mTask;
    Button btnStock, btnCurrencie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mList = findViewById(R.id.Lista);
        btnCurrencie = findViewById(R.id.btnCurrencie);
        btnStock = findViewById(R.id.btnStock);

        search();

        btnCurrencie.setOnClickListener((v) -> handleCurrencie());
        btnStock.setOnClickListener((v) -> handleStock());
    }

    private void handleStock() {
        StockAdapter stockAdapter = new StockAdapter(getApplicationContext(), mFinacne.getStocks());
        mList.setAdapter(stockAdapter);
        btnCurrencie.setEnabled(true);
        btnStock.setEnabled(false);
    }

    private void handleCurrencie() {
        CurrenciesAdapter currenciesAdapter = new CurrenciesAdapter(getApplicationContext(), mFinacne.getCurrencies());
        mList.setAdapter(currenciesAdapter);
        btnCurrencie.setEnabled(false);
        btnStock.setEnabled(true);
    }


    private void search() {
        if (mTask == null) {
            if (FinanceHttp.hasConexao(this)) {
                start();
            } else {
                Toast.makeText(getApplicationContext(), "Erro a buscar....", Toast.LENGTH_SHORT).show();
            }
        } else if (mTask.getStatus() == AsyncTask.Status.RUNNING) {
            Toast.makeText(getApplicationContext(), "....", Toast.LENGTH_SHORT).show();
        }

    }


    public void start() {
        if (mTask == null || mTask.getStatus() != AsyncTask.Status.RUNNING) {
            mTask = new FinanceTask();
            mTask.execute();
        }
    }

    class FinanceTask extends AsyncTask<Void, Void, Finance> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(getApplicationContext(), "Pronto....", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Finance doInBackground(Void... voids) {
            Finance finance = FinanceHttp.loadFinacne();
            return finance;
        }

        @Override
        protected void onPostExecute(Finance finances) {
            super.onPostExecute(finances);
            if (finances != null) {
                mFinacne = finances;
                handleCurrencie();
            } else {
                Toast.makeText(getApplicationContext(), "Buscando...", Toast.LENGTH_LONG).show();
            }
        }
    }
}