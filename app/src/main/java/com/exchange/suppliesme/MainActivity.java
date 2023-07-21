package com.exchange.suppliesme;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;



public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button bProcess_button, bBegin_button, bLogout_button;

    @Override
    //in the video, the below is protected void
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

// Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_main);

        // Find the Button that shows How it Works/process_button category
        final Button bProcess_button = (Button) findViewById(R.id.bProcess_button);
        // Find the Button that shows Get Started/begin_button category
        final Button bBegin_button = (Button) findViewById(R.id.bBegin_button);
        // Find the Button that shows Logout/logout_button category
        final Button bLogout_button = (Button) findViewById(R.id.bLogout_button);

        // Capture button clicks
        bProcess_button.setOnClickListener(this);
        bBegin_button.setOnClickListener(this);
        bLogout_button.setOnClickListener(this);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bBegin_button:
                startActivity(new Intent(this, ChooseActivity.class));
                break;

            case R.id.bLogout_button:

                startActivity(new Intent(this, LoginActivity.class));
                break;

            case R.id.bProcess_button:
                startActivity(new Intent(this, ProcessActivity.class));
                break;
        }
    }
}
