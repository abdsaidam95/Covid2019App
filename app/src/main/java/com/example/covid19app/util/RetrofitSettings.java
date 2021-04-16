package com.example.covid19app.util;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitSettings {

    private static Retrofit retrofit;
    private static final String BASE_URL = "https://api.covid19api.com/";

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            OkHttpClient client = refreshHeaders();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    private static OkHttpClient refreshHeaders() {
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder builder = chain.request().newBuilder();
                builder.addHeader("Token", "TOKEN");
                Request newRequest = builder.build();
                return chain.proceed(newRequest);
            }
        }).build();
        return client;
    }
}
