package com.example.covid19app.util;


import com.example.covid19app.feature.informCovid.Information;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

interface Requests {


    @GET("summary")
    Call<Information> getPost();




}

