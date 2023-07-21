package com.exchange.suppliesme;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class ProcessActivity extends AppCompatActivity implements View.OnClickListener {
    Button begin_button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

// Set the content of the activity to use the process_screen.xml layout file
        setContentView(R.layout.process_screen);

        // Find the Button that shows Get Started/begin_button category
        begin_button = (Button) findViewById(R.id.begin_button);

        begin_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.begin_button:
                startActivity(new Intent(this, ChooseActivity.class));
                break;


        }
    }
}
