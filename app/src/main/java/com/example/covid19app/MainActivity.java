package com.example.covid19app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covid19app.feature.informCovid.InfrornationActivity;
import com.example.covid19app.feature.chat.ChatActivity;
import com.example.covid19app.feature.editProfile.EditProfileActivity;
import com.example.covid19app.feature.login.LoginActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private String currentUserID;
    private CardView cardChat;
    private DatabaseReference RootRef;
    private TextView textChat,diviceText,estimateText,covidText;
    private DrawerLayout drawer;
    private CircleImageView userImage;
    private TextView userName,status;
    NavigationView navigationView;
    private int textChange;

    private androidx.appcompat.widget.Toolbar mToolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawer = findViewById(R.id.drawer_layout);
         navigationView = findViewById(R.id.nav_view);
       navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
           @Override
           public boolean onNavigationItemSelected(@NonNull MenuItem item) {
               switch (item.getItemId()) {
                   case R.id.nav_home:
                       Intent intent=new Intent(MainActivity.this,EditProfileActivity.class);
                       startActivity(intent);

                       break;
                   case R.id.Log_out:
                       mAuth.signOut();
                       Intent intent1=new Intent(MainActivity.this,LoginActivity.class);
                       startActivity(intent1);
                       Toast.makeText(MainActivity.this, "ttttt", Toast.LENGTH_SHORT).show();

                       break;

               }
               drawer.closeDrawer(GravityCompat.START);
               return true;
           }
       });
        mToolbar = findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, mToolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        RootRef = FirebaseDatabase.getInstance().getReference();
        inisalize();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            SendUserToLoginActivity();
        }
        try {
            currentUserID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();


        }catch (Exception e){}

    }

    private void inisalize() {
        cardChat=findViewById(R.id.cardAdvice);
        diviceText=findViewById(R.id.AdviceText);
        estimateText=findViewById(R.id.EstimatedText);
        textChat=findViewById(R.id.ChatText);
        covidText=findViewById(R.id.convidText);
        textChat.setOnClickListener(this);
        diviceText.setOnClickListener(this);
        estimateText.setOnClickListener(this);
        covidText.setOnClickListener(this);
        inisalizeControler();



    }

    private void inisalizeControler() {
        View header = navigationView.getHeaderView(0);
        userName = (TextView)header. findViewById(R.id.txt_name);
        status = (TextView)header.findViewById(R.id.txt_phone);
        userImage = (CircleImageView)header.findViewById(R.id.imageView);
    }

    private void SendUserToLoginActivity() {
        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        // loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);

    }


    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.ChatText){

            updateUserStatus("online");
            Intent settingsIntent = new Intent(MainActivity.this, ChatActivity.class);
            startActivity(settingsIntent);
        }
        else if (view.getId()==R.id.AdviceText){
            textChange=1;

            goToinformActivity(textChange);
        }
        else if (view.getId()==R.id.EstimatedText){
            textChange=2;
            goToinformActivity(textChange);
        }
        else if (view.getId()==R.id.convidText){
            textChange=3;

            goToinformActivity(textChange);
        }
    }
    public void goToinformActivity(int position){
        Intent intent = new Intent(MainActivity.this, InfrornationActivity.class);
        intent.putExtra("Key",position);
        startActivity(intent);

    }
    @Override
    protected void onStart() {
        super.onStart();

        if (currentUser == null) {
            SendUserToLoginActivity();
        } else {
            updateUserStatus("online");
            VerifyUserExistance();
        }
    }



    private void VerifyUserExistance() {
        String currentUserID = mAuth.getCurrentUser().getUid();

        RootRef.child("Users").child(currentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if ((dataSnapshot.child("name").exists())) {
                    if ((dataSnapshot.child("image").exists())){
                        String name = dataSnapshot.child("name").getValue().toString();
                        String statuss = dataSnapshot.child("status").getValue().toString();
                        String image = dataSnapshot.child("image").getValue().toString();
                        userName.setText(name);
                        status.setText(statuss);
                        Picasso.get().load(image).into(userImage);
                    }
                    String name = dataSnapshot.child("name").getValue().toString();
                    String statuss = dataSnapshot.child("status").getValue().toString();
                    userName.setText(name);
                    status.setText(statuss);

                    Toast.makeText(MainActivity.this, "Welcome"+name, Toast.LENGTH_SHORT).show();
                } else {
                    SendUserToSettingsActivity();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void SendUserToSettingsActivity() {
        Intent settingsIntent = new Intent(MainActivity.this, EditProfileActivity.class);
        startActivity(settingsIntent);
    }
    private void updateUserStatus(String state)
    {
        String saveCurrentTime, saveCurrentDate;

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        HashMap<String, Object> onlineStateMap = new HashMap<>();
        onlineStateMap.put("time", saveCurrentTime);
        onlineStateMap.put("date", saveCurrentDate);
        onlineStateMap.put("state", state);
        onlineStateMap.put("isTyping", false);
        onlineStateMap.put("isTypingFirstUser", false);
        onlineStateMap.put("isTypingisTypingSecandUser", false);

        RootRef.child("Users").child(currentUserID).child("userState")
                .updateChildren(onlineStateMap);

    }

    @Override
    protected void onStop()
    {
        super.onStop();


    }



    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        if (currentUser != null)
        {
            updateUserStatus("offline");
        }
    }
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (currentUserID==null){
                Intent intent1=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent1);
            }
            super.onBackPressed();
        }
    }




}