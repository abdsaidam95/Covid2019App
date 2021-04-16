package com.example.covid19app.feature.informCovid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.RoomDatabase;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.covid19app.R;
import com.example.covid19app.feature.chat.Messages;
import com.example.covid19app.localStorege.CountryDeo;
import com.example.covid19app.localStorege.RoomAppDb;
import com.example.covid19app.localStorege.country;
import com.example.covid19app.util.ApiController;
import com.example.covid19app.util.MarginDecoration;
import com.example.covid19app.util.interfaces.ObjectRequestCallback;

import java.util.ArrayList;
import java.util.List;

public class InfrornationActivity extends AppCompatActivity {
    private androidx.appcompat.widget.Toolbar mToolbar;
    private TextView textView;
    public ProgressBar progressBar;
    private RecyclerView recyclerView;
    private AdapterResInform adapterResInform;
    public RoomAppDb roomAppDb;
    CountryDeo countryDeo;
    List<country> countryList = new ArrayList<>();
    public boolean isConnectedNet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infrornation);
        mToolbar = findViewById(R.id.main_page_toolbar);
        progressBar = findViewById(R.id.progress);
        recyclerView = findViewById(R.id.recycle);
        isConnectedNet=isConnectedToInternet();
        roomAppDb = RoomAppDb.getAppDatabase(this);
        countryDeo = roomAppDb.userDao();
        setSupportActionBar(mToolbar);
        textView = findViewById(R.id.text);
        MarginDecoration marginDecoration = new MarginDecoration(this, 8);
        recyclerView.addItemDecoration(marginDecoration);


        int textChange = (int) getIntent().getExtras().get("Key");
        if (textChange == 1) {
            recyclerView.setVisibility(View.GONE);
            textView.setText(R.string.advice_virous);
            getSupportActionBar().setTitle("Advice Covid19");

        } else if (textChange == 2) {
            textView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            getSupportActionBar().setTitle("Estimated Covid19");
            if (isConnectedNet){
                getPost();
            }else {
                getAllCountry();
                if (countryList.size()>0){
                    Log.d("uykuykk","iii "+countryList.size());
                    AdapterRoom adapterRoom=new AdapterRoom(countryList);
                    recyclerView.setAdapter(adapterRoom);
                }
            }

        } else {
            recyclerView.setVisibility(View.GONE);
            textView.setText(R.string.about_corona);
            getSupportActionBar().setTitle("About Covid19");

        }

    }

    private void getPost() {
        progressBar.setVisibility(View.VISIBLE);
        ApiController.getInstance(this).getCountry(new ObjectRequestCallback<Information>() {
            @Override
            public void onSuccess(Information object) {
                countryDeo.deleteAllWords();
                progressBar.setVisibility(View.GONE);
                adapterResInform = new AdapterResInform(object.getCountries());
                recyclerView.setAdapter(adapterResInform);
                int i=0;
                for(Country country:object.getCountries()){
                    country cont = new country();
                    cont.setId(i);
                    cont.setNameCountry(country.getCountry());
                    cont.setTextHealth(String.valueOf(country.getTotalConfirmed()));
                    cont.setTextDeath(String.valueOf(country.getTotalDeaths()));
                    insertCountry(cont, new MyRunnable() {
                        @Override
                        public void run(country data) {
                            countryDeo.insertCountry(data);

                            getAllCountry();

                        }

                        @Override
                        public void run() {

                        }
                    });
                    i++;

                }

                Log.d("yyyy", "size :" + object.getCountries().size());


            }

            @Override
            public void onFailed() {
                progressBar.setVisibility(View.VISIBLE);


            }
        });

    }

    public void insertCountry(country count, MyRunnable runnable) {
        runnable.run(count);
//            countryDeo.insertCountry(count);
//            getAllCountry();


    }

    public void getAllCountry() {

        countryList = countryDeo.getCountry();
    }

    public interface MyRunnable extends Runnable {
        public void run(country data);
    }
    public boolean isConnectedToInternet() {
        ConnectivityManager connManager = (ConnectivityManager) this.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
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