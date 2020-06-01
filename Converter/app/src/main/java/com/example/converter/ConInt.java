package com.example.converter;

import android.os.AsyncTask;
import android.view.View;

import com.google.gson.JsonObject;

import org.apache.commons.math3.util.Precision;

import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


class ConInt extends AsyncTask<String, Integer, String> {
    private WeakReference<MainActivity> mainactivity;
    private String BASEURL = "https://www.cbr-xml-daily.ru/";
    private Retrofit retrofit; // retrofit2 позволяет делать полноценный клиент
    CallObject callObject; // интерфейс через который делаем GET запрос на сервер.
    String out;



    ConInt (WeakReference<MainActivity> mainactivity) {
        this.mainactivity = mainactivity;

    }

    @Override
    protected void onPreExecute() {
        mainactivity.get().progressBar.setVisibility(View.VISIBLE);
        retrofit = new Retrofit.Builder()       //API Builder для конвертирования JSON
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        callObject = retrofit.create(CallObject.class);

        }
    @Override
    protected String doInBackground(String... params) {
        for (int i = 0; i < params.length; i++) {
            publishProgress(i*100/params.length);}
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Call<JsonObject> request = callObject.call();  //Делаем запрос
            JsonObject Value2 = null;
            JsonObject Value1 = null;
            try {
                Response<JsonObject> response = request.execute(); //получаем распарсенный ответ
                JsonObject reader = response.body();
                JsonObject Valute = reader.getAsJsonObject("Valute");
                if ("RUB".equals(params[1])) {
                    Value1 = Valute.getAsJsonObject(params[0]);
                    double a = Value1.get("Value")
                            .getAsDouble();
                    out = String.valueOf(Precision.round(a / 1, 3));//Использовал библиотеку APACH для округления чисел
                    return out;
                } else if ("RUB".equals(params[0])) {
                    Value2 = Valute.getAsJsonObject(params[1]);
                    double a = Value2.get("Value")
                            .getAsDouble();
                    out = String.valueOf(Precision.round(1 / a, 3));
                    return out;
                } else {
                    Value1 = Valute.getAsJsonObject(params[0]);
                    Value2 = Valute.getAsJsonObject(params[1]);
                    double a = Value1.get("Value")
                            .getAsDouble();
                    double b = Value2.get("Value").getAsDouble();
                    out = String.valueOf(Precision.round(a / b, 3));
                    return out;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
        @Override
        protected void onProgressUpdate (Integer...values){
            super.onProgressUpdate(values);
            mainactivity.get().progressBar.setProgress(values[0]);

        }
        @Override
        protected void onPostExecute (String str){
            super.onPostExecute(str);

            try {
                mainactivity.get().progressBar.setProgress(0);
                mainactivity.get().progressBar.setVisibility(View.INVISIBLE);
                mainactivity.get().setResultsCount(str);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
}//В итоге у нас есть объект Retrofit, который содержит базовый URL и способность преобразовывать json данные с помощью Gson. Мы передаем ему в метод create класс интерфейса, в котором описывали методы.