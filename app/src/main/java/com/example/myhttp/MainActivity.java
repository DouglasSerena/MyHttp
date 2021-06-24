package com.example.myhttp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Currencies> mCurrencies;
    ArrayAdapter<Currencies> mAdapter;
    ListView mList;
    CurrenciesTask mTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mList = findViewById(R.id.Lista);

        buscar();
    }
    private void buscar(){
        if(mCurrencies==null){
            mCurrencies= new ArrayList<Currencies>();
        }
        mAdapter= new CurrenciesAdapter(getApplicationContext(),mCurrencies);
        mList.setAdapter(mAdapter);

        if(mTask==null){
            if (CurrenciesHttp.hasConexao(this)){
                start();
            }else{
                Toast.makeText(getApplicationContext(),"Erro a buscar....",Toast.LENGTH_SHORT).show();
            }
        }else if(mTask.getStatus()==AsyncTask.Status.RUNNING){
            Toast.makeText(getApplicationContext(),"....",Toast.LENGTH_SHORT).show();
        }

    }



    public void start(){
        if(mTask ==null || mTask.getStatus()!= AsyncTask.Status.RUNNING){
            mTask = new CurrenciesTask();
            mTask.execute();
        }
    }

    class CurrenciesTask extends AsyncTask<Void,Void, ArrayList<Currencies>>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(getApplicationContext(),"Pronto....",Toast.LENGTH_SHORT).show();
        }

        @Override
        protected ArrayList<Currencies> doInBackground(Void... voids) {
            ArrayList<Currencies> coinsList= CurrenciesHttp.loadCurrencies();
            return coinsList;
        }

        @Override
        protected void onPostExecute(ArrayList<Currencies> currencies) {
            super.onPostExecute(currencies);
            if (currencies!=null){
                mCurrencies.clear();
                mCurrencies.addAll(currencies);
                mAdapter.notifyDataSetChanged();
            }else{
                Toast.makeText(getApplicationContext(),"Buscando...", Toast.LENGTH_LONG).show();
            }
        }
    }
}