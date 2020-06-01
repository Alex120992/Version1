package com.example.converter;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CallObject { // описание методов
    @GET ("/daily_json.js")    // имя метода
    Call <JsonObject> call(); // Указывваем тип получаемых данных и параметры(Они пустые)
}
