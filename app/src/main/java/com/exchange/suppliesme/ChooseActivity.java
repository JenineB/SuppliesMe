package com.exchange.suppliesme;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class ChooseActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_screen);
    }


// Capture button clicks
    public void giveClick(View v){
        startActivity(new Intent(this, LoginActivity.class));
    }
    public void receiveClick(View v){
        startActivity(new Intent (this, LoginActivity.class));
    }
    public void loginClick(View v) {
        startActivity(new Intent(this, LoginActivity.class));
    }
}

