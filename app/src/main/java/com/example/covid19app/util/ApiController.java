package com.example.covid19app.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


import com.example.covid19app.feature.informCovid.Information;
import com.example.covid19app.util.interfaces.ObjectRequestCallback;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiController {

    private Context context;
    private Requests requests;
    private static ApiController instance;

    private ApiController(Context context) {
        this.context = context;
        requests = RetrofitSettings.getRetrofitInstance().create(Requests.class);
    }

    public static ApiController getInstance(Context context) {
        if (instance == null) {
            instance = new ApiController(context);
        }
        return instance;
    }




    public void getCountry( final ObjectRequestCallback<Information> objectRequestCallback) {
        if (isConnectedToInternet()) {
            Call<Information> call = requests.getPost();
            call.enqueue(new Callback<Information>() {
                @Override
                public void onResponse(Call<Information> call, Response<Information> response) {
                                 if (response.isSuccessful()) {

                  objectRequestCallback.onSuccess(response.body());
                   } else { objectRequestCallback.onFailed();
                   }
               }



                @Override
                public void onFailure(Call<Information> call, Throwable t) {
                    objectRequestCallback.onFailed();

                }
            });

        }
    }





    public boolean isConnectedToInternet() {
        ConnectivityManager connManager = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connManager.getActiveNetworkInfo();
        if (activeNetwork != null) { // connected to the internet
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI ||
                    activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                // connected to wifi
                return true;
            }
        }
        return false;
    }
}
